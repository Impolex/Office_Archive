package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractPluginComment {
    private String commentUUID;
    private String authorUUID;
    private String fileUUID;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    AbstractPluginComment(String commentUUID, String authorUUID, String fileUUID, String content, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.commentUUID = commentUUID;
        this.authorUUID = authorUUID;
        this.fileUUID = fileUUID;
        this.content = content;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    AbstractPluginComment(UUID commentUUID, UUID authorUUID, UUID fileUUID, String content, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.commentUUID = commentUUID.toString();
        this.authorUUID = authorUUID.toString();
        this.fileUUID = fileUUID.toString();
        this.content = content;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    abstract String getCommentUUID();

    abstract String getAuthorUUID();

    abstract String getFileUUID();

    abstract String getContent();

    abstract LocalDateTime getCreationDate();

    abstract LocalDateTime getModificationDate();

    abstract void setContent(String content);

    abstract void setAuthor(String authorUUID);

    abstract void setFile(String fileUUID);

    abstract void setCommentUUID(String commentUUID);

    abstract void setCreationDate(LocalDateTime creationDate);

    abstract void setModificationDate(LocalDateTime modificationDate);
}
