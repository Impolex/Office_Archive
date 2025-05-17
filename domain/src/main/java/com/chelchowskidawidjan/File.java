package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class File {
    String objectName;
    final String ID;
    User uploader;
    FileType fileType;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;

    public File(String id, String objectName, User uploader, FileType fileType, LocalDateTime creationDate, LocalDateTime modificationDate) {
        ID = id;
        this.objectName = objectName;
        this.uploader = uploader;
        this.fileType = fileType;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }
}
