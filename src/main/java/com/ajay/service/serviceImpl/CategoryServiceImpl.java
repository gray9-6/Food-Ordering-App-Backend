package com.ajay.service.serviceImpl;

import com.ajay.model.Category;
import com.ajay.model.Restaurant;
import com.ajay.repository.CategoryRepository;
import com.ajay.service.CategoryService;
import com.ajay.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RestaurantService restaurantService;


    @Override
    public Category createCategory(String categoryName, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category savedCategory = Category.builder()
                .name(categoryName)
                .restaurant(restaurant)
                .build();

        return categoryRepository.save(savedCategory);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long categoryId) throws Exception {
        return categoryRepository.findById(categoryId).orElseThrow(()-> new Exception("Category Not Found !!"));
    }
}
