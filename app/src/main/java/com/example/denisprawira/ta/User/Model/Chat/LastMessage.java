package com.example.denisprawira.ta.User.Model.Chat;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class LastMessage {
    String receiver, sender, message, timeMilis,status,token;
    String userName, userPhoto;
    @ServerTimestamp
    public Date date;

    public LastMessage() {
    }

    public LastMessage(String receiver, String sender, String message, String timeMilis, String status, Date date) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.timeMilis = timeMilis;
        this.status = status;
        this.date = date;
        this.userName = "";
        this.userPhoto="";
    }

    public LastMessage(String receiver, String sender, String message, String timeMilis, String userName, String userPhoto, Date date) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.timeMilis = timeMilis;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.date = date;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTimeMilis() {
        return timeMilis;
    }

    public void setTimeMilis(String timeMilis) {
        this.timeMilis = timeMilis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
