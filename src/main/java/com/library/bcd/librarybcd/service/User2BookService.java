package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.exception.BookAlreadyBorrowedByUserException;
import com.library.bcd.librarybcd.repository.BookRepository;
import com.library.bcd.librarybcd.repository.User2BookRepository;
import com.library.bcd.librarybcd.utils.TmpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class User2BookService {

    private final User2BookRepository user2BookRepository;
    private final BookRepository bookRepository;

    @Autowired
    public User2BookService(User2BookRepository user2BookRepository, BookRepository bookRepository) {
        this.user2BookRepository = user2BookRepository;
        this.bookRepository = bookRepository;

    }

    public User2Book borrowUser4Book(User user, Book book) {
        User2Book u2b = new User2Book(0, book, user);
        saveU2B(u2b);
        return u2b;
    }

    public void saveU2B(User2Book u2b) {
        user2BookRepository.save(u2b);
    }

    public List<Book> getBooksForUser(User user) {
        List<User2Book> user2Books = user2BookRepository.findAllByUser(user);
        List<Book> books = new ArrayList<>();
        for (User2Book u2b : user2Books) {
            books.add(u2b.getBook());
        }
        return books;
    }

    public void checkIfBookDoNotDuplicate(User user, Book book) throws BookAlreadyBorrowedByUserException {
        List<User2Book> user2Books = user2BookRepository.findAllByUserAndBook(user, book);
        if (user2Books.size() != 0) {
            throw new BookAlreadyBorrowedByUserException(book, TmpUser.getTmpUser());
        }
    }

    public void returnBook(User user, Book book) {
        user2BookRepository.deleteByUserAndBook(user, book);
        book.setAmount(book.getAmount() + 1);
        book.setAvailable(true);
        bookRepository.save(book);
    }

}
