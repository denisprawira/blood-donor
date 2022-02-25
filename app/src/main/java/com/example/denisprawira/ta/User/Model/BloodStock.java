package com.example.denisprawira.ta.User.Model;

public class BloodStock {
    String id, idGolDarah, golDarah, component, description,idPmi, nama, photo, telp, token, alamat, lat, lng, jumlah;

    public BloodStock(String id, String idGolDarah, String golDarah, String component, String description, String idPmi, String nama, String photo, String telp, String token, String alamat, String lat, String lng, String jumlah) {
        this.id = id;
        this.idGolDarah = idGolDarah;
        this.golDarah = golDarah;
        this.component = component;
        this.description = description;
        this.idPmi = idPmi;
        this.nama = nama;
        this.photo = photo;
        this.telp = telp;
        this.token = token;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.jumlah = jumlah;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGolDarah() {
        return idGolDarah;
    }

    public void setIdGolDarah(String idGolDarah) {
        this.idGolDarah = idGolDarah;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdPmi() {
        return idPmi;
    }

    public void setIdPmi(String idPmi) {
        this.idPmi = idPmi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
