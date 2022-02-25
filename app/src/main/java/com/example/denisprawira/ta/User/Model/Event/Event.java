package com.example.denisprawira.ta.User.Model.Event;

public class Event {
    String idEvent,idUser, idUtd,userName,userPhoto,userToken,userTelp, utdName, utdPhoto, utdToken, utdTelp,title,description,instansi,lat,lng,address,document,date,startTime,endTime,target,distance, postTime,status;

    public Event(String idEvent, String idUser, String idUtd, String userName, String userPhoto, String userTelp, String userToken, String utdName, String utdPhoto,String utdTelp,String utdToken, String title, String description, String instansi, String lat, String lng, String address, String document, String date, String startTime, String endTime, String target, String distance) {
        this.idEvent     = idEvent;
        this.idUser      = idUser;
        this.idUtd       = idUtd;
        this.userName    = userName;
        this.userPhoto   = userPhoto;
        this.userTelp    = userTelp;
        this.userToken   = userToken;
        this.utdName     = utdName;
        this.utdPhoto    = utdPhoto;
        this.utdTelp     = utdTelp;
        this.utdToken    = utdToken;
        this.title       = title;
        this.description = description;
        this.instansi    = instansi;
        this.lat         = lat;
        this.lng         = lng;
        this.address     = address;
        this.document    = document;
        this.date        = date;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.target      = target;
        this.distance    = distance;
    }

    public Event(String idEvent, String idUser, String idUtd, String userName, String userPhoto, String userTelp,String userToken, String utdName, String utdPhoto,String utdTelp,String utdToken, String title, String description, String instansi, String lat, String lng, String address, String document, String date, String startTime, String endTime, String target, String postTime, String status) {
        this.idEvent     = idEvent;
        this.idUser      = idUser;
        this.idUtd       = idUtd;
        this.userName    = userName;
        this.userPhoto   = userPhoto;
        this.userTelp    = userTelp;
        this.userToken   = userToken;
        this.utdName     = utdName;
        this.utdPhoto    = utdPhoto;
        this.utdTelp     = utdTelp;
        this.utdToken    = utdToken;
        this.title       = title;
        this.description = description;
        this.instansi    = instansi;
        this.lat         = lat;
        this.lng         = lng;
        this.address     = address;
        this.document    = document;
        this.date        = date;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.target      = target;
        this.postTime    = postTime;
        this.status      = status;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
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

    public String getUtdName() {
        return utdName;
    }

    public void setUtdName(String utdName) {
        this.utdName = utdName;
    }

    public String getUtdPhoto() {
        return utdPhoto;
    }

    public void setUtdPhoto(String utdPhoto) {
        this.utdPhoto = utdPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserTelp() {
        return userTelp;
    }

    public void setUserTelp(String userTelp) {
        this.userTelp = userTelp;
    }

    public String getUtdToken() {
        return utdToken;
    }

    public void setUtdToken(String utdToken) {
        this.utdToken = utdToken;
    }

    public String getUtdTelp() {
        return utdTelp;
    }

    public void setUtdTelp(String utdTelp) {
        this.utdTelp = utdTelp;
    }
}
