package com.library.bcd.librarybcd.service;

import com.library.bcd.librarybcd.entity.User;
import com.library.bcd.librarybcd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authorizeUser(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User: %s with password: %s does not exist.", login, password)));
    }
}
