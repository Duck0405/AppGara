package com.example.garacar.models;

public class Booking {
    public String name, phone, service, date, time, carType, plateNumber, note, userId;

    public Booking() {} // Firebase cần constructor rỗng

    public Booking(String name, String phone, String service, String date, String time, String carType, String plateNumber, String note, String userId) {
        this.name = name;
        this.phone = phone;
        this.service = service;
        this.date = date;
        this.time = time;
        this.carType = carType;
        this.plateNumber = plateNumber;
        this.note = note;
        this.userId = userId;
    }
}
