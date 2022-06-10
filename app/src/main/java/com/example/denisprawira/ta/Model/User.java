package com.example.denisprawira.ta.Model;

public class User {

    String id, nama, telp, goldarah,gender, email,photo,password,token,distance,chatId;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String nama, String photo) {
        this.id = id;
        this.nama = nama;
        this.photo = photo;
    }

    public User(String id, String nama, String photo, String token) {
        this.id = id;
        this.nama = nama;
        this.photo = photo;
        this.token = token;
    }

    public User(String id, String nama, String email, String photo, String token) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.photo = photo;
        this.token = token;
    }

    public User(String id, String nama, String telp, String goldarah, String gender, String email, String photo, String token) {
        this.id = id;
        this.nama = nama;
        this.telp = telp;
        this.goldarah = goldarah;
        this.gender = gender;
        this.email = email;
        this.photo = photo;
        this.token = token;
    }

    public User(String id, String nama, String telp, String goldarah, String gender, String email, String photo, String token, String distance) {
        this.id = id;
        this.nama = nama;
        this.telp = telp;
        this.goldarah = goldarah;
        this.gender = gender;
        this.email = email;
        this.photo = photo;
        this.token = token;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getGoldarah() {
        return goldarah;
    }

    public void setGoldarah(String goldarah) {
        this.goldarah = goldarah;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
