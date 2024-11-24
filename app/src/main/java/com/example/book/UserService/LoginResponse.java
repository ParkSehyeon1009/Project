package com.example.book.UserService;

public class LoginResponse {
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
