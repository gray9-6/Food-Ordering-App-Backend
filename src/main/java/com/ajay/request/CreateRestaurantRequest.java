package com.ajay.request;

import com.ajay.model.Address;
import com.ajay.model.ContactInformation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRestaurantRequest {
    Long id;
    String name;
    String description;
    String cuisineType;
    Address address;
    ContactInformation contactInformation;
    String openingHours;
    List<String> images;
}
