package com.library.bcd.librarybcd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AngularUser {

    private int id;
    private String login;
    private String name;
    private String lastName;
    private String email;
    private Integer bookLimit;
    private boolean isAdmin;

}
