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
@RequestMapping("api/users")
public class UserController {

    private UserService userService;
    private BookService bookService;
    private User2BookService user2BookService;

    @Autowired
    public UserController(UserService userService, BookService bookService, User2BookService user2BookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.user2BookService = user2BookService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        User user = userService.findByLogin(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getUserBooks() throws UserWithPasswordDoesNotExists {
        User user = userService.authorizeUser("d_user", "123");
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserWithPasswordDoesNotExists {
        User oldUser = userService.findById(user.getId());
        User updatedUser = userService.updateUser(user);
        user2BookService.updateUser2Books(oldUser, updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> returnUsersBook(@PathVariable String login, @PathVariable String password, @PathVariable int bookId) throws UserWithPasswordDoesNotExists, BookNotFoundException {
        User user = userService.authorizeUser(login, password);
        Book book = bookService.getBookById(bookId);
        System.out.println(user);
        System.out.println(book);
        user2BookService.returnBook(user, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
