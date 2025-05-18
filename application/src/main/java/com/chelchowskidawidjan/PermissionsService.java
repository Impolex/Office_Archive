package com.chelchowskidawidjan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PermissionsService {

    private final PermissionsRepository permissionsRepository;
    @Autowired
    public PermissionsService(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    boolean setUserFilePermissions(UUID userUUID, UUID fileUUID, Permissions permissions) {
        return permissionsRepository.persistUserFilePermissions(userUUID, fileUUID, permissions);
    }

    Permissions getUserFilePermissions(UUID userUUID, UUID fileUUID) {
        return permissionsRepository.fetchUserFilePermissions(userUUID, fileUUID);
    }

}
