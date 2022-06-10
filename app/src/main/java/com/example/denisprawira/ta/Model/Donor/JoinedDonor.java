package com.example.denisprawira.ta.Model.Donor;

public class JoinedDonor {

    String idDonor, idUser, idUtd, tanggal, userName, userPhoto,userTelp, userToken, utdName, utdTelp, utdPhoto, utdAddress,golDarah, lat, lng;

    public JoinedDonor() {
    }

    public JoinedDonor(String idDonor, String idUser, String idUtd, String tanggal, String userName, String userPhoto, String userTelp, String userToken, String utdName, String utdTelp, String utdPhoto, String utdAddress,String golDarah, String lat, String lng) {
        this.idDonor    = idDonor;
        this.idUser     = idUser;
        this.idUtd      = idUtd;
        this.tanggal    = tanggal;
        this.userName   = userName;
        this.userPhoto  = userPhoto;
        this.userTelp   = userTelp;
        this.userToken  = userToken;
        this.utdName    = utdName;
        this.utdTelp    = utdTelp;
        this.utdPhoto   = utdPhoto;
        this.utdAddress = utdAddress;
        this.golDarah   = golDarah;
        this.lat        = lat;
        this.lng        = lng;
    }

    public String getIdDonor() {
        return idDonor;
    }

    public void setIdDonor(String idDonor) {
        this.idDonor = idDonor;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUtd() {
        return idUtd;
    }

    public void setIdUtd(String idUtd) {
        this.idUtd = idUtd;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserTelp() {
        return userTelp;
    }

    public void setUserTelp(String userTelp) {
        this.userTelp = userTelp;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUtdName() {
        return utdName;
    }

    public void setUtdName(String utdName) {
        this.utdName = utdName;
    }

    public String getUtdTelp() {
        return utdTelp;
    }

    public void setUtdTelp(String utdTelp) {
        this.utdTelp = utdTelp;
    }

    public String getUtdPhoto() {
        return utdPhoto;
    }

    public void setUtdPhoto(String utdPhoto) {
        this.utdPhoto = utdPhoto;
    }

    public String getUtdAddress() {
        return utdAddress;
    }

    public void setUtdAddress(String utdAddress) {
        this.utdAddress = utdAddress;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
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
}
