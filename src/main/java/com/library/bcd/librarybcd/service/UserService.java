package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.LoginAlreadyTakenException;
import com.library.bcd.librarybcd.exception.UserNotFoundException;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByLogin(String login) throws UserNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    public List<User> getUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.isAdmin())
                .collect(Collectors.toList());
    }

    public User createUser(AngularUser user, String password) throws LoginAlreadyTakenException {
        checkIfUserLoginAvailable(user.getLogin());
        User newUser = new User();
        newUser.setId(0);
        newUser.setLogin(user.getLogin());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(encodePassword(password));
        newUser.setBookLimit(user.getBookLimit());
        newUser.setAdmin(false);
        newUser.setEmail(user.getEmail());
        newUser.setEnabled(true);
        userRepository.save(newUser);
        return newUser;
    }

    public User updateUser(AngularUser angularUser) throws UserNotFoundException {
        User updatedUser = getUserByLogin(angularUser.getLogin());
        updatedUser.setName(angularUser.getName());
        updatedUser.setLastName(angularUser.getLastName());
        updatedUser.setEmail(angularUser.getEmail());
        updatedUser.setBookLimit(angularUser.getBookLimit());
        userRepository.saveAndFlush(updatedUser);
        return updatedUser;
    }

    public User deleteUser(String login) throws UserNotFoundException {
        User user = getUserByLogin(login);
        userRepository.delete(user);
        return user;
    }

    public User setStatus(String login, boolean enabled) throws UserNotFoundException {
        User user = getUserByLogin(login);
        user.setEnabled(enabled);
        userRepository.saveAndFlush(user);
        return user;
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private void checkIfUserLoginAvailable(String login) throws LoginAlreadyTakenException {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                throw new LoginAlreadyTakenException(login);
            }
        }
    }


}
