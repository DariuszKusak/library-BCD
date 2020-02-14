package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.UserWithPasswordDoesNotExists;
import com.library.bcd.librarybcd.service.UserService;
import com.library.bcd.librarybcd.utils.TmpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authorize/user/{login}/password/{password}")
    public ResponseEntity<User> authorize(@PathVariable String login, @PathVariable String password) throws UserWithPasswordDoesNotExists {
        User authorizedUser = TmpUser.getTmpUser();
        return new ResponseEntity<>(authorizedUser, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserWithPasswordDoesNotExists {
        System.out.println(user);
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

}
