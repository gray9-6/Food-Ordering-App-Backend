package com.ajay.service;

import com.ajay.model.IngredientCategory;

import java.util.List;

public interface IngredientCategoryService {

    IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;

    IngredientCategory findIngredientCategoryById(Long categoryId) throws Exception;

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;


}
