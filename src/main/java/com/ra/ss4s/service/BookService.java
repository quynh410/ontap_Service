package com.ra.ss4s.service;


import com.ra.ss4s.model.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();
    boolean addBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(Integer bookId);
    Book getBookById(Integer bookId);
    List<Book> getBooksByBookName(String bookName);
}