package com.example.garacar.models;

public class NewsItem {
    private String title;
    private String date;
    private String content;

    private String image;


    public NewsItem() {
        // Constructor mặc định cần thiết cho Firebase
    }

    public NewsItem(String title, String date, String content, String image) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
    public String getImage() {
        return image;
    }
}
