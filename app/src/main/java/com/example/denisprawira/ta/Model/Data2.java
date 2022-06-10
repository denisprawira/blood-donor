package com.example.denisprawira.ta.Model;

public class Data2 {

    private String idSender, sender, type, title, idType,message,senderName,senderPhoto, golDarah,pmiName,pmiAddress, lat, lng,distance,chatId,token;

    public Data2() {
    }

    public Data2(String type, String message,String senderName, String senderPhoto,String chatId,String token) {
        this.type           = type;
        this.message            = message;
        this.senderName     = senderName;
        this.senderPhoto    = senderPhoto;
        this.chatId         = chatId;
        this.token          = token;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getPmiName() {
        return pmiName;
    }

    public void setPmiName(String pmiName) {
        this.pmiName = pmiName;
    }

    public String getPmiAddress() {
        return pmiAddress;
    }

    public void setPmiAddress(String pmiAddress) {
        this.pmiAddress = pmiAddress;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
