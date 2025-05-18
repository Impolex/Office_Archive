package com.chelchowskidawidjan;

import java.util.UUID;

public class UserAdapter {
    public UserAdapter() {
    }

    public User pluginToDomain(AbstractPluginUser pluginUser){
        String objectName = pluginUser.getObjectName();
        UUID uuid = UUID.fromString(pluginUser.getUUID());
        boolean isAdmin = pluginUser.isAdmin();

        return new User(uuid, objectName, isAdmin);
    }

    public void domainToPlugin(User domainUser, AbstractPluginUser pluginUser){
        pluginUser.setObjectName(domainUser.getObjectName());
        pluginUser.setUUID(domainUser.getID().toString());
        pluginUser.setAdmin(domainUser.isAdmin());
    }
}
