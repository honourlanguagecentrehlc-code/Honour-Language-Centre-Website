package com.example.LoginApp;

public class User {
    private String username;
    private String password;
    private String classType; // "A" or "B" or "" (unassigned)

    public User() {}

    public User(String username, String password, String classType) {
        this.username = username;
        this.password = password;
        this.classType = classType;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
}
