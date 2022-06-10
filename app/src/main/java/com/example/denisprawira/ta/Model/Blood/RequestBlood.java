package com.example.denisprawira.ta.Model.Blood;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestBlood {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<Data> data;

    public RequestBlood() {
    }

    public RequestBlood(String status, List<Data> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
