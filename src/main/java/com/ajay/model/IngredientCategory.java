package com.ajay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IngredientCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;  // kis restaurant ke saath ye IngredientCategory belong karti hai

    @OneToMany(mappedBy = "ingredientCategory",cascade = CascadeType.ALL,orphanRemoval = true)
    List<IngredientsItem> ingredientsItems = new ArrayList<>();
}
