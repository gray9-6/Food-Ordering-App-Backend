package com.ajay.controller;

import com.ajay.config.JwtConstant;
import com.ajay.dtos.RestaurantDto;
import com.ajay.model.Restaurant;
import com.ajay.model.User;
import com.ajay.service.RestaurantService;
import com.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                             @RequestParam("keyword") String keyword) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurantList = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantByID(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                             @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                       @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurantDto = restaurantService.addToFavorites(restaurantId,user);
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }

}
