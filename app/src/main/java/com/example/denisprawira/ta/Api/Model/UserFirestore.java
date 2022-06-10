package com.example.denisprawira.ta.Api.Model;

public class UserFirestore {
    String userUID,userId,userName,userPhoto,userStatus,userToken;

    public UserFirestore() {
    }

    public UserFirestore(String userUID, String userId, String userName, String userPhoto, String userStatus, String userToken) {
        this.userUID = userUID;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.userStatus = userStatus;
        this.userToken = userToken;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
