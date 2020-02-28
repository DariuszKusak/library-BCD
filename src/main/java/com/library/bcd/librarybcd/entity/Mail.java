package com.library.bcd.librarybcd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
}
