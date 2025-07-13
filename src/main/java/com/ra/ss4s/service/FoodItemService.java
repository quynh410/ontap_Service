package com.ra.ss4s.service;

import com.ra.ss4s.model.entity.Category;
import com.ra.ss4s.model.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FoodItemService {

    Page<FoodItem> getAllFoodItems(Pageable pageable);

    Optional<FoodItem> getFoodItemById(Long id);

    FoodItem saveFoodItem(FoodItem foodItem);

    FoodItem updateFoodItem(FoodItem foodItem);

    void deleteFoodItem(Long id);

    List<Category> getAllCategories();

    Page<FoodItem> searchAndFilter(String name, Long categoryId, Pageable pageable);

    Page<FoodItem> searchByName(String name, Pageable pageable);

    Page<FoodItem> filterByCategory(Long categoryId, Pageable pageable);

    Optional<Category> getCategoryById(Long id);

    Category saveCategory(Category category);

    boolean existsCategoryByName(String categoryName);
}