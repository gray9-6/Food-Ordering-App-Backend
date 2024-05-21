package com.ajay.controller;

import com.ajay.service.IngredientCategoryService;
import com.ajay.service.IngredientItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    IngredientCategoryService ingredientCategoryService;

    @Autowired
    IngredientItemsService ingredientItemsService;

    
}
