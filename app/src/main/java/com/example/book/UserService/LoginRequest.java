package com.example.book.UserService;

public class LoginRequest {
    private String user_id;
    private String password;

    // 생성자
    public LoginRequest(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }

    // Getter for ID
    public String getEmail() {
        return user_id;
    }

    // Setter for ID
    public void setID(String user_id) {
        this.user_id = user_id;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}
