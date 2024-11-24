package com.example.book.UserService;

public class User {
    private int id; // 고유 사용자 ID (채팅에서 필요)
    private String user_id; // 사용자 ID (회원가입에 사용)
    private String username; // 사용자 이름
    private int student_num; // 학번
    private String email; // 이메일
    private String password; // 비밀번호

    // 1. 회원가입용 생성자 (id를 제외)
    public User(String user_id, String username, int student_num, String email, String password) {
        this.user_id = user_id;
        this.username = username;
        this.student_num = student_num;
        this.email = email;
        this.password = password;
    }

    // 2. 조회 및 채팅용 생성자 (id 포함)
    public User(int id, String user_id, String username, int student_num, String email, String password) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.student_num = student_num;
        this.email = email;
        this.password = password;
    }

    // Getter 및 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStudent_num() {
        return student_num;
    }

    public void setStudent_num(int student_num) {
        this.student_num = student_num;
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
