package com.ajay.service;

import com.ajay.model.Category;
import com.ajay.model.Food;
import com.ajay.model.Restaurant;
import com.ajay.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Food createFood(CreateFoodRequest createFoodRequest, Category foodCategory, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    List<Food> getRestaurantsFood(Long restaurantId,boolean isVegetarian,boolean isNonVeg,
                                  boolean isSeasonal,String foodCategory) throws Exception;

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId) throws Exception;

    Food updateAvailabilityStatus(Long foodId) throws Exception;
}
