package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.repository.BookRepository;
import com.library.bcd.librarybcd.repository.User2BookRepository;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class User2BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final User2BookRepository user2BookRepository;

    @Autowired
    public User2BookService(UserRepository userRepository, BookRepository bookRepository, User2BookRepository user2BookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.user2BookRepository = user2BookRepository;
    }

    public List<Book> getBooksForUser(User user) {
        List<User2Book> user2Books = user2BookRepository.findAllByUser(user);
        List<Book> books = new ArrayList<>();
        for (User2Book u2b : user2Books) {
            books.add(u2b.getBook());
        }
        return books;
    }

    public User2Book borrowBookForUser(User user, Book book) {
        User2Book u2b = new User2Book(0, book, user);
        user2BookRepository.save(u2b);
        return u2b;
    }

    public void returnBook(User user, Book book) {
        user2BookRepository.deleteByUserAndBook(user, book);
        book.setAmount(book.getAmount() + 1);
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public void returnUserBooks(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
        List<User2Book> userBooks = user2BookRepository.findAllByUser(user);
        for (User2Book u2b : userBooks) {
            user2BookRepository.delete(u2b);
        }
    }

}
