package com.ajay.controller;

import com.ajay.model.Food;
import com.ajay.model.Restaurant;
import com.ajay.model.User;
import com.ajay.request.CreateFoodRequest;
import com.ajay.response.MessageResponse;
import com.ajay.service.FoodService;
import com.ajay.service.RestaurantService;
import com.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest createFoodRequest,
                                           @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(createFoodRequest.getRestaurantId());
        Food food =foodService.createFood(createFoodRequest,createFoodRequest.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long foodId,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(foodId);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food Deleted Successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateAvailabilityStatus(@PathVariable("id") Long foodId,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(foodId);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
