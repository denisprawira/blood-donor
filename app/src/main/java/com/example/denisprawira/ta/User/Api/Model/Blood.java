package com.example.denisprawira.ta.User.Api.Model;

import com.google.gson.annotations.SerializedName;

public class Blood {
    String id;
    @SerializedName("'blood_type'")
    String bloodType;

    public Blood(String id, String bloodType) {
        this.id = id;
        this.bloodType = bloodType;
    }

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
