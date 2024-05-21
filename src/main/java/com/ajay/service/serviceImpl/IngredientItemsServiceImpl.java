package com.ajay.service.serviceImpl;

import com.ajay.model.IngredientCategory;
import com.ajay.model.IngredientsItem;
import com.ajay.model.Restaurant;
import com.ajay.repository.IngredientItemRepository;
import com.ajay.service.IngredientCategoryService;
import com.ajay.service.IngredientItemsService;
import com.ajay.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientItemsServiceImpl implements IngredientItemsService {

    @Autowired
    IngredientItemRepository ingredientItemRepository;

    @Autowired
    IngredientCategoryService ingredientCategoryService;
    @Autowired
    RestaurantService restaurantService;


    @Override
    public IngredientsItem createIngredientItems(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant= restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = ingredientCategoryService.findIngredientCategoryById(categoryId);

        IngredientsItem createdIngredientsItem = IngredientsItem.builder()
                .name(ingredientName)
                .ingredientCategory(ingredientCategory)
                .restaurant(restaurant)
                .build();

        IngredientsItem savedIngredientsItem = ingredientItemRepository.save(createdIngredientsItem);
        ingredientCategory.getIngredientsItems().add(savedIngredientsItem);

        return savedIngredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredientItems(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long ingredientId) throws Exception {
        IngredientsItem ingredientsItem = ingredientItemRepository.findById(ingredientId).orElseThrow(()-> new Exception("IngredientsItem not found !!"));

        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        ingredientItemRepository.save(ingredientsItem);
        return ingredientsItem;
    }
}
