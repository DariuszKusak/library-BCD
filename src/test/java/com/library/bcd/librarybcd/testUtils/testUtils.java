package com.library.bcd.librarybcd.testUtils;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.library.bcd.librarybcd.utils.Convert.user2AngularUser;

public class testUtils {

    public static User createTestUser() {
        return new User(1, "testLogin", "Darek", "Testowy", "darek.testowy@example.com",
                "password", true, 1, false);
    }

    public static User createTestUser2() {
        return new User(2, "testLogin2", "Darek2", "Testowy2", "darek2.testowy2@example.com",
                "password2", true, 2, false);
    }

    public static User createTestUser3() {
        return new User(1, "testLogin3", "Darek3", "Testowy3", "darek3.testowy3@example.com",
                "password3", true, 3, false);
    }

    public static User createTestUser4() {
        return new User(4, "testLogin4", "Darek4", "Testowy4", "darek4.testowy4@example.com",
                "password4", true, 4, false);
    }

    public static Book createTestBook() {
        return new Book(1, "title1", "author", "desc1", "img1", true, "genre", 2000, 3);
    }

    public static Book createTestBook2() {
        return new Book(2, "title2", "author2", "desc2", "img2", true, "genre", 2000, 3);
    }

    public static Book createTestBook3() {
        return new Book(3, "title3", "author3", "desc3", "img3", true, "genre", 2000, 3);
    }

    public static User2Book createUser2Book() {
        return new User2Book(1, createTestBook(), createTestUser());
    }

    public static User2Book createUser2Book2() {
        return new User2Book(2, createTestBook2(), createTestUser());
    }

    public static User2Book createUser2Book3() {
        return new User2Book(3, createTestBook3(), createTestUser());
    }

    public static List<Book> createBookList() {
        return Stream.of(createTestBook(), createTestBook2(), createTestBook3()).collect(Collectors.toList());
    }

    public static List<User2Book> createUserBooks() {
        return Stream.of(createUser2Book(), createUser2Book2(), createUser2Book3()).collect(Collectors.toList());
    }

    public static List<User> createTestUserList() {
        return Stream.of(createTestUser(), createTestUser2(), createTestUser3()).collect(Collectors.toList());
    }

    public static AngularUser createAngularTestUser() {
        return user2AngularUser(createTestUser());
    }

    public static AngularUser createAngularTestUser4() {
        return user2AngularUser(createTestUser4());
    }


}
