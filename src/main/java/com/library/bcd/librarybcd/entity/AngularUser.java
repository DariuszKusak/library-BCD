package com.library.bcd.librarybcd.entity;

import lombok.*;

@ToString
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
    private boolean enabled;
    private boolean isAdmin;

}
