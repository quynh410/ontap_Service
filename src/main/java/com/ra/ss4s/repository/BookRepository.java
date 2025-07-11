package com.ra.ss4s.repository;

import com.ra.ss4s.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :bookName, '%'))")
    List<Book> findByBookNameContainingIgnoreCase(@Param("bookName") String bookName);

    List<Book> findByBookNameContaining(String bookName);
}