package com.example.book.UserService;

public class Post {
    private int id;
    private String title;
    private String content;
    private String author;
    private String created_at;

    // 생성자
    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // id Getter와 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // title Getter와 Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // content Getter와 Setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // author Getter와 Setter
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // created_at Getter와 Setter
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
