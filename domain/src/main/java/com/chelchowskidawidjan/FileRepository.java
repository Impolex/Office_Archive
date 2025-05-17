package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.List;

public interface FileRepository {
    boolean persistFileUpload(String[] fileUUID, String[] objectName, FileType fileType, String[] uploaderUUID, LocalDateTime creationDate, byte[] content);

    boolean persistFileEdit(String[] fileUUID, LocalDateTime editDate, byte[] content);

    boolean persistFileDeletion(String[] UUID);

    byte[] fetchFileContent(String[] UUID);

    List<String[]> fetchFilesforUser(String[] userUUID);

    File fetchFile(String[] UUID);
}
