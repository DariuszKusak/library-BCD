package com.library.bcd.librarybcd.exception;

import com.library.bcd.librarybcd.entity.Book;

public class BookAlreadyExists extends Exception {

    private static final long UID = 4445;

    public BookAlreadyExists(Book book) {
        super(String.format("Książka %d autora %d wydana w roku %d istnieje już w bazie danych",
                book.getTitle(), book.getAuthor(), book.getYear()));
    }
}
