package com.chelchowskidawidjan;

public class User {
    String objectName;
    final String ID;
    boolean isAdmin;


    public User(String id, String objectName, boolean isAdmin) {
        ID = id;
        this.objectName = objectName;
        this.isAdmin = isAdmin;
    }
}
