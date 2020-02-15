package com.library.bcd.librarybcd.repository;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User2BookRepository extends JpaRepository<User2Book, Integer> {

    List<User2Book> findAllByUserId(User userId);
    List<User2Book> findAllByUser(User user);
    List<User2Book> findAllByUserAndBook(User user, Book book);
    void deleteByUserAndBook(User user,Book book);
}
