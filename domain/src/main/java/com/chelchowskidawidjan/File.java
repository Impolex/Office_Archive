package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.UUID;

public class File {
    private String objectName;
    private final UUID ID;
    private final User uploader;
    private final FileType fileType;
    private final LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private byte[] content;

    public File(String objectName, final User uploader, FileType fileType, byte[] content) {
        ID = UUID.randomUUID();
        setObjectName(objectName);
        this.uploader = uploader;
        this.fileType = fileType;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = this.creationDate;
        this.content = content;
    }

    public String getObjectName() {
        return objectName;
    }

    public UUID getID() {
        return ID;
    }

    public User getUploader() {
        return uploader;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public FileType getFileType() {
        return fileType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public byte[] getContent() {
        return content;
    }

    public void setObjectName(String objectName) {
        if(!objectName.isEmpty() && objectName.length() <= 30){
            this.objectName = objectName;
        }
    }

    public void modifyContent(byte[] content) {
        if(content != null && content.length > 0){
            this.content = content;
            this.modificationDate = LocalDateTime.now();
        }
    }
}
