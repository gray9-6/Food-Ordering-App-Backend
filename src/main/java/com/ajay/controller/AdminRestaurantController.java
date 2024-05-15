package com.ajay.controller;

import com.ajay.config.JwtConstant;
import com.ajay.model.Restaurant;
import com.ajay.model.User;
import com.ajay.request.CreateRestaurantRequest;
import com.ajay.response.MessageResponse;
import com.ajay.service.RestaurantService;
import com.ajay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest createRestaurantRequest,
                                                       @RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(createRestaurantRequest,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest updateRestaurantRequest,
                                                       @RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                       @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId,updateRestaurantRequest);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                       @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(restaurantId);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant Deleted Successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                            @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
