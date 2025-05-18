package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractPluginFile {
    private String fileUUID;
    private String objectName;
    private String uploaderUUID;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private FileType fileType;
    private byte[] content;

    AbstractPluginFile(String fileUUID, String objectName, String uploaderUUID, LocalDateTime creationDate, LocalDateTime modificationDate, FileType fileType, byte[] content) {
        this.fileUUID = fileUUID;
        this.objectName = objectName;
        this.uploaderUUID = uploaderUUID;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.fileType = fileType;
        this.content = content;
    }

    AbstractPluginFile(UUID fileUUID, String objectName, UUID uploaderUUID, LocalDateTime creationDate, LocalDateTime modificationDate, FileType fileType, byte[] content) {
        this.fileUUID = fileUUID.toString();
        this.objectName = objectName;
        this.uploaderUUID = uploaderUUID.toString();
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.fileType = fileType;
        this.content = content;
    }

    abstract FileType getType();

    abstract byte[] getContent();

    abstract String getObjectName();

    abstract String getUploaderUUID();

    abstract LocalDateTime getCreationDate();

    abstract LocalDateTime getModificationDate();

    abstract String getUUID();

    abstract void setFileType(FileType type);

    abstract void setContent(byte[] content);

    abstract void setObjectName(String objectName);

    abstract void setUploaderUUID(String uploaderUUID);

    abstract void setCreationDate(LocalDateTime creationDate);

    abstract void setModificationDate(LocalDateTime modificationDate);

    abstract void setUUID(String uuid);
}
