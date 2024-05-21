package com.ajay.service;

import com.ajay.model.IngredientsItem;

import java.util.List;


public interface IngredientItemsService {

    IngredientsItem createIngredientItems(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    List<IngredientsItem> findRestaurantsIngredientItems(Long restaurantId);

    IngredientsItem updateStock(Long ingredientId) throws Exception;
}
