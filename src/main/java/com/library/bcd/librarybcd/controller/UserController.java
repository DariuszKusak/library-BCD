package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.UserWithPasswordDoesNotExists;
import com.library.bcd.librarybcd.service.User2BookService;
import com.library.bcd.librarybcd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;
    private User2BookService user2BookService;

    @Autowired
    public UserController(UserService userService, User2BookService user2BookService) {
        this.userService = userService;
        this.user2BookService = user2BookService;
    }

    @GetMapping("/authorize/user/{login}/password/{password}")
    public ResponseEntity<User> authorize(@PathVariable String login, @PathVariable String password) throws UserWithPasswordDoesNotExists {
        User authorizedUser = userService.authorizeUser(login, password);
        return new ResponseEntity<>(authorizedUser, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserWithPasswordDoesNotExists {
        User oldUser = userService.findById(user.getId());
        User updatedUser = userService.updateUser(user);
        user2BookService.updateUser2Books(oldUser, updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
