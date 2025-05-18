package com.chelchowskidawidjan;

import java.util.UUID;

public class JooqUserObject extends AbstractPluginUser{
    JooqUserObject(String uuid, String objectName, boolean isAdmin) {
        super(uuid, objectName, isAdmin);
    }

    @Override
    String getUUID() {
        return this.uuid;
    }

    @Override
    String getObjectName() {
        return this.objectName;
    }

    String[] getUUIDArray() {
        return new String[]{this.uuid};
    }

    String[] getObjectNameArray() {
        return new String[]{this.objectName};
    }

    @Override
    boolean isAdmin() {
        return this.isAdmin;
    }

    @Override
    void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
