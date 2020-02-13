package com.library.bcd.librarybcd.exception;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;

public class BookAlreadyBorrowedByUserException extends Exception {

    private static final long UID = 4441L;

    public BookAlreadyBorrowedByUserException(Book book, User user) {
        super(String.format("Book(%d): %s has been already borrowed by user(%d): %s",
                book.getId(), book.getTitle(), user.getId(), user.getLogin()));
    }

}
