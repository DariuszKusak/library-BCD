package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.LoginAlreadyTakenException;
import com.library.bcd.librarybcd.exception.MailNotSentException;
import com.library.bcd.librarybcd.service.AuthorityService;
import com.library.bcd.librarybcd.service.MailService;
import com.library.bcd.librarybcd.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.library.bcd.librarybcd.utils.Utils.generatePassword;
import static com.library.bcd.librarybcd.utils.Utils.prepareMail;

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
    public ResponseEntity<AngularUser> createAccount(@RequestBody AngularUser user) throws LoginAlreadyTakenException,
            MailNotSentException {
        String password = generatePassword();
        User newUser = userService.createUser(user, password);
        authorityService.grantRoleToUser(newUser.getLogin());
        mailService.send(prepareMail(newUser, password));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
