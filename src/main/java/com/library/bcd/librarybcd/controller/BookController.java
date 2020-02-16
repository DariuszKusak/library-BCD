package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.exception.BookAlreadyBorrowedByUserException;
import com.library.bcd.librarybcd.exception.BookLimitException;
import com.library.bcd.librarybcd.exception.BookNotFoundException;
import com.library.bcd.librarybcd.exception.UserWithPasswordDoesNotExists;
import com.library.bcd.librarybcd.service.BookService;
import com.library.bcd.librarybcd.service.User2BookService;
import com.library.bcd.librarybcd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<Book> books = bookService.getAllBooks();
        System.out.println(books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Book> borrowBook(@RequestBody Book book) throws BookAlreadyBorrowedByUserException, BookNotFoundException, UserWithPasswordDoesNotExists, BookLimitException {
        User user = userService.authorizeUser("d_user", "123");
        user2BookService.checkIfBookDoNotDuplicate(user, book);
        Book borrowedBook = bookService.borrowBook(book, user);
        User2Book u2b = user2BookService.borrowUser4Book(user, borrowedBook);
        bookService.saveBook(borrowedBook);
        user2BookService.saveU2B(u2b);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Book> addBook() {
//        Book newBook = new Book();
//        newBook.setAmount(500);
//        newBook.setAvailable(true);
//        newBook.setTitle("abc");
//        bookService.saveBook(newBook);
//        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
//    }

}
