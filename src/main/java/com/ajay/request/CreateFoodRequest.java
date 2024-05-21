package com.ajay.request;

import com.ajay.model.Category;
import com.ajay.model.IngredientsItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateFoodRequest {

    String name;
    String description;
    Long price;

    Category category;
    List<String> images;

    Long restaurantId;
    boolean vegetarian;
    boolean seasonal;
    List<IngredientsItem> ingredientsItems;
}
