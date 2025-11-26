package com.example.LoginApp;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private final List<User> defaultUsers = Arrays.asList(
        new User("zaynhtet227", "2272008Zylh", "A"),
        new User("test", "test", "B"),
        new User("admin", "admin", "A")
    );

    public synchronized List<User> getAllUsers() {
        return new ArrayList<>(defaultUsers);
    }

    public synchronized void saveAllUsers(List<User> users) {
        // Do nothing - using hardcoded users
    }

    public synchronized boolean register(String username, String password) {
        for (User u : defaultUsers) {
            if (u.getUsername().equals(username)) return false;
        }
        return true;
    }

    public synchronized User findByUsername(String username) {
        for (User u : defaultUsers) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    public synchronized boolean validateCredentials(String username, String password) {
        User u = findByUsername(username);
        return u != null && u.getPassword().equals(password);
    }
}