package com.ra.ss4s.repository;

import com.ra.ss4s.model.entity.Category;
import com.ra.ss4s.model.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    Page<FoodItem> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<FoodItem> findByCategory(Category category, Pageable pageable);

    Page<FoodItem> findByCategoryId(Long categoryId, Pageable pageable);

    Page<FoodItem> findByNameContainingIgnoreCaseAndCategory(String name, Category category, Pageable pageable);

    Page<FoodItem> findByNameContainingIgnoreCaseAndCategoryId(String name, Long categoryId, Pageable pageable);

    @Query("SELECT f FROM FoodItem f WHERE " +
            "(:name IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR f.category = :category)")
    Page<FoodItem> findByNameAndCategory(@Param("name") String name,
                                         @Param("category") Category category,
                                         Pageable pageable);
}