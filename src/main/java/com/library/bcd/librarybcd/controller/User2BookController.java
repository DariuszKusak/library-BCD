package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.UserWithPasswordDoesNotExists;
import com.library.bcd.librarybcd.service.User2BookService;
import com.library.bcd.librarybcd.service.UserService;
import com.library.bcd.librarybcd.utils.TmpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/u2b")
public class User2BookController {

    private final User2BookService user2BookService;
    private final UserService userService;

    @Autowired
    public User2BookController(User2BookService user2BookService, UserService userService) {
        this.user2BookService = user2BookService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllUserBooks() throws UserWithPasswordDoesNotExists {
        User user = TmpUser.getTmpUser();
        List<Book> user2Books = user2BookService.getBooksForUser(user);
        return new ResponseEntity<>(user2Books, HttpStatus.OK);
    }

}
