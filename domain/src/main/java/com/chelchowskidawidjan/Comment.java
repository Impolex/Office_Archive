package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private final UUID authorUUID;
    private final UUID uuid;
    private String comment;
    private final LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private final UUID fileUUID;

    public Comment(final UUID authorUUID, String comment, final UUID fileUUID) {
        this.uuid = UUID.randomUUID();
        this.authorUUID = authorUUID;
        this.comment = comment;
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = creationDate;
        this.fileUUID = fileUUID;
    }

    public Comment(final UUID uuid, final UUID authorUUID, String comment, final UUID fileUUID, LocalDateTime creationDate, LocalDateTime modifiedDate) {
        this.uuid = UUID.randomUUID();
        this.authorUUID = authorUUID;
        this.comment = comment;
        this.creationDate = creationDate;
        this.modifiedDate = modifiedDate;
        this.fileUUID = fileUUID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getAuthorUUID() {
        return authorUUID;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getComment() {
        return comment;
    }

    public UUID getFileUUID() {
        return fileUUID;
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
