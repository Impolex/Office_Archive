package com.chelchowskidawidjan;

import java.util.ArrayList;

public class File {
    String objectName;
    final String ID;
    User uploader;
    FileType fileType;
    int creationDate;
    int modificationDate;
    ArrayList<Comment> comments;

    public File(String id) {
        ID = id;
    }
}
