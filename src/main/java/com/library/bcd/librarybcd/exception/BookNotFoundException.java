package com.library.bcd.librarybcd.exception;

public class BookNotFoundException extends Exception {

    private static final long UID = 4442L;

    public BookNotFoundException(int bookId) {
        super(String.format("Book(%d) not found", bookId));
    }
}
