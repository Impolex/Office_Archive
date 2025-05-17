package com.chelchowskidawidjan;

public interface PermissionsRepository {
    boolean persistUserFilePermissions(String[] userUUID, String[] fileUUID, Permissions permissions);

    boolean persistUserFilePermissionsUpdate(String[] userUUID, String[] fileUUID, Permissions permissions);

    Permissions fetchUserFilePermissions(String[] userUUID, String[] fileUUID);

    Iterable<Permissions> fetchAllUserPermissionsForFile(String[] userUUID, String[] fileUUID);
}
