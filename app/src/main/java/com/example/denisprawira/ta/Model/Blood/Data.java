package com.example.denisprawira.ta.Model.Blood;

public class Data {
    String id, unit, provinsi, jumlah;

    public Data() {
    }

    public Data(String id, String unit, String provinsi, String jumlah) {
        this.id = id;
        this.unit = unit;
        this.provinsi = provinsi;
        this.jumlah = jumlah;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
