package com.library.bcd.librarybcd.exception;

public class UserNotFoundException extends Exception {

    private Long UID = 123L;

    public UserNotFoundException(String login) {
        super(String.format("User by login: %s not found.", login));
    }
}
