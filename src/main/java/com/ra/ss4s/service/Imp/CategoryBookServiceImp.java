package com.ra.ss4s.service.Imp;


import com.ra.ss4s.model.entity.CategoryBook;
import com.ra.ss4s.repository.CategoryBookRepository;
import com.ra.ss4s.service.CategoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBookServiceImp implements CategoryBookService {
    @Autowired
    private CategoryBookRepository categoryBookRepository;

    @Override
    public List<CategoryBook> getCategoryBooks() {
        return categoryBookRepository.findAll();
    }

    @Override
    public boolean insertCategoryBook(CategoryBook categoryBook) {
        try {
            categoryBookRepository.save(categoryBook);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCategoryBook(CategoryBook categoryBook) {
        try {
            CategoryBook existingCategory = categoryBookRepository.findById(categoryBook.getCateBookId()).orElse(null);
            if (existingCategory != null) {
                categoryBookRepository.save(categoryBook);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCategoryBookById(String cateBookId) {
        try {
            categoryBookRepository.deleteById(cateBookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CategoryBook getCategoryBookById(String cateBookId) {
        return categoryBookRepository.findById(cateBookId).orElse(null);
    }

    @Override
    public List<CategoryBook> getCategoryBooksByCategoryName(String categoryName) {
        return categoryBookRepository.findCategoryBooksByCateBookNameContains(categoryName);
    }
}