package com.example.denisprawira.ta.Api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Blood implements Parcelable {
    String id;

    @SerializedName("blood_type")
    String bloodType;

    public Blood(String id, String bloodType) {
        this.id = id;
        this.bloodType = bloodType;
    }

    protected Blood(Parcel in) {
        id = in.readString();
        bloodType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(bloodType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Blood> CREATOR = new Creator<Blood>() {
        @Override
        public Blood createFromParcel(Parcel in) {
            return new Blood(in);
        }

        @Override
        public Blood[] newArray(int size) {
            return new Blood[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
