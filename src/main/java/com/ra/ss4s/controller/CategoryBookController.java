package com.ra.ss4s.controller;

import com.ra.ss4s.model.entity.CategoryBook;
import com.ra.ss4s.service.CategoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryBookController {

    @Autowired
    private CategoryBookService categoryBookService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryBook> categories = categoryBookService.getCategoryBooks();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        CategoryBook category = new CategoryBook();
        model.addAttribute("category", category);
        return "categories/add";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute CategoryBook category) {
        categoryBookService.insertCategoryBook(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        CategoryBook category = categoryBookService.getCategoryBookById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute CategoryBook category) {
        categoryBookService.updateCategoryBook(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryBookService.deleteCategoryBookById(id);
        return "redirect:/categories";
    }
}