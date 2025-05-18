package com.chelchowskidawidjan;

import java.time.LocalDateTime;

public class JooqCommentObject extends AbstractPluginComment{
    JooqCommentObject(String commentUUID, String authorUUID, String fileUUID, String content, LocalDateTime creationDate, LocalDateTime modificationDate) {
        super(commentUUID, authorUUID, fileUUID, content, creationDate, modificationDate);
    }

    @Override
    String getCommentUUID() {
        return this.commentUUID;
    }

    @Override
    String getAuthorUUID() {
        return this.authorUUID;
    }

    @Override
    String getFileUUID() {
        return this.fileUUID;
    }

    @Override
    String getContent() {
        return this.content;
    }

    @Override
    LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    @Override
    LocalDateTime getModificationDate() {
        return this.modificationDate;
    }

    @Override
    void setContent(String content) {
        this.content = content;
    }

    @Override
    void setAuthor(String authorUUID) {
        this.authorUUID = authorUUID;
    }

    @Override
    void setFile(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    @Override
    void setCommentUUID(String commentUUID) {
        this.commentUUID = commentUUID;
    }

    @Override
    void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
