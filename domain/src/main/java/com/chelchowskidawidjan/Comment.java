package com.chelchowskidawidjan;

import java.time.LocalDateTime;

public class Comment {
    private final User author;
    private String comment;
    private final LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private final File file;

    public Comment(final User author, String comment, final File file) {
        this.author = author;
        this.comment = comment;
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = creationDate;
        this.file = file;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getComment() {
        return comment;
    }

    public File getFile() {
        return file;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setComment(String comment) {
        if(comment != null && comment.length() <= 256){
            this.comment = comment;
        }
    }

    public void updateComment(String comment) {
        if(comment != null && comment.length() <= 256){
            this.comment = comment;
            this.modifiedDate = LocalDateTime.now();
        }
    }
}
