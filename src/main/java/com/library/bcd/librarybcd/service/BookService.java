package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.exception.BookAlreadyBorrowedByUserException;
import com.library.bcd.librarybcd.exception.BookAlreadyExists;
import com.library.bcd.librarybcd.exception.BookLimitException;
import com.library.bcd.librarybcd.exception.BookNotFoundException;
import com.library.bcd.librarybcd.repository.BookRepository;
import com.library.bcd.librarybcd.repository.User2BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final User2BookRepository user2BookRepository;

    public BookService(BookRepository bookRepository, User2BookRepository user2BookRepository) {
        this.bookRepository = bookRepository;
        this.user2BookRepository = user2BookRepository;
    }

    public Book getBookById(int id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book borrowBook(Book book, User user) throws BookAlreadyBorrowedByUserException, BookLimitException {
        checkIfBookBorrowedAlready(user, book);
        checkIfUserCanBorrowMoreBooks(user);
        checkIfBookIsAvailable(book);
        book.setAmount(book.getAmount() - 1);
        if (book.getAmount() < 1) book.setAvailable(false);
        bookRepository.save(book);
        return book;
    }

    public Book addBook(Book book) throws BookAlreadyExists {
        checkIfBookExistAlready(book);
        book.setId(0);
        bookRepository.save(book);
        return book;
    }

    private Book checkIfBookIsAvailable(Book book) throws BookAlreadyBorrowedByUserException {
        if (book.getAmount() < 1 || !book.isAvailable()) {
            throw new BookAlreadyBorrowedByUserException(book.getTitle());
        }
        return book;
    }

    private void checkIfUserCanBorrowMoreBooks(User user) throws BookLimitException {
        List<User2Book> user2Books = user2BookRepository.findAllByUser(user);
        if (user2Books.size() >= user.getBookLimit()) {
            throw new BookLimitException(user.getBookLimit());
        }
    }

    private void checkIfBookBorrowedAlready(User user, Book book) throws BookAlreadyBorrowedByUserException {
        List<User2Book> user2Books = user2BookRepository.findAllByUserAndBook(user, book);
        if (user2Books.size() != 0) {
            throw new BookAlreadyBorrowedByUserException(book.getTitle());
        }
    }

    private void checkIfBookExistAlready(Book book) throws BookAlreadyExists {
        List<Book> books = bookRepository.findAll();
        for (Book iBook : books) {
            if (iBook.getTitle().equals(book.getTitle()) && iBook.getAuthor().equals(book.getAuthor()) &&
                    iBook.getYear().equals(book.getYear()) ) {
                throw new BookAlreadyExists(book);
            }
        }
    }

}
