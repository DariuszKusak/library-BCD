package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.exception.BookAlreadyBorrowedByUserException;
import com.library.bcd.librarybcd.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.isAvailable() && book.getAmount() > 0)
                .collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Book + %d not found", id)));
    }

    public Book checkIfBookIsAvailable(Book book) throws BookAlreadyBorrowedByUserException {
        if (book.getAmount() < 1 || !book.isAvailable()) {
            throw new BookAlreadyBorrowedByUserException(1, book.getId());
        }
        return book;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public Book borrowBook(Book book) throws BookAlreadyBorrowedByUserException {
        checkIfBookIsAvailable(book);
        book.setAmount(book.getAmount() - 1);
        if (book.getAmount() < 1) book.setAvailable(false);
        bookRepository.save(book);
        return book;
    }

}
