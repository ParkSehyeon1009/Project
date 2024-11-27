package com.example.book.UserService;
import com.google.gson.annotations.SerializedName;

public class User {
    private int id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String username;

    @SerializedName("student_num")
    private int studentNum;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    // 기본 생성자
    public User() {
    }

    // 회원가입용 생성자
    public User(String userId, String username, int studentNum, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.studentNum = studentNum;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

