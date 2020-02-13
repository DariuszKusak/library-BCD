package com.library.bcd.librarybcd.exception;

public class BookLimitException extends Exception {

    private static final long UID = 4444L;

    public BookLimitException(int limit) {
        super(String.format("Book limit(%d) extended", limit));
    }
}
