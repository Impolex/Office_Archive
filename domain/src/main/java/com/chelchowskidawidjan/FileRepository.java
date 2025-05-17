package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileRepository {
    boolean persistFileUpload(String[] fileUUID, String[] objectName, FileType fileType, String[] uploaderUUID, LocalDateTime creationDate, byte[] content);

    boolean persistFileEdit(String[] fileUUID, LocalDateTime editDate, byte[] content);

    boolean persistFileDeletion(String[] UUID);

    byte[] fetchFileContent(String[] UUID);

    List<String[]> fetchFilesforUser(String[] userUUID);

    Optional<File> fetchFile(String[] UUID);
}
