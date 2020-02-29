package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Mail;
import com.library.bcd.librarybcd.exception.MailNotSentException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class MailService {
    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(Mail mail) throws MailNotSentException {

        MimeMessage mime = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mime, true);
            helper.setTo(mail.getTo());
            helper.setReplyTo(mail.getReplyTo());
            helper.setFrom(mail.getFrom());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent());
            javaMailSender.send(mime);
        } catch (MessagingException e) {
            throw new MailNotSentException(mail);
        }
    }

}
