package com.example.denisprawira.ta.User.Model.Donor;

public class RequestDonor {

    String idDonor, idUtd,golDarah, utdName, utdEmail, utdAddress, utdTelp, lat, lng, utdPhoto,utdToken, status, tanggal;


    public RequestDonor() {
    }

    public RequestDonor(String idDonor, String idUtd, String golDarah, String utdName, String utdEmail, String utdAddress, String utdTelp, String lat, String lng, String utdPhoto, String utdToken, String status, String tanggal) {
        this.idDonor = idDonor;
        this.idUtd = idUtd;
        this.golDarah = golDarah;
        this.utdName = utdName;
        this.utdEmail = utdEmail;
        this.utdAddress = utdAddress;
        this.utdTelp = utdTelp;
        this.lat = lat;
        this.lng = lng;
        this.utdPhoto = utdPhoto;
        this.utdToken = utdToken;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getIdDonor() {
        return idDonor;
    }

    public void setIdDonor(String idDonor) {
        this.idDonor = idDonor;
    }

    public String getIdUtd() {
        return idUtd;
    }

    public void setIdUtd(String idUtd) {
        this.idUtd = idUtd;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getUtdName() {
        return utdName;
    }

    public void setUtdName(String utdName) {
        this.utdName = utdName;
    }

    public String getUtdEmail() {
        return utdEmail;
    }

    public void setUtdEmail(String utdEmail) {
        this.utdEmail = utdEmail;
    }

    public String getUtdAddress() {
        return utdAddress;
    }

    public void setUtdAddress(String utdAddress) {
        this.utdAddress = utdAddress;
    }

    public String getUtdTelp() {
        return utdTelp;
    }

    public void setUtdTelp(String utdTelp) {
        this.utdTelp = utdTelp;
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

    public String getUtdPhoto() {
        return utdPhoto;
    }

    public void setUtdPhoto(String utdPhoto) {
        this.utdPhoto = utdPhoto;
    }

    public String getUtdToken() {
        return utdToken;
    }

    public void setUtdToken(String utdToken) {
        this.utdToken = utdToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
