package com.chelchowskidawidjan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UUID UUID) {
        return this.userRepository.fetchUserByUUID(UUID);
    }

    public boolean createUser(String username, String password) {
        try{
            User user = new User(username, false);
            byte[] salt = new byte[12];
            new SecureRandom().nextBytes(salt);
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);
            password = Arrays.toString(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
            return this.userRepository.persistUserCreation(user, password, Arrays.toString(salt));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean login(String username, String password) {
        return this.userRepository.login(username, password);
    }

    public boolean deleteUser(User invokingUser, UUID UUID) {
        if(invokingUser.getID() == UUID || invokingUser.isAdmin()) {
            return this.userRepository.persistUserDeletion(UUID);
        }
        else{
            return false;
        }
    }

    public boolean changeUserName(User invokingUser, UUID UUID, String newName) {
        if (invokingUser.getID() == UUID || invokingUser.isAdmin()) {
            User targetUser = this.userRepository.fetchUserByUUID(UUID);
            targetUser.setObjectName(newName);
            return this.userRepository.persistUserUpdate(targetUser);
        }
        else {
            return false;

        }
    }

    public boolean changePassword(User invokingUser, UUID UUID, String password) {
        try {
            if (invokingUser.getID() == UUID || invokingUser.isAdmin()) {
                byte[] salt = new byte[12];
                new SecureRandom().nextBytes(salt);
                MessageDigest digest = MessageDigest.getInstance("SHA-512");
                digest.update(salt);
                password = Arrays.toString(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
                return this.userRepository.persistUserPasswordUpdate(UUID, password, Arrays.toString(salt));
            }
            else {
                return false;

            }
        }
        catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
        }
    }

    public boolean changeUserAdminStatus(User invokingUser, UUID UUID, boolean adminStatus) {
        if(invokingUser.isAdmin()) {
            User targetUser = this.userRepository.fetchUserByUUID(UUID);
            targetUser.setAdmin(adminStatus);
            return this.userRepository.persistUserUpdate(targetUser);
        }
        else{
            return false;
        }
    }

    public List<User> getAllUsers() {
        return this.userRepository.fetchAllUsers();
    }

    public List<User> getAllAdminUsers() {
        return this.userRepository.fetchAllAdmins();
    }
}
