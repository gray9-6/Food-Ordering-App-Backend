package com.ajay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    User owner;

    String name;

    String description;

    String cuisineType;

    @OneToOne
    Address address;

    @Embedded
    ContactInformation contactInformation;

    String openingHours;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    List<Order> orderList = new ArrayList<>();

    @Column(length = 1000)
    @ElementCollection
    List<String> images;

    LocalDateTime registrationDate;  // its that date , when this restaurant owner register on our platform

    boolean open;  // restaurant open ya close

    @JsonIgnore  // whenever i fetch the restaurant , i don't want the foodList in the restaurant object, becoz i will make the separate api to fetch the foodList for a particular restaurant.
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    List<Food> foods = new ArrayList<>();

}
