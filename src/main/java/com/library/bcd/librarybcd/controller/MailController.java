package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.entity.Mail;
import com.library.bcd.librarybcd.exception.MailNotSentException;
import com.library.bcd.librarybcd.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody Mail mail) throws MailNotSentException {
        mailService.send(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
