package com.example.denisprawira.ta.Api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HelpRequest implements Parcelable {
    String id;
    @SerializedName("id_user")
    String idUser;
    @SerializedName("id_pmi")
    String idPmi;
    @SerializedName("blood_type")
    String blood;
    @SerializedName("patient_name")
    String patientName;
    String description;
    String target;
    String img;
    @SerializedName("post_at")
    String postAt;
    String status;

    User user;

    public HelpRequest() {
    }

    public HelpRequest(String id, String idUser, String idPmi, String blood, String patientName, String description, String target, String img, String postAt, String status) {
        this.id = id;
        this.idUser = idUser;
        this.idPmi = idPmi;
        this.blood = blood;
        this.patientName = patientName;
        this.description = description;
        this.target = target;
        this.img = img;
        this.postAt = postAt;
        this.status = status;
    }

    protected HelpRequest(Parcel in) {
        id = in.readString();
        idUser = in.readString();
        idPmi = in.readString();
        blood = in.readString();
        patientName = in.readString();
        description = in.readString();
        target = in.readString();
        img = in.readString();
        postAt = in.readString();
        status = in.readString();
    }

    public HelpRequest(String id, String idUser, String idPmi, String blood, String patientName, String description, String target, String img, String postAt, String status, User user) {
        this.id = id;
        this.idUser = idUser;
        this.idPmi = idPmi;
        this.blood = blood;
        this.patientName = patientName;
        this.description = description;
        this.target = target;
        this.img = img;
        this.postAt = postAt;
        this.status = status;
        this.user = user;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idUser);
        dest.writeString(idPmi);
        dest.writeString(blood);
        dest.writeString(patientName);
        dest.writeString(description);
        dest.writeString(target);
        dest.writeString(img);
        dest.writeString(postAt);
        dest.writeString(status);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HelpRequest> CREATOR = new Creator<HelpRequest>() {
        @Override
        public HelpRequest createFromParcel(Parcel in) {
            return new HelpRequest(in);
        }

        @Override
        public HelpRequest[] newArray(int size) {
            return new HelpRequest[size];
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPostAt() {
        return postAt;
    }

    public void setPostAt(String postAt) {
        this.postAt = postAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
