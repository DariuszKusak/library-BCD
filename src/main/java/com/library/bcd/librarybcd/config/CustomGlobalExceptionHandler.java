package com.library.bcd.librarybcd.config;

import com.library.bcd.librarybcd.exception.BookAlreadyBorrowedByUserException;
import com.library.bcd.librarybcd.exception.BookLimitException;
import com.library.bcd.librarybcd.exception.BookNotFoundException;
import com.library.bcd.librarybcd.exception.CustomErrorResponse;
import com.library.bcd.librarybcd.exception.CustomHttpStatus;
import com.library.bcd.librarybcd.exception.UserWithPasswordDoesNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.library.bcd.librarybcd.exception.CustomHttpStatus.BOOK_ALREADY_BORROWED;
import static com.library.bcd.librarybcd.exception.CustomHttpStatus.BOOK_LIMIT_EXTENDED;
import static com.library.bcd.librarybcd.exception.CustomHttpStatus.BOOK_NOT_FOUND;
import static com.library.bcd.librarybcd.exception.CustomHttpStatus.USER_WITH_PASSWORD_NOT_EXISTS;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookAlreadyBorrowedByUserException.class)
    public ResponseEntity<CustomErrorResponse> bookAlreadyBorrowed(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(BOOK_ALREADY_BORROWED, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> bookNotFound(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(BOOK_NOT_FOUND, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWithPasswordDoesNotExists.class)
    public ResponseEntity<CustomErrorResponse> userWithPasswordDoesNotExist(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(USER_WITH_PASSWORD_NOT_EXISTS, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookLimitException.class)
    public ResponseEntity<CustomErrorResponse> bookLimitExtended(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(BOOK_LIMIT_EXTENDED, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private CustomErrorResponse generateCustomErrorResponse(CustomHttpStatus customHttpStatus, Exception ex) {
        return new CustomErrorResponse(LocalDateTime.now(), customHttpStatus.getValue(), ex.getMessage());
    }

}
