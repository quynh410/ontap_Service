package com.ra.ss4s.controller;

import com.ra.ss4s.model.entity.Category;
import com.ra.ss4s.model.entity.FoodItem;
import com.ra.ss4s.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/food-items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @GetMapping
    public String listFoodItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            Model model) {

        // Validate page number
        if (page < 0) page = 0;
        if (size < 1) size = 10;

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FoodItem> foodItems;

        if ((name != null && !name.trim().isEmpty()) || categoryId != null) {
            foodItems = foodItemService.searchAndFilter(name, categoryId, pageable);
        } else {
            foodItems = foodItemService.getAllFoodItems(pageable);
        }

        List<Category> categories = foodItemService.getAllCategories();

        model.addAttribute("foodItems", foodItems);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodItems.getTotalPages());
        model.addAttribute("totalItems", foodItems.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);

        return "food-items/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        FoodItem foodItem = new FoodItem();
        model.addAttribute("foodItem", foodItem);
        model.addAttribute("categories", foodItemService.getAllCategories());
        return "food-items/form";
    }

    @PostMapping
    public String createFoodItem(@Validated @ModelAttribute FoodItem foodItem,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categories", foodItemService.getAllCategories());
            return "food-items/form";
        }

        try {
            foodItemService.saveFoodItem(foodItem);
            redirectAttributes.addFlashAttribute("successMessage", "Thực phẩm đã được thêm thành công!");
            return "redirect:/food-items";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm thực phẩm: " + e.getMessage());
            model.addAttribute("categories", foodItemService.getAllCategories());
            return "food-items/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<FoodItem> foodItem = foodItemService.getFoodItemById(id);

            if (foodItem.isPresent()) {
                model.addAttribute("foodItem", foodItem.get());
                model.addAttribute("categories", foodItemService.getAllCategories());
                return "food-items/form";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thực phẩm với ID: " + id);
                return "redirect:/food-items";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/food-items";
        }
    }

    @PostMapping("/{id}")
    public String updateFoodItem(@PathVariable Long id,
                                 @Validated @ModelAttribute FoodItem foodItem,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categories", foodItemService.getAllCategories());
            return "food-items/form";
        }

        try {
            foodItem.setId(id);
            foodItemService.updateFoodItem(foodItem);
            redirectAttributes.addFlashAttribute("successMessage", "Thực phẩm đã được cập nhật thành công!");
            return "redirect:/food-items";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật thực phẩm: " + e.getMessage());
            model.addAttribute("categories", foodItemService.getAllCategories());
            return "food-items/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteFoodItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<FoodItem> foodItem = foodItemService.getFoodItemById(id);
            if (foodItem.isPresent()) {
                foodItemService.deleteFoodItem(id);
                redirectAttributes.addFlashAttribute("successMessage", "Thực phẩm đã được xóa thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thực phẩm với ID: " + id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa thực phẩm: " + e.getMessage());
        }

        return "redirect:/food-items";
    }

    @GetMapping("/{id}")
    public String showFoodItemDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<FoodItem> foodItem = foodItemService.getFoodItemById(id);

            if (foodItem.isPresent()) {
                model.addAttribute("foodItem", foodItem.get());
                return "food-items/details";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thực phẩm với ID: " + id);
                return "redirect:/food-items";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/food-items";
        }
    }
}