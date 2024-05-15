package com.ajay.service;

import com.ajay.dtos.RestaurantDto;
import com.ajay.model.Restaurant;
import com.ajay.model.User;
import com.ajay.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);

    Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant) throws Exception;

    void deleteRestaurant(Long restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant();  // only admin have the rights

    List<Restaurant> searchRestaurant(String keyWord);

    Restaurant findRestaurantById(Long restaurantId) throws Exception;

    Restaurant getRestaurantByUserId(Long userId) throws Exception;

    RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;

    Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
