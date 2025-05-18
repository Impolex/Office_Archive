package com.chelchowskidawidjan;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileAdapter {
    public FileAdapter(){

    }

    public File pluginToDomain(AbstractPluginFile pluginFile){
        UUID uuid = UUID.fromString(pluginFile.getUUID());
        String objectName = pluginFile.getObjectName();
        String uploaderUUID = pluginFile.getUploaderUUID();
        FileType fileType = pluginFile.getType();
        byte[] content = pluginFile.getContent();
        LocalDateTime creationDate = pluginFile.getCreationDate();
        LocalDateTime modificationDate = pluginFile.getModificationDate();

        return new File(uuid, objectName, UUID.fromString(uploaderUUID), fileType, content, creationDate, modificationDate);
    }

    public <T extends AbstractPluginFile> T domainToPlugin(File domainFile, Class<T> pluginFileClass){
        try{
            String fileUUID = domainFile.getID().toString();
            String objectName = domainFile.getObjectName();
            String uploaderUUID = domainFile.getUploaderUUID().toString();
            FileType fileType = domainFile.getFileType();
            byte[] content = domainFile.getContent();
            LocalDateTime creationDate = domainFile.getCreationDate();
            LocalDateTime modificationDate = domainFile.getModificationDate();
            Constructor<T> constructor = pluginFileClass.getConstructor(String.class, String.class, String.class, LocalDateTime.class, LocalDateTime.class, FileType.class, byte[].class);

            return constructor.newInstance(fileUUID, objectName, uploaderUUID, creationDate, modificationDate, fileType, content);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
