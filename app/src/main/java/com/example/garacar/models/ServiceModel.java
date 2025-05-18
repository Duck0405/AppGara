package com.example.garacar.models;
public class ServiceModel {
    private String id;
    private String serviceName;
    private String serviceDesc;
    private String serviceImage;
    private int servicePrice;
    private boolean visible; // Nếu bạn có trường này trong Firebase

    public ServiceModel() {
        // Constructor rỗng cần thiết cho Firebase
    }

    public ServiceModel(String id, String serviceName, String serviceDesc, String serviceImage, int servicePrice, boolean visible) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.serviceImage = serviceImage;
        this.servicePrice = servicePrice;
        this.visible = visible;
    }

    // Getter và setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getServiceDesc() { return serviceDesc; }
    public void setServiceDesc(String serviceDesc) { this.serviceDesc = serviceDesc; }
    public String getServiceImage() { return serviceImage; }
    public void setServiceImage(String serviceImage) { this.serviceImage = serviceImage; }
    public int getServicePrice() { return servicePrice; }
    public void setServicePrice(int servicePrice) { this.servicePrice = servicePrice; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
}
