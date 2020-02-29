package com.library.bcd.librarybcd.exception;

import com.library.bcd.librarybcd.entity.Mail;

public class MailNotSentException extends Exception {

    private static final long UID = 4446;

    public MailNotSentException(Mail mail) {
        super(String.format("Błąd wysyłki wiadomości %s na adres %s", mail.getSubject(), mail.getTo()));
    }


}
