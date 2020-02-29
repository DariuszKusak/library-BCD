package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.Mail;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.LoginAlreadyTakenException;
import com.library.bcd.librarybcd.exception.MailNotSentException;
import com.library.bcd.librarybcd.service.AuthorityService;
import com.library.bcd.librarybcd.service.MailService;
import com.library.bcd.librarybcd.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final UserService userService;
    private final MailService mailService;
    private final AuthorityService authorityService;

    public AccountController(UserService userService, MailService mailService, AuthorityService authorityService) {
        this.userService = userService;
        this.mailService = mailService;
        this.authorityService = authorityService;
    }

    @PostMapping
    public ResponseEntity<AngularUser> createAccount(@RequestBody AngularUser user) throws LoginAlreadyTakenException, MailNotSentException {
        String password = RandomStringUtils.randomAlphabetic(5);
        User newUser = userService.createUser(user, password);
        authorityService.grantRoleToUser(user.getLogin());
        mailService.send(prepareMail(user, password));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private Mail prepareMail(AngularUser user, String password) {
        return new Mail("examples123.dk@gmail.com", user.getEmail(), "examples123.dk@gmail.com", "Nowe hasło", password);
    }

}
