package com.library.bcd.librarybcd.exception;

public class LoginAlreadyTakenException extends Exception {

    private static final long UID = 444L;

    public LoginAlreadyTakenException(String login) {
        super(String.format("Użytkownik o loginie %s już istnieje", login));
    }
}
