package com.chelchowskidawidjan;

import java.lang.reflect.Constructor;
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

    public <T extends AbstractPluginUser> T domainToPlugin(User domainUser, Class<T> pluginUserClass){
        try{
            String objectName = domainUser.getObjectName();
            String uuid = domainUser.getID().toString();
            boolean isAdmin = domainUser.isAdmin();

            Constructor<T> constructor = pluginUserClass.getConstructor(String.class, String.class, boolean.class);

            return constructor.newInstance(uuid, objectName, isAdmin);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
