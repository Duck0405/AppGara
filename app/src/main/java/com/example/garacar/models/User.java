package com.example.garacar.models;

public class User {
    private String fullName, email, phone, role, avatarUrl;

    public User() { } // Bắt buộc Firebase dùng

    public User(String fullName, String email, String phone, String role, String avatarUrl) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    // Getter + Setter
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
