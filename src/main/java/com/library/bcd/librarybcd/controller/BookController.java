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
import com.library.bcd.librarybcd.utils.TmpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final User2BookService user2BookService;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public BookController(User2BookService user2BookService, UserService userService, BookService bookService) {
        this.user2BookService = user2BookService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> availableBooks = bookService.getAvailableBooks();
        return new ResponseEntity<>(availableBooks, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) throws BookNotFoundException {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/{bookId}/{login}/{password}")
    public ResponseEntity<Book> borrowBook(@PathVariable int bookId, @PathVariable String login, @PathVariable String password) throws BookAlreadyBorrowedByUserException, BookNotFoundException, UserWithPasswordDoesNotExists, BookLimitException {
        User user = TmpUser.getTmpUser();
        Book book = bookService.getBookById(bookId);
        user2BookService.checkIfBookDoNotDuplicate(user, book);
        Book borrowedBook = bookService.borrowBook(book);
        User2Book u2b = user2BookService.borrowUser4Book(user, borrowedBook);
        bookService.saveBook(borrowedBook);
        user2BookService.saveU2B(u2b);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook() {
        Book newBook = new Book();
        newBook.setAmount(500);
        newBook.setAvailable(true);
        newBook.setTitle("abc");
        bookService.saveBook(newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

}
