package com.example.garacar.models;

public class CoverModel {
    private String imageUrl;
    private String note;

    public CoverModel() {
        // Firebase cần constructor rỗng
    }

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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
