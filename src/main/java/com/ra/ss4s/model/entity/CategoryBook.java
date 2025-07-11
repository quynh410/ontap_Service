package com.ra.ss4s.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categoryBook")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBook {
    @Id
    @Column(name = "cate_book_id", length = 20)
    private String cateBookId;

    @Column(name = "cate_book_name", length = 100, unique = true, nullable = false)
    private String cateBookName;

    // Sửa tên thuộc tính từ bookList thành books và mappedBy thành "category"
    @OneToMany(mappedBy = "category")
    private List<Book> books;
}