package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.LoginAlreadyTakenException;
import com.library.bcd.librarybcd.exception.UserNotFoundException;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Stream;

import static com.library.bcd.librarybcd.testUtils.testUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private String NOT_EXISTING_LOGIN = "Login_does_not_exist";
    private String PASSWORD = "password";

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    UserService userService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        User user = createTestUser();
        when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(java.util.Optional.of(user));
    }

    @ParameterizedTest
    @ArgumentsSource(GetUserByLoginSuccessArgs.class)
    public void testGetUserByLoginSuccess(String login, User user) throws UserNotFoundException {
        when(userRepositoryMock.findByLogin(login)).thenReturn(java.util.Optional.of(user));
        User result = userService.getUserByLogin(login);

        verify(userRepositoryMock).findByLogin(login);
        assertEquals(login, result.getLogin());
    }

    @Test
    public void testGetUserByLoginError() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserByLogin(NOT_EXISTING_LOGIN));
    }

    @Test
    public void testGetUsersSuccess() {
        when(userRepositoryMock.findAll()).thenReturn(createTestUserList());
        List<User> userList = userService.getUsers();

        verify(userRepositoryMock).findAll();
        assertThat(userList, hasSize(3));
    }

    @Ignore
    @Test
    public void testCreateUserSuccess() throws LoginAlreadyTakenException {
        when(userRepositoryMock.findAll()).thenReturn(createTestUserList());
        when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        userService.createUser(createAngularTestUser4(), PASSWORD);
    }

    @Test
    public void testCrateUserError() {
        when(userRepositoryMock.findAll()).thenReturn(createTestUserList());

        assertThrows(LoginAlreadyTakenException.class, () -> userService.createUser(createAngularTestUser(), PASSWORD));
    }

    @Test
    public void testUpdateUserSuccess() throws UserNotFoundException {
        User user = createTestUser();
        when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(java.util.Optional.of(user));
        User updatedUser = userService.updateUser(createAngularTestUser());

        verify(userRepositoryMock).findByLogin(updatedUser.getLogin());
        assertEquals(user.getLogin(), updatedUser.getLogin());
    }

    @Test
    public void testUpdateUserError() {
        assertThrows(UserNotFoundException.class, () ->
                userService.updateUser(new AngularUser(1, NOT_EXISTING_LOGIN, "", "", "", 1, true, false)));
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        User user = createTestUser();
        when(userRepositoryMock.findByLogin(user.getLogin())).thenReturn(java.util.Optional.of(user));

        userService.deleteUser(user.getLogin());
        verify(userRepositoryMock).findByLogin(user.getLogin());
    }

    @Test
    public void testDeleteUserError() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(NOT_EXISTING_LOGIN));
    }

    @ParameterizedTest
    @ArgumentsSource(SetStatusSuccessArgs.class)
    public void testSetStatusSuccess(boolean expectedStatus) throws Exception {
        User updatedUser = userService.setStatus(createTestUser().getLogin(), expectedStatus);

        verify(userRepositoryMock).findByLogin(updatedUser.getLogin());
        assertEquals(expectedStatus, updatedUser.isEnabled());
    }

    @Test
    public void testSetStatusErrorArgs() {
        assertThrows(UserNotFoundException.class, () -> userService.setStatus(NOT_EXISTING_LOGIN, true));
    }


}

class GetUserByLoginSuccessArgs implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                /* login, User */
                Arguments.of("testLogin", createTestUser()),
                Arguments.of("testLogin2", createTestUser2()),
                Arguments.of("testLogin3", createTestUser3())
        );
    }
}

class SetStatusSuccessArgs implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                /* expectedStatus */
                Arguments.of(true, false)
        );
    }
}