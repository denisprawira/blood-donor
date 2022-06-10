package com.example.denisprawira.ta.Api.Model;

public class Response {
    public String status, message, token,userStatus;

    public Response() {
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public Response(String status, String message, String token, String userStatus) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.userStatus = userStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
