package com.ajay.service.serviceImpl;

import com.ajay.model.Category;
import com.ajay.model.Food;
import com.ajay.model.Restaurant;
import com.ajay.repository.FoodRepository;
import com.ajay.request.CreateFoodRequest;
import com.ajay.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest createFoodRequest, Category foodCategory, Restaurant restaurant) {
        Food createdFood = Food.builder()
                .foodCategory(foodCategory)
                .restaurant(restaurant)
                .name(createFoodRequest.getName())
                .description(createFoodRequest.getDescription())
                .price(createFoodRequest.getPrice())
                .images(createFoodRequest.getImages())
                .isVegetarian(createFoodRequest.isVegetarian())
                .isSeasonal(createFoodRequest.isSeasonal())
                .ingredientsItems(createFoodRequest.getIngredientsItems())
                .build();

        Food savedFood = foodRepository.save(createdFood);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
        /*Note :- the food will remain in our db , but it will not show on the owner or customer dashboard*/
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian,boolean isNonVeg,
                                         boolean isSeasonal, String foodCategory) throws Exception {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

//        foods = isVegetarian == true ? filterByVegetarian(foods, true) : foods;

        if(isVegetarian){
            foods = filterByVegetarian(foods, true);
        }
        if(isNonVeg){
            foods = filterByNonVeg(foods, true);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods, true);
        }
        if(foodCategory != null && !foodCategory.isBlank()){
            foods = filterByFoodCategory(foods,foodCategory);
        }
        return foods;
    }

    private List<Food> filterByFoodCategory(List<Food> foods,String foodCategory) {
        return foods.stream().filter(food -> food.getFoodCategory().getName().equalsIgnoreCase(foodCategory)).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> food.isVegetarian()==isNonVeg).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }


    public Food findFoodById(Long foodId) throws Exception {
        return foodRepository.findById(foodId).orElseThrow(()-> new Exception("Food Not Found for this food id " + foodId));
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());   // agar wo available hai toh , unavailable ho jaayega , and vice versa
        return foodRepository.save(food);
    }
}
