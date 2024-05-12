package com.ajay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IngredientsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @ManyToOne
    IngredientCategory ingredientCategory;   // we need this becoz inside one IngredientsItem there can be multiple category, (for e.g:- sauce IngredientsItem , can have multiple category , like tomato sauce, red hot sauce)

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;

    boolean inStoke = true;
}
