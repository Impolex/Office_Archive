package com.chelchowskidawidjan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;
    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    File getFileByUUID(UUID UUID){
        return fileRepository.fetchFileByUUID(UUID);
    }

    boolean uploadFile(String fileName, UUID uploaderUUID, FileType fileType, byte[] fileContent) {
        File file = new File(fileName, uploaderUUID, fileType, fileContent);
        return fileRepository.persistFileUpload(file);
    }

    boolean editFile(File file, byte[] fileContent) {
        file.modifyContent(fileContent);
        return fileRepository.persistFileEdit(file);
    }

    boolean deleteFile(UUID UUID) {
        return fileRepository.persistFileDeletion(UUID);
    }

    Map<String, UUID> getFilesForUser(UUID userUUID){
        return fileRepository.fetchFilesForUser(userUUID);
    }

}
