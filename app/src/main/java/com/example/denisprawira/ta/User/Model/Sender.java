package com.example.denisprawira.ta.User.Model;

public class Sender {

    public String to;
    public Data2 data;
    public Notification notification;


    public Sender() {
    }

    public Sender(String to, Data2 data) {
        this.to = to;
        this.data = data;
    }

    public Sender(String to, Data2 data, Notification notification) {
        this.to = to;
        this.data = data;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data2 getData() {
        return data;
    }

    public void setData(Data2 data) {
        this.data = data;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}


