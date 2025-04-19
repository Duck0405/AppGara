package com.example.garacar.models;

public class Product {
    private String serviceName;
    private String serviceImage;
    private double servicePrice;
    private float rating;
    private boolean isDiscounted;
    private int discountPercentage;

    public Product() {
        // Firebase cần constructor rỗng
    }

    public Product(String serviceName, String serviceImage, double servicePrice, float rating, boolean isDiscounted, int discountPercentage) {
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.servicePrice = servicePrice;
        this.rating = rating;
        this.isDiscounted = isDiscounted;
        this.discountPercentage = discountPercentage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public double getServicePrice() {
        return servicePrice;
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

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setDiscounted(boolean discounted) {
        isDiscounted = discounted;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
