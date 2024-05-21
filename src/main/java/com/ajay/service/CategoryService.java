package com.ajay.service;

import com.ajay.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String categoryName,Long userId) throws Exception;

    List<Category> findCategoryByRestaurantId(Long userId) throws Exception;

    Category findCategoryById(Long categoryId) throws Exception;
}
