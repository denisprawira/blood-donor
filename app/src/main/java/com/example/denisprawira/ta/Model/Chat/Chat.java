package com.example.denisprawira.ta.Model.Chat;


import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Chat {

    public String sender;
    public String receiver;
    public String message;
    public String id;
    public String nama;
    public String photo;
    public String numberOfMessage;
    public String lastMessage;
    public int status;
    @ServerTimestamp
    public Date date;

    public Chat(String id, String nama, String photo, String numberOfMessage, String lastMessage) {
        this.id = id;
        this.nama = nama;
        this.photo = photo;
        this.numberOfMessage = numberOfMessage;
        this.lastMessage = lastMessage;
    }

    public Chat(String sender, String receiver, String message, int status) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.status = status;
    }

    public Chat(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Chat(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Chat(String sender, String receiver, String message, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNumberOfMessage() {
        return numberOfMessage;
    }

    public void setNumberOfMessage(String numberOfMessage) {
        this.numberOfMessage = numberOfMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
