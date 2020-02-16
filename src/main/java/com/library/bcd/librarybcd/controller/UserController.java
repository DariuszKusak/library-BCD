package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.BookNotFoundException;
import com.library.bcd.librarybcd.exception.UserNotFoundException;
import com.library.bcd.librarybcd.service.BookService;
import com.library.bcd.librarybcd.service.User2BookService;
import com.library.bcd.librarybcd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
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
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) throws UserNotFoundException {
        User user = userService.getUserByLogin(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getLoggedUserBooks(Principal principal) throws UserNotFoundException {
        User user = userService.getUserByLogin(principal.getName());
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

    @GetMapping("/{login}/books")
    public ResponseEntity<List<Book>> getUserBooks(@PathVariable String login) throws UserNotFoundException {
        User user = userService.getUserByLogin(login);
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNotFoundException {
        User oldUser = userService.getUserByLogin(user.getLogin());
        User updatedUser = userService.updateUser(user);
        user2BookService.updateUser2Books(oldUser, updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{login}/book/{id}")
    public ResponseEntity<Void> returnUsersBook(@PathVariable String login, @PathVariable int id)
            throws BookNotFoundException, UserNotFoundException {
        User user = userService.getUserByLogin(login);
        Book book = bookService.getBookById(id);
        user2BookService.returnBook(user, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<User> deleteUser(@PathVariable String login) throws UserNotFoundException {
        User user = userService.deleteUser(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
