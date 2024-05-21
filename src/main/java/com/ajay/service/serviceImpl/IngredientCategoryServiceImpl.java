package com.ajay.service.serviceImpl;

import com.ajay.model.IngredientCategory;
import com.ajay.model.Restaurant;
import com.ajay.repository.IngredientCategoryRepository;
import com.ajay.service.IngredientCategoryService;
import com.ajay.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {
    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory savedIngredientCategory = IngredientCategory.builder()
                .name(name)
                .restaurant(restaurant)
                .build();
        return ingredientCategoryRepository.save(savedIngredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long categoryId) throws Exception {
        return ingredientCategoryRepository.findById(categoryId).orElseThrow(()-> new Exception("Category Not Found"));
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }
}
