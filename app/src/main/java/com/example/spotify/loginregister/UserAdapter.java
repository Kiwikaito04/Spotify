package com.example.spotify.loginregister;

public class UserAdapter {
    private String Email;
    private String Username;
    private String Password;
    public UserAdapter(String email, String username, String password) {
        Email = email;
        Username = username;
        Password = password;
    }
    public UserAdapter() {
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
}
