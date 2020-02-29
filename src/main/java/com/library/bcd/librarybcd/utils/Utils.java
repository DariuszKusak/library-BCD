package com.library.bcd.librarybcd.utils;

import com.library.bcd.librarybcd.entity.Mail;
import com.library.bcd.librarybcd.entity.User;
import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
    private static final String LIBRARY_ADDRESS = "examples123.dk@gmail.com";
    private static final String SUBJECT = "Nowe hasło aplikacji library";
    private static final int PASSWORD_LENGTH = 5;

    public static Mail prepareMail(User user, String password) {
        return new Mail(LIBRARY_ADDRESS, user.getEmail(), LIBRARY_ADDRESS, SUBJECT, password);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(PASSWORD_LENGTH);
    }


}
