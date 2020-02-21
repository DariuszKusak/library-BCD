package com.library.bcd.librarybcd.exception;

public class BookLimitException extends Exception {
    private static final long UID = 4444L;

    public BookLimitException(int limit) {

        super(String.format("Przekroczono limit wypożyczeń książek, który wynosi %d.", limit));
    }

}
