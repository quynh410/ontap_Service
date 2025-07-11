package com.ra.ss4s.controller;

import com.ra.ss4s.model.entity.Book;
import com.ra.ss4s.model.entity.CategoryBook;
import com.ra.ss4s.service.BookService;
import com.ra.ss4s.service.CategoryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryBookService categoryBookService;

    // Hiển thị danh sách sách
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    // Form thêm sách mới
    @GetMapping("/new")
    public String showAddForm(Model model) {
        Book book = new Book();
        List<CategoryBook> categories = categoryBookService.getCategoryBooks();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "books/add";
    }

    // Xử lý thêm sách - Sửa tên thuộc tính
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book, @RequestParam(required = false) String categoryId) {
        if (categoryId != null && !categoryId.isEmpty()) {
            CategoryBook category = categoryBookService.getCategoryBookById(categoryId);
            book.setCategory(category); // Sửa từ setCategoryBook thành setCategory
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    // Form sửa sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Book book = bookService.getBookById(id);
        List<CategoryBook> categories = categoryBookService.getCategoryBooks();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "books/edit";
    }

    // Xử lý cập nhật sách - Sửa tên thuộc tính
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book, @RequestParam(required = false) String categoryId) {
        if (categoryId != null && !categoryId.isEmpty()) {
            CategoryBook category = categoryBookService.getCategoryBookById(categoryId);
            book.setCategory(category); // Sửa từ setCategoryBook thành setCategory
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    // Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    // Tìm kiếm sách
    @GetMapping("/search")
    public String searchBooks(@RequestParam String bookName, Model model) {
        List<Book> books = bookService.getBooksByBookName(bookName);
        model.addAttribute("books", books);
        model.addAttribute("searchTerm", bookName);
        return "books/list";
    }
}