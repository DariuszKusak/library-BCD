package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authorize/user/{login}/password/{password}")
    public ResponseEntity<User> authorize(@PathVariable String login, @PathVariable String password) {
        User authorizedUser = userService.authorizeUser(login, password);
        return new ResponseEntity<>(authorizedUser, HttpStatus.OK);
    }

}
