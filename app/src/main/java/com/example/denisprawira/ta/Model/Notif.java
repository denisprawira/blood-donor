package com.example.denisprawira.ta.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Notif {
    String id,title,message,typeId,type,senderId,senderStatus,senderName,receiverId,receiverStatus,status,img,timeMilis;
    @ServerTimestamp
    public Date date;

    public Notif() {
    }

    public Notif(String id, String title, String message, String typeId, String type, String senderId, String senderStatus, String senderName, String receiverId, String receiverStatus, String status, String img, String timeMilis, Date date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.typeId = typeId;
        this.type = type;
        this.senderId = senderId;
        this.senderStatus = senderStatus;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverStatus = receiverStatus;
        this.status = status;
        this.img = img;
        this.timeMilis = timeMilis;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(String senderStatus) {
        this.senderStatus = senderStatus;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(String receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTimeMilis() {
        return timeMilis;
    }

    public void setTimeMilis(String timeMilis) {
        this.timeMilis = timeMilis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
