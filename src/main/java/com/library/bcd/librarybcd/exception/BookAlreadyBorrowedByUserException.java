package com.library.bcd.librarybcd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Book already borrowed")
public class BookAlreadyBorrowedByUserException extends Exception {

    private static final long UID = 11111L;

    public BookAlreadyBorrowedByUserException(int userId, int bookId) {
        super(String.format("Book: %d already borrowed by user %d", bookId, userId));
    }

}
