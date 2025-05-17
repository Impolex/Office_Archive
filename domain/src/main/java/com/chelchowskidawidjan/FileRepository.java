package com.chelchowskidawidjan;

import java.util.Map;
import java.util.UUID;

public interface FileRepository {
    boolean persistFileUpload(File file);

    boolean persistFileEdit(File file);

    boolean persistFileDeletion(UUID UUID);

    Map<String, UUID> fetchFilesForUser(UUID userUUID);

    File fetchFileByUUID(UUID UUID);
}
