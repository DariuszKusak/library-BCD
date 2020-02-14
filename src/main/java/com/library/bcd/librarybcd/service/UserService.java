package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.exception.UserWithPasswordDoesNotExists;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authorizeUser(String login, String password) throws UserWithPasswordDoesNotExists {
        return userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new UserWithPasswordDoesNotExists(login, password));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) throws UserWithPasswordDoesNotExists {
        User updatedUser = authorizeUser(user.getLogin(), user.getPassword());
        updatedUser.setId(user.getId());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRole(user.getRole());
        userRepository.saveAndFlush(updatedUser);
        return updatedUser;
    }
}
