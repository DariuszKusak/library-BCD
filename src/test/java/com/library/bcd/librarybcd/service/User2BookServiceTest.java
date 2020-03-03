package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.Book;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.entity.User2Book;
import com.library.bcd.librarybcd.repository.BookRepository;
import com.library.bcd.librarybcd.repository.User2BookRepository;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.library.bcd.librarybcd.testUtils.testUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class User2BookServiceTest {

    @Mock
    User2BookRepository user2BookRepositoryMock;
    @Mock
    BookRepository bookRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    User2BookService user2BookService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBooksForUserSuccess() {
        User user = createTestUser();
        when(bookRepositoryMock.findBooksForUser(user)).thenReturn(createBookList());
        List<Book> userBooks = user2BookService.getBooksForUser(user);

        assertEquals(3, userBooks.size());
    }

    @Test
    public void testReturnBookSuccess() {
        User user = createTestUser();
        Book book = createTestBook();
        int amountBeforeReturn = book.getAmount();
        user2BookService.returnBook(user, book);
        int amountAfterReturn = book.getAmount();

        assertEquals(++amountBeforeReturn, amountAfterReturn);
    }

    @Test
    public void testReturnUsersBooks() {
        User user = createTestUser();
        when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(java.util.Optional.of(user));
        user2BookService.returnUserBooks(user.getLogin());

        assertEquals(0, bookRepositoryMock.findBooksForUser(user).size());
    }

}
