package com.ajay.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String description;

    Long price;

    @ManyToOne
    Category foodCategory;


    @Column(length = 1000)  // we are going to store the url of the images, so we are defining the length of url
    @ElementCollection  // with this annotation , it will create the different table for the food images
    List<String> images;

    boolean available;

    @ManyToOne   // many food belongs to one restaurant , but that one food belongs to that one particular restaurant
    Restaurant restaurant;

    boolean isVegetarian;
    boolean isSeasonal;

    @ManyToMany  // many IngredientsItem used in one food and also many Foods can use the same IngredientsItem
    List<IngredientsItem> ingredientsItems = new ArrayList<>();

    @CreationTimestamp
    Date creationDate;
}
