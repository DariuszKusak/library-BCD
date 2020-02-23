package com.library.bcd.librarybcd.config;

import com.library.bcd.librarybcd.exception.*;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.library.bcd.librarybcd.exception.CustomHttpStatus.*;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> userNotFoundException(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(USER_NOT_FOUND_EXCEPTION, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookLimitException.class)
    public ResponseEntity<CustomErrorResponse> bookLimitExtended(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(BOOK_LIMIT_EXTENDED, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginAlreadyTakenException.class)
    public ResponseEntity<CustomErrorResponse> loginAlreadyTaken(Exception ex) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(LOGIN_ALREADY_TAKEN, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookAlreadyExists.class)
    public ResponseEntity<CustomErrorResponse> bookAlreadyExists(Exception ex) {
        CustomErrorResponse customErrorResponse = generateCustomErrorResponse(BOOK_ALREADY_EXISTS, ex);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private CustomErrorResponse generateCustomErrorResponse(CustomHttpStatus customHttpStatus, Exception ex) {
        return new CustomErrorResponse(LocalDateTime.now(), customHttpStatus.getValue(), ex.getMessage());
    }

}
