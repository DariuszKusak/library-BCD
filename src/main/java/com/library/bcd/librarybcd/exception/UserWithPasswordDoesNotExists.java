package com.library.bcd.librarybcd.exception;

public class UserWithPasswordDoesNotExists extends Exception {

    private static final long UID = 4443L;

    public UserWithPasswordDoesNotExists(String login, String password) {
        super(String.format("User: %s with password: %s does not exist.", login, password));
    }
}
