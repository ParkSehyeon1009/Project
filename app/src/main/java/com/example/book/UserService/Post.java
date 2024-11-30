package com.example.book.UserService;

public class Post {
    private String title;
    private String bookTitle;
    private String author;
    private String publisher;
    private String price;

    // 기본 생성자
    public Post() {
    }

    // 매개변수 있는 생성자 추가
    public Post(String title, String bookTitle, String author, String publisher, String price) {
        this.title = title;
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}



