package com.example.garacar.models;

public class Booking {
    public String name, phone, service, date, time, carType, plateNumber, note, userId, status;

    public Booking() {}

    public Booking(String name, String phone, String service, String date, String time,
                   String carType, String plateNumber, String note, String userId, String status) {
        this.name = name;
        this.phone = phone;
        this.service = service;
        this.date = date;
        this.time = time;
        this.carType = carType;
        this.plateNumber = plateNumber;
        this.note = note;
        this.userId = userId;
        this.status = status;
    }
}

