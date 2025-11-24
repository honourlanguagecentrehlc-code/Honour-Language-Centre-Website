package com.example.LoginApp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("src/main/resources/data/users.json");

    public synchronized List<User> getAllUsers() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<User>());
            }
            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public synchronized void saveAllUsers(List<User> users) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean register(String username, String password) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) return false;
        }
        users.add(new User(username, password, ""));
        saveAllUsers(users);
        return true;
    }

    public synchronized User findByUsername(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    public synchronized boolean validateCredentials(String username, String password) {
        User u = findByUsername(username);
        return u != null && u.getPassword().equals(password);
    }
}
