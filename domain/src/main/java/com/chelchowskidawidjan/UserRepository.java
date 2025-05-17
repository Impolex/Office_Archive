package com.chelchowskidawidjan;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    boolean persistUserCreation(User user, String passwordHash, String salt);

    boolean persistUserDeletion(UUID UUID);

    boolean persistUserUpdate(User user);

    boolean persistUserPasswordUpdate(UUID UUID, String passwordHash, String salt);

    User fetchUserByUUID(UUID UUID);

    List<User> fetchAllUsers();

    List<User> fetchAllAdmins();

    List<User> fetchUsersWithAccessToFile(UUID fileUUID);
}
