package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.BookNotFoundException;
import com.library.bcd.librarybcd.exception.LoginAlreadyTakenException;
import com.library.bcd.librarybcd.exception.UserNotFoundException;
import com.library.bcd.librarybcd.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.library.bcd.librarybcd.utils.Convert.user2AngularUser;
import static com.library.bcd.librarybcd.utils.Convert.userList2AngularUserList;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;
    private AuthorityService authorityService;
    private BookService bookService;
    private User2BookService user2BookService;

    @Autowired
    public UserController(UserService userService, AuthorityService authorityService, BookService bookService,
                          User2BookService user2BookService) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.bookService = bookService;
        this.user2BookService = user2BookService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<AngularUser> getUserByLogin(@PathVariable String login) throws UserNotFoundException {
        User user = userService.getUserByLogin(login);
        return new ResponseEntity<>(user2AngularUser(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AngularUser>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(userList2AngularUserList(users), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getLoggedUserBooks(HttpServletRequest request) throws UserNotFoundException {
        System.out.println(request.getUserPrincipal().getName());
        User user = userService.getUserByLogin(request.getUserPrincipal().getName());
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

    @GetMapping("/{login}/books")
    public ResponseEntity<List<Book>> getUserBooks(@PathVariable String login) throws UserNotFoundException {
        User user = userService.getUserByLogin(login);
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody AngularUser user, @RequestHeader("Password") String password) throws LoginAlreadyTakenException {
        User newUser = userService.createUser(user, password);
        authorityService.grantRoleToUser(user.getLogin());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody AngularUser user) throws UserNotFoundException {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/disable/{login}")
    public ResponseEntity<User> blockUser(@PathVariable String login) throws UserNotFoundException {
        User blockedUser = userService.setStatus(login, false);
        return new ResponseEntity<>(blockedUser, HttpStatus.OK);
    }

    @PutMapping("/enable/{login}")
    public ResponseEntity<User> unBlockUser(@PathVariable String login) throws UserNotFoundException {
        User blockedUser = userService.setStatus(login, true);
        return new ResponseEntity<>(blockedUser, HttpStatus.OK);
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
        user2BookService.returnUserBooks(login);
        authorityService.revokeRoleFromUser(login);
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
