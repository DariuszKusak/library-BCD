package com.library.bcd.librarybcd.exception;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;

public class BookAlreadyBorrowedByUserException extends Exception {

    private static final long UID = 4441L;

    public BookAlreadyBorrowedByUserException(String bookName) {
        super("Książka %s została już wyporzyczona przez zalogowanego użytkownika.");
    }

}
