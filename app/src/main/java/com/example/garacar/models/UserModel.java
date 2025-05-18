package com.example.garacar.models;


public class UserModel {
    public String uid, name, email, role;

    public UserModel() {
    }

    public UserModel(String uid, String name, String email, String role) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
