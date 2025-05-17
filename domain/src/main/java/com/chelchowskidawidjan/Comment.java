package com.chelchowskidawidjan;

import java.time.LocalDateTime;

public class Comment {
    User author;
    String comment;
    LocalDateTime creationDate;
    File file;

    public Comment(User author, String comment, LocalDateTime creationDate, File file) {
        this.author = author;
        this.comment = comment;
        this.creationDate = creationDate;
        this.file = file;
    }
}
