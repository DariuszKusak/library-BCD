package com.library.bcd.librarybcd.entity;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Mail {
    private String from;
    private String to;
    private String replyTo;
    private String subject;
    private String content;
}
