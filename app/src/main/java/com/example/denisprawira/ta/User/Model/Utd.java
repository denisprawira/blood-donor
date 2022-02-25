package com.example.denisprawira.ta.User.Model;

public class Utd {

    String id, nama,kabupaten, email, alamat, telp, lat,lng, token;

    public Utd() {

    }

    public Utd(String id, String nama, String token) {
        this.id = id;
        this.nama = nama;
        this.token = token;
    }

    public Utd(String id, String nama, String lat, String lng, String token) {
        this.id = id;
        this.nama = nama;
        this.lat = lat;
        this.lng = lng;
        this.token = token;
    }

    public Utd(String id, String nama, String kabupaten, String email, String alamat, String telp, String lat, String lng) {
        this.id = id;
        this.nama = nama;
        this.kabupaten = kabupaten;
        this.email = email;
        this.alamat = alamat;
        this.telp = telp;
        this.lat = lat;
        this.lng = lng;
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

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
