package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
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
@RequestMapping("/api/v1/u2b")
public class User2BookController {

    private final User2BookService user2BookService;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public User2BookController(User2BookService user2BookService, UserService userService, BookService bookService) {
        this.user2BookService = user2BookService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/{login}/{password}")
    public ResponseEntity<List<Book>> getUserBooks(@PathVariable String login, @PathVariable String password) throws UserWithPasswordDoesNotExists {
        User user = userService.authorizeUser(login, password);
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

    @DeleteMapping("/return/{login}/{password}/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable String login, @PathVariable String password, @PathVariable int bookId) throws UserWithPasswordDoesNotExists, BookNotFoundException {
        User user = userService.authorizeUser(login, password);
        Book book = bookService.getBookById(bookId);
        System.out.println(user);
        System.out.println(book);
        user2BookService.returnBook(user, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
