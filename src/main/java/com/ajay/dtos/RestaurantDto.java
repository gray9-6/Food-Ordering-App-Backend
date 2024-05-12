package com.ajay.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Embeddable // jaha par maine iss class ko as a reference use kiya hai , waha par uss class ke  table mein , iss class ki fields table mein aa jaayegi
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantDto {

    String title;

    @Column(length = 1000)
    List<String> images;

    String description;

    Long id;
}









/*
* The `@Embeddable` annotation in JPA (Java Persistence API) is used to designate a class as an embeddable class. An embeddable class is a non-entity class whose instances are stored as part of an owning entity's data. This annotation is typically applied to a class whose instances represent a part of the state of an entity, rather than entities themselves.

Here are some key points about `@Embeddable`:

1. **Purpose**:
   - `@Embeddable` is used to mark a class as embeddable, meaning instances of this class can be embedded within other entities.
   - It signifies that instances of the class should not be stored in a separate table but rather as part of the owning entity's table.

2. **Usage**:
   - `@Embeddable` is applied to the class declaration of the embeddable class.
   - The class must provide getter and setter methods for its properties, just like any other JavaBean.

3. **Embeddable Objects**:
   - Instances of embeddable classes are stored directly within the same table as the owning entity.
   - The fields of the embeddable class are mapped to columns in the database table of the owning entity.

4. **Example**:
   ```java
   @Embeddable
   public class Address {
       private String street;
       private String city;
       private String zipcode;

       // getters and setters
   }
   ```
   In this example, the `Address` class is marked with `@Embeddable`, indicating that it can be embedded within other entity classes.

5. **Embeddable Within Entities**:
   - Embeddable classes are typically used as components of an entity's state.
   - They can be embedded within one or more entity classes, allowing reuse of common attributes across entities.

6. **Composite Primary Keys**:
   - Embeddable classes are often used to define composite primary keys, where multiple attributes together uniquely identify an entity.
   - In such cases, the embeddable class is annotated with `@Embeddable`, and the owning entity uses `@EmbeddedId` to reference the composite key.

`@Embeddable` provides a way to create reusable components for entity classes, simplifying the mapping of complex data structures in JPA. It's particularly useful when certain attributes are shared across multiple entities or when modeling composite keys.
* */