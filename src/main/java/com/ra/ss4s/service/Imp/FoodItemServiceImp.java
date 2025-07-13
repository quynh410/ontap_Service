package com.ra.ss4s.service.Imp;

import com.ra.ss4s.model.entity.Category;
import com.ra.ss4s.model.entity.FoodItem;
import com.ra.ss4s.repository.CategoryRepository;
import com.ra.ss4s.repository.FoodItemRepository;
import com.ra.ss4s.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemServiceImp implements FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<FoodItem> getAllFoodItems(Pageable pageable) {
        return foodItemRepository.findAll(pageable);
    }

    @Override
    public Optional<FoodItem> getFoodItemById(Long id) {
        return foodItemRepository.findById(id);
    }

    @Override
    public FoodItem saveFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    @Override
    public FoodItem updateFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    @Override
    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<FoodItem> searchAndFilter(String name, Long categoryId, Pageable pageable) {
        Category category = null;
        if (categoryId != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            if (categoryOpt.isPresent()) {
                category = categoryOpt.get();
            }
        }

        return foodItemRepository.findByNameAndCategory(
                (name != null && !name.trim().isEmpty()) ? name.trim() : null,
                category,
                pageable
        );
    }

    @Override
    public Page<FoodItem> searchByName(String name, Pageable pageable) {
        return foodItemRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<FoodItem> filterByCategory(Long categoryId, Pageable pageable) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return foodItemRepository.findByCategory(category.get(), pageable);
        }
        return Page.empty(pageable);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public boolean existsCategoryByName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }
}