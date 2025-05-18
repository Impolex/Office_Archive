package com.chelchowskidawidjan;

import java.util.UUID;

public abstract class AbstractPluginUser {
    private String uuid;
    private String objectName;
    private boolean isAdmin;

    AbstractPluginUser(String uuid, String objectName, boolean isAdmin) {
        this.uuid = uuid;
        this.objectName = objectName;
        this.isAdmin = isAdmin;
    }

    AbstractPluginUser(UUID uuid, String objectName, boolean isAdmin) {
        this.uuid = uuid.toString();
        this.objectName = objectName;
        this.isAdmin = isAdmin;
    }

    abstract String getUUID();

    abstract String getObjectName();

    abstract boolean isAdmin();

    abstract void setUUID(String uuid);

    abstract void setObjectName(String objectName);

    abstract void setAdmin(boolean isAdmin);
}