package com.library.bcd.librarybcd.utils;

import com.library.bcd.librarybcd.entity.AngularUser;
import com.library.bcd.librarybcd.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class Convert {

    public static AngularUser user2AngularUser(User user) {
        AngularUser angularUser = new AngularUser();
        angularUser.setId(user.getId());
        angularUser.setLogin(user.getLogin());
        angularUser.setName(user.getName());
        angularUser.setLastName(user.getLastName());
        angularUser.setEmail(user.getEmail());
        angularUser.setBookLimit(user.getBookLimit());
        angularUser.setEnabled(user.isEnabled());
        angularUser.setAdmin(user.isAdmin());
        return angularUser;
    }

    public static List<AngularUser> userList2AngularUserList(List<User> users) {
        return users.stream()
                .map(Convert::user2AngularUser)
                .collect(Collectors.toList());
    }
}
