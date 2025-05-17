package com.chelchowskidawidjan;

public interface UserRepository {
    boolean persistUserCreation(String[] objectName, String[] UUID, String[] passwordHash, String[] passwordSalt);

    boolean persistUserDeletion(String[] UUID);

    boolean persistUserNameUpdate(String[] UUID, String[] Name);

    boolean persistUserPasswordUpdate(String[] UUID, String[] passwordHash, String[] passwordSalt);

    User fetchUserByUUID(String[] UUID);

    Iterable<User> fetchAllUsers();

    Iterable<User> fetchAllAdmins();

    Iterable<User> fetchUsersWithAccessToFile(String[] fileUUID);
}
