package com.example.book.UserService;

public class LoginRequest {
    private String email;
    private String password;

    // 생성자
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter for ID
    public String getEmail() {
        return email;
    }

    // Setter for ID
    public void setID(String email) {
        this.email = email;
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
