package com.example.garacar.models;

public class CoverModel {
    private String imageUrl;
    private String note;

    public CoverModel(String imageUrl, String note) {
        this.imageUrl = imageUrl;
        this.note = note;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNote() {
        return note;
    }
}
