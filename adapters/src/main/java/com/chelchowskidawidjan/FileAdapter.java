package com.chelchowskidawidjan;

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

    public void domainToPlugin(File file, AbstractPluginFile pluginFile){
        pluginFile.setUUID(file.getID().toString());
        pluginFile.setObjectName(file.getObjectName());
        pluginFile.setUploaderUUID(file.getUploaderUUID().toString());
        pluginFile.setCreationDate(file.getCreationDate());
        pluginFile.setModificationDate(file.getModificationDate());
        pluginFile.setFileType(file.getFileType());
        pluginFile.setContent(file.getContent());
    }
}
