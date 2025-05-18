package com.chelchowskidawidjan;

import java.time.LocalDateTime;

public class JooqFileObject extends AbstractPluginFile{
    JooqFileObject(String fileUUID, String objectName, String uploaderUUID, LocalDateTime creationDate, LocalDateTime modificationDate, FileType fileType, byte[] content) {
        super(fileUUID,
                objectName,
                uploaderUUID,
                creationDate,
                modificationDate,
                fileType,
                content);
    }

    @Override
    FileType getType() {
        return this.fileType;
    }

    @Override
    byte[] getContent() {
        return this.content;
    }

    @Override
    String getObjectName() {
        return this.objectName;
    }

    String[] getObjectNameArray() {
        return new String[]{this.objectName};
    }

    @Override
    String getUploaderUUID() {
        return this.uploaderUUID;
    }

    String[] getUploaderUUIDArray() {
        return new String[]{this.uploaderUUID};
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
    String getUUID() {
        return this.fileUUID;
    }

    String[] getUUIDArray() {
        return new String[]{this.fileUUID};
    }

    @Override
    void setFileType(FileType type) {
        this.fileType = type;
    }

    @Override
    void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    void setUploaderUUID(String uploaderUUID) {
        this.uploaderUUID = uploaderUUID;
    }

    @Override
    void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    void setUUID(String uuid) {
        this.fileUUID = uuid;
    }
}
