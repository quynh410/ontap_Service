package com.ra.ss4s.service.Imp;

import com.ra.ss4s.model.entity.Book;
import com.ra.ss4s.repository.BookRepository;
import com.ra.ss4s.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public boolean addBook(Book book) {
        try {
            bookRepository.save(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBook(Book book) {
        try {
            Book existingBook = bookRepository.findById(book.getBookId()).orElse(null);
            if (existingBook != null) {
                bookRepository.save(book);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBook(Integer bookId) {
        try {
            bookRepository.deleteById(bookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    public List<Book> getBooksByBookName(String bookName) {
        // Sử dụng method từ Repository thay vì filter stream
        return bookRepository.findByBookNameContainingIgnoreCase(bookName);
    }
}