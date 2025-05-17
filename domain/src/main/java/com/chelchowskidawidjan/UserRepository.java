package com.chelchowskidawidjan;

import java.util.Optional;

public interface UserRepository {
    boolean persistUserCreation(String[] objectName, String[] UUID, String[] passwordHash, String[] passwordSalt);

    boolean persistUserDeletion(String[] UUID);

    boolean persistUserUpdate(String[] objectName, String[] UUID, String[] passwordHash, String[] passwordSalt);

    Optional<User> fetchUserByUUID(String UUID);

    Iterable<User> fetchAllUsers();

    Iterable<User> fetchAllAdmins();

    Iterable<User> fetchUsersWithAccessToFile(String fileUUID);
}
