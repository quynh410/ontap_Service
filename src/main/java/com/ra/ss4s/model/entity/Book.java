package com.ra.ss4s.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(name = "bookName", length = 100)
    private String bookName;

    @Column(name = "author", length = 70)
    private String author;

    @Column(name = "publisher", length = 100)
    private String publisher;

    @Column(name = "year_publish")
    private Integer yearPublish;

    @Column(name = "price")
    private Double price;

    // Sửa tên thuộc tính từ categoryBook thành category
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "cate_book_id")
    private CategoryBook category;
}