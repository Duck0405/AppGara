package com.example.garacar.models;

public class Article {
    private String id;
    private String title;
    private String content;
    private String image;
    private String date;

    // 🔑 Firebase key (để xoá)
    private String key;

    // Constructor mặc định (Firebase cần để mapping dữ liệu)
    public Article() {}

    // Constructor đầy đủ
    public Article(String id, String title, String content, String image, String date, String key) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.key = key;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
