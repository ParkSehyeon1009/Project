package com.example.book.UserService;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private boolean success;

    private String message;

    @SerializedName("user_id")
    private int userId; // JSON의 user_id와 매핑

    // Getter and Setter for success
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                '}';
    }
}
