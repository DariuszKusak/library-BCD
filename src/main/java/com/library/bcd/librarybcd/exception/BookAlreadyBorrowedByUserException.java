package com.library.bcd.librarybcd.exception;

public class BookAlreadyBorrowedByUserException extends Exception {

    private static final long UID = 4441L;

    public BookAlreadyBorrowedByUserException(String bookName) {
        super(String.format("Książka %s została już wypożyczona przez zalogowanego użytkownika.", bookName));
    }

}
