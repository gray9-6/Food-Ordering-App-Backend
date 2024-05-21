package com.ajay.controller;

import com.ajay.model.Food;
import com.ajay.model.Restaurant;
import com.ajay.model.User;
import com.ajay.request.CreateFoodRequest;
import com.ajay.service.FoodService;
import com.ajay.service.RestaurantService;
import com.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(keyword);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantsFood(@RequestParam boolean vegetarian, @RequestParam boolean seasonal,
                                                         @RequestParam boolean nonVeg,
                                                         @PathVariable Long restaurantId,
                                                         @RequestParam(required = false) String foodCategory,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId,vegetarian,nonVeg,seasonal,foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
