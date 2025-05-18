package com.chelchowskidawidjan;

import java.util.UUID;

public class User {
    private String objectName;
    private final UUID ID;
    private boolean isAdmin;


    public User(String objectName, boolean isAdmin) {
        this.ID = UUID.randomUUID();
        this.isAdmin = isAdmin;
        setObjectName(objectName);
    }

    public User(UUID uuid, String objectName, boolean isAdmin) {
        this.ID = uuid;
        this.isAdmin = isAdmin;
        setObjectName(objectName);
    }

    public String getObjectName() {
        return objectName;
    }

    public UUID getID() {
        return ID;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setObjectName(String objectName) {
        if(!objectName.isEmpty() && objectName.length() <= 20) {
            this.objectName = objectName;
        }
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
