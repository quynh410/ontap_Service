package com.ra.ss4s.repository;

import com.ra.ss4s.model.entity.CategoryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryBookRepository extends JpaRepository<CategoryBook,String> {
    List<CategoryBook> findCategoryBooksByCateBookNameContains(String categoryBookName);
}
