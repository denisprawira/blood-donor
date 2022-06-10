package com.example.denisprawira.ta.Api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Event implements  Parcelable {
    String id;
    @SerializedName("id_user")
    String idUser;
    @SerializedName("id_pmi")
    String idPmi;
    String title;
    String institution;
    String description;
    String img;
    String lat;
    String lng;
    String address;
    String target;
    @SerializedName("post_at")
    String postAt;
    @SerializedName("date_start")
    String dateStart;
    @SerializedName("date_end")
    String dateEnd;
    String status;
    String message;
    String distance;

    public Event() {
    }

    public Event(String id, String idUser, String idPmi, String title, String institution, String description, String img, String lat, String lng, String address, String amount, String postAt, String dateStart, String dateEnd, String status) {
        this.id = id;
        this.idUser = idUser;
        this.idPmi = idPmi;
        this.title = title;
        this.institution = institution;
        this.description = description;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.target = amount;
        this.postAt = postAt;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    public Event(String id, String idUser, String idPmi, String title, String institution, String description, String img, String lat, String lng, String address, String amount, String postAt, String dateStart, String dateEnd, String status, String distance) {
        this.id = id;
        this.idUser = idUser;
        this.idPmi = idPmi;
        this.title = title;
        this.institution = institution;
        this.description = description;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.target = amount;
        this.postAt = postAt;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
        this.distance = distance;
    }

    public Event(String id, String idUser, String idPmi, String title, String institution, String description, String img, String lat, String lng, String address, String target, String postAt, String dateStart, String dateEnd, String status, String message, String distance) {
        this.id = id;
        this.idUser = idUser;
        this.idPmi = idPmi;
        this.title = title;
        this.institution = institution;
        this.description = description;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.target = target;
        this.postAt = postAt;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
        this.message = message;
        this.distance = distance;
    }

    protected Event(Parcel in) {
        id = in.readString();
        idUser = in.readString();
        idPmi = in.readString();
        title = in.readString();
        institution = in.readString();
        description = in.readString();
        img = in.readString();
        lat = in.readString();
        lng = in.readString();
        address = in.readString();
        target = in.readString();
        postAt = in.readString();
        dateStart = in.readString();
        dateEnd = in.readString();
        status = in.readString();
        message = in.readString();
        distance = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdPmi() {
        return idPmi;
    }

    public void setIdPmi(String idPmi) {
        this.idPmi = idPmi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPostAt() {
        return postAt;
    }

    public void setPostAt(String postAt) {
        this.postAt = postAt;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(idUser);
        parcel.writeString(idPmi);
        parcel.writeString(title);
        parcel.writeString(institution);
        parcel.writeString(description);
        parcel.writeString(img);
        parcel.writeString(lat);
        parcel.writeString(lng);
        parcel.writeString(address);
        parcel.writeString(target);
        parcel.writeString(postAt);
        parcel.writeString(dateStart);
        parcel.writeString(dateEnd);
        parcel.writeString(status);
        parcel.writeString(target);
        parcel.writeString(distance);
    }
}
