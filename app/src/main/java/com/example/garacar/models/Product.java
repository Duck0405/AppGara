package com.example.garacar.models;

public class Product {
    private String name;
    private String imageUrl;
    private double price;
    private float rating;
    private boolean isDiscounted;
    private int discountPercentage;

    // Constructor
    public Product(String name, String imageUrl, double price, float rating, boolean isDiscounted, int discountPercentage) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.rating = rating;
        this.isDiscounted = isDiscounted;
        this.discountPercentage = discountPercentage;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }
}
