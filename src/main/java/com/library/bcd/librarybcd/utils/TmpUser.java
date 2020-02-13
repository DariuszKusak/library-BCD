package com.library.bcd.librarybcd.utils;

import com.library.bcd.librarybcd.entity.User;

public class TmpUser {

    public static User getTmpUser() {
        return new User(1, "d_user", "123", "ROLE");
    }
}
