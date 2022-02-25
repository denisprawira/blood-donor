package com.example.denisprawira.ta.User.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Notif {
    String id,idSender,idReceiver,senderName, senderPhoto,idType,msg,status,timeMilis, type,receiverStatus,senderStatus;
    @ServerTimestamp
    public Date date;

    public Notif() {
    }

    public Notif(String id, String idSender, String idReceiver, String idType, String msg, String status, String timeMilis, String type, Date date,String receiverStatus,String senderStatus) {
        this.id = id;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.idType = idType;
        this.msg = msg;
        this.status = status;
        this.timeMilis = timeMilis;
        this.type = type;
        this.date = date;
        this.receiverStatus = receiverStatus;
        this.senderStatus = senderStatus;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeMilis() {
        return timeMilis;
    }

    public void setTimeMilis(String timeMilis) {
        this.timeMilis = timeMilis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(String receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    public String getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(String senderStatus) {
        this.senderStatus = senderStatus;
    }


}
