package com.example.denisprawira.ta.Api.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pmi implements Parcelable {
    String id, name, email,phone,address,lat,lng,img,status,token;

    public Pmi() {
    }

    public Pmi(String id, String name, String email, String phone, String address, String lat, String lng, String img, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.img = img;
        this.status = status;
    }

    public Pmi(String id, String name, String email, String phone, String address, String lat, String lng, String img, String status, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.img = img;
        this.status = status;
        this.token = token;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    protected Pmi(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        address = in.readString();
        lat = in.readString();
        lng = in.readString();
        img = in.readString();
        status = in.readString();
        token = in.readString();
    }

    public static final Creator<Pmi> CREATOR = new Creator<Pmi>() {
        @Override
        public Pmi createFromParcel(Parcel in) {
            return new Pmi(in);
        }

        @Override
        public Pmi[] newArray(int size) {
            return new Pmi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(lat);
        parcel.writeString(lng);
        parcel.writeString(img);
        parcel.writeString(status);
        parcel.writeString(token);
    }
}
