package com.example.denisprawira.ta.User.Api.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    String id, name, email, phone, address;

    @SerializedName("blood_type")
    String bloodType;
    String img, status;

    @SerializedName("blood")
    Blood blood;

    String token;

    public User(String id, String name, String email, String phone, String address, String bloodType, String img, String status, Blood blood) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bloodType = bloodType;
        this.img = img;
        this.status = status;
        this.blood = blood;
    }

    public User(String id, String name, String email, String phone, String address, String bloodType, String img, String status, Blood blood, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bloodType = bloodType;
        this.img = img;
        this.status = status;
        this.blood = blood;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}