package com.chelchowskidawidjan;

import java.util.UUID;

public interface PermissionsRepository {
    boolean persistUserFilePermissions(UUID userUUID, UUID fileUUID, Permissions permissions);

    boolean persistUserFilePermissionsUpdate(UUID userUUID, UUID fileUUID, Permissions permissions);

    Permissions fetchUserFilePermissions(UUID userUUID, UUID fileUUID);
}
