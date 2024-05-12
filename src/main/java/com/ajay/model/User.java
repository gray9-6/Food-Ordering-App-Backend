package com.ajay.model;

import com.ajay.dtos.RestaurantDto;
import com.ajay.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;

    String email;

    String password;

    UserRole role = UserRole.CUSTOMER;

    /* Preventing Jackson from serializing the 'orders' field to avoid potential
       issues with circular dependencies or infinite recursion during JSON serialization.
    */
    @JsonIgnore   // whenever i fetch the user , at that time i don't need the ordersList, becoz i will write the separate api to fetch the user order
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Order> orders = new ArrayList<>();

    @ElementCollection
    List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)  // CascadeType.ALL becoz whenever i delete the user, all the address related to this user should  delete automatically
    List<Address> addresses = new ArrayList<>();
}



/*   NOTE :-  (@JsonIgnore)
* The `@JsonIgnore` annotation in this context is used to prevent Jackson,
* the JSON processing library commonly used in Spring applications,
*  from serializing the `orders` field of the `User` class into JSON.

Here's why you might use `@JsonIgnore` in this scenario:

1. **Avoiding Infinite Recursion**:
   - If the `Order` class (which is referenced by the `User` class through the `orders` field)
   * has a reference back to `User`, and if Jackson serializes `orders`,
   * it might lead to infinite recursion during the serialization process.
   * Jackson will attempt to serialize the `User` objects contained within `orders`,
   * which in turn might try to serialize their associated `Order` objects, leading to an endless loop.
   - By annotating `orders` with `@JsonIgnore`, you break this loop and prevent Jackson from attempting
   * to serialize the `Order` objects back to `User` objects.

2. **Avoiding Circular Dependencies**:
   - Even if there's no direct circular reference between `User` and `Order`,
   * omitting the `@JsonIgnore` annotation might still result in circular dependencies
   * when serializing objects with bidirectional relationships.
   * This can lead to unexpected behavior or stack overflow errors during serialization.

3. **Selective Serialization**:
   - Sometimes, certain fields are not relevant or should not be exposed in the JSON representation of an object.
   * In such cases, `@JsonIgnore` can be used to exclude those fields from serialization
   * while still retaining them in the Java object.

However, it's essential to consider the implications of using `@JsonIgnore`.
* If you genuinely need to serialize `orders` in certain contexts,
* you might want to use more advanced techniques like custom serializers
* or DTOs (Data Transfer Objects) to control the serialization process more precisely.
* */



/*
* The `@ElementCollection` annotation in JPA (Java Persistence API) is used to define a collection of basic or embeddable types as part of an entity. This annotation is typically applied to a field or property of a Java class that represents a collection of simple values or embeddable objects.

Here are some key points about `@ElementCollection`:

1. **Usage**:
   - `@ElementCollection` is applied to a collection-valued field or property within an entity class.
   - It signifies that the collection is not composed of entities, but rather of basic types (such as String, Integer, etc.) or embeddable classes.

2. **Basic Types**:
   - When the elements of the collection are basic types (e.g., String, Integer), they are stored directly in a separate table that is mapped to the owning entity.
   - The collection elements are typically mapped to columns in the database table.

3. **Embeddable Types**:
   - If the elements of the collection are embeddable classes (classes without their own identity), the fields of these embeddable classes are mapped to columns in the same table as the owning entity.

4. **Example**:
   ```java
   @Entity
   public class Book {
       @Id
       private Long id;

       private String title;

       @ElementCollection
       private List<String> authors = new ArrayList<>();

       // getters and setters
   }
   ```
   In this example, the `authors` field represents an `@ElementCollection` of basic type `String`. It will be mapped to a separate table with a column for the author names.

5. **Cascading**:
   - By default, changes to the elements of the collection are not cascaded to the database (i.e., changes to the elements do not trigger database updates).
   - However, cascading behavior can be specified using `@ElementCollection` in conjunction with other cascade-related annotations.

6. **Indexed Collections**:
   - You can specify additional attributes such as `@OrderColumn` or `@CollectionTable` to define the ordering or table name for the collection, respectively.

`@ElementCollection` provides a convenient way to map collections of basic or embeddable types without the need to create separate entity classes for each element. It's useful for scenarios where you have simple data types associated with an entity that do not warrant their own entities.
* */