package com.example.denisprawira.ta.Api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    String id, name, email, phone, address;

    @SerializedName("blood_type")
    String bloodType;
    String img, role,status;

    @SerializedName("blood")
    Blood blood;
    String token;

    public User() {
    }

    public User(String id, String name, String email, String phone, String address, String bloodType, String img, String role, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bloodType = bloodType;
        this.img = img;
        this.role = role;
        this.status = status;
    }

    public User(String id, String name, String email, String phone, String address, String bloodType, String img, String role, String status, Blood blood) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bloodType = bloodType;
        this.img = img;
        this.role = role;
        this.status = status;
        this.blood = blood;
    }

    public User(String id, String name, String email, String phone, String address, String bloodType, String img, String role, String status, Blood blood, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bloodType = bloodType;
        this.img = img;
        this.role = role;
        this.status = status;
        this.blood = blood;
        this.token = token;
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        address = in.readString();
        bloodType = in.readString();
        img = in.readString();
        role = in.readString();
        status = in.readString();
        blood = in.readParcelable(Blood.class.getClassLoader());
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(bloodType);
        dest.writeString(img);
        dest.writeString(role);
        dest.writeString(status);
        dest.writeParcelable(blood, flags);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
