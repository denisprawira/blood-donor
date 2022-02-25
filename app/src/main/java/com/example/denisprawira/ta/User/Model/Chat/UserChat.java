package com.example.denisprawira.ta.User.Model.Chat;

public class UserChat {
    String userId,userChatId, userName,userPhoto,userStatus,userToken;

    public UserChat() {
    }

    public UserChat(String userId, String userName, String userPhoto, String userStatus, String userToken) {
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.userStatus = userStatus;
        this.userToken = userToken;
    }

    public UserChat(String userId, String userChatId, String userName, String userPhoto, String userStatus, String userToken) {
        this.userId = userId;
        this.userChatId = userChatId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.userStatus = userStatus;
        this.userToken = userToken;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(String userChatId) {
        this.userChatId = userChatId;
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
