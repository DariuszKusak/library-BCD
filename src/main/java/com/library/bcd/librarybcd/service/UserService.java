package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.UserNotFoundException;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByLogin(String login) throws UserNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    public List<User> getUsers() {
        return userRepository.findAll().stream().filter(u -> u.getRole().equals("USER")).collect(Collectors.toList());
    }

    public User updateUser(User user) throws UserNotFoundException {
        User updatedUser = getUserByLogin(user.getLogin());
        updatedUser.setId(user.getId());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRole(user.getRole());
        updatedUser.setBookLimit(user.getBookLimit());
        userRepository.saveAndFlush(updatedUser);
        return updatedUser;
    }

}
