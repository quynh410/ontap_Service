package com.ra.ss4s.service;


import com.ra.ss4s.model.entity.CategoryBook;

import java.util.List;

public interface CategoryBookService {
    List<CategoryBook> getCategoryBooks();
    boolean insertCategoryBook(CategoryBook categoryBook);
    boolean updateCategoryBook(CategoryBook categoryBook);
    boolean deleteCategoryBookById(String cateBookId);
    CategoryBook getCategoryBookById(String cateBookId);
    List<CategoryBook> getCategoryBooksByCategoryName(String categoryName);

}