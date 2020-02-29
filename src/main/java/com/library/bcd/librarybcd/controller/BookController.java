package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.exception.*;
import com.library.bcd.librarybcd.service.BookService;
import com.library.bcd.librarybcd.service.User2BookService;
import com.library.bcd.librarybcd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final UserService userService;
    private final BookService bookService;
    private final User2BookService user2BookService;

    @Autowired
    public BookController(UserService userService, BookService bookService, User2BookService user2BookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.user2BookService = user2BookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) throws BookNotFoundException {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Book> borrowBook(HttpServletRequest request, @RequestBody Book book) throws BookAlreadyBorrowedByUserException,
            BookLimitException, UserNotFoundException {
        User user = userService.getUserByLogin(request.getUserPrincipal().getName());
        Book borrowedBook = bookService.borrowBook(book, user);
        User2Book u2b = user2BookService.borrowBookForUser(user, borrowedBook);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) throws BookNotFoundException {
        bookService.updateBook(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws BookAlreadyExists {
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) throws BookNotFoundException {
        Book book = bookService.getBookById(id);
        user2BookService.returnBooks(book);
        bookService.deleteBook(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
