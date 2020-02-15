package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.exception.BookAlreadyBorrowedByUserException;
import com.library.bcd.librarybcd.exception.BookLimitException;
import com.library.bcd.librarybcd.exception.BookNotFoundException;
import com.library.bcd.librarybcd.repository.BookRepository;
import com.library.bcd.librarybcd.repository.User2BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookService {

    private final int BOOKS_LIMIT = 3;

    private final BookRepository bookRepository;
    private final User2BookRepository user2BookRepository;

    public BookService(BookRepository bookRepository, User2BookRepository user2BookRepository) {
        this.bookRepository = bookRepository;
        this.user2BookRepository = user2BookRepository;
    }

    public Book getBookById(int id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.isAvailable() && book.getAmount() > 0)
                .collect(Collectors.toList());
    }

    public Book borrowBook(Book book, User user) throws BookAlreadyBorrowedByUserException, BookLimitException {
        checkIfUserCanBorrowMoreBooks(user);
        checkIfBookIsAvailable(book, user);
        book.setAmount(book.getAmount() - 1);
        if (book.getAmount() < 1) book.setAvailable(false);
        bookRepository.save(book);
        return book;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    private Book checkIfBookIsAvailable(Book book, User user) throws BookAlreadyBorrowedByUserException {
        if (book.getAmount() < 1 || !book.isAvailable()) {
            throw new BookAlreadyBorrowedByUserException(book, user);
        }
        return book;
    }

    private void checkIfUserCanBorrowMoreBooks(User user) throws BookLimitException {
        List<User2Book> user2Books = user2BookRepository.findAllByUser(user);
        if (user2Books.size() >= BOOKS_LIMIT) {
            throw new BookLimitException(BOOKS_LIMIT);
        }
    }

}
