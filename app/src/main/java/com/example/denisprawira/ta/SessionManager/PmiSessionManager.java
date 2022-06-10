package com.example.denisprawira.ta.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.Values.GlobalValues;

public class PmiSessionManager {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    Context context;

    //String id,name, email, phone, address,lat,lng,img,status, tokenApi, tokenFirebase;

    public PmiSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("pmiCurrentUser",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSession(Pmi pmi){
        editor.putString(GlobalValues.PMI_ID,pmi.getId());
        editor.putString(GlobalValues.PMI_NAME,pmi.getName());
        editor.putString(GlobalValues.PMI_EMAIL,pmi.getEmail());
        editor.putString(GlobalValues.PMI_PHONE,pmi.getPhone());
        editor.putString(GlobalValues.PMI_ADDRESS,pmi.getAddress());
        editor.putString(GlobalValues.PMI_LAT,pmi.getLat());
        editor.putString(GlobalValues.PMI_LNG,pmi.getLng());
        editor.putString(GlobalValues.PMI_IMG,pmi.getImg());
        editor.putString(GlobalValues.PMI_STATUS,pmi.getStatus());
        editor.putString(GlobalValues.PMI_API_TOKEN,"");
        editor.putString(GlobalValues.PMI_FIREBASE_TOKEN,"");
        editor.putString(GlobalValues.USER_FIREBASE_ID,"");
        editor.putBoolean(GlobalValues.PMI_LOG_STATUS,true);
        editor.apply();
    }

    public String getId() {
        return sharedPreferences.getString(GlobalValues.PMI_ID,"");
    }

    public void setId(String id) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_ID,id).apply();
    }

    public String getName() {
        return sharedPreferences.getString(GlobalValues.PMI_NAME,"");
    }

    public void setName(String name) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_NAME,name).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(GlobalValues.PMI_EMAIL,"");
    }

    public void setEmail(String email) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_EMAIL,email).apply();
    }

    public String getPhone() {
        return sharedPreferences.getString(GlobalValues.PMI_PHONE,"");
    }

    public void setPhone(String phone) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_PHONE,phone).apply();
    }

    public String getAddress() {
        return sharedPreferences.getString(GlobalValues.PMI_ADDRESS,"");
    }

    public void setAddress(String address) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_ADDRESS,address).apply();
    }

    public String getLat() {
        return sharedPreferences.getString(GlobalValues.PMI_LAT,"");
    }

    public void setLat(String lat) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_LAT,lat).apply();
    }

    public String getLng() {
        return sharedPreferences.getString(GlobalValues.PMI_LNG,"");
    }

    public void setLng(String lng) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_LNG,lng).apply();
    }

    public String getImg() {
        return sharedPreferences.getString(GlobalValues.PMI_IMG,"");
    }

    public void setImg(String img) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_IMG,img).apply();
    }

    public String getStatus() {
        return sharedPreferences.getString(GlobalValues.PMI_STATUS,"");
    }

    public void setStatus(String status) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_STATUS,status).apply();
    }

    public String getTokenApi() {
        return sharedPreferences.getString(GlobalValues.PMI_API_TOKEN,"");
    }

    public void setTokenApi(String tokenApi) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_API_TOKEN,tokenApi).apply();
    }

    public String getTokenFirebase() {
        return sharedPreferences.getString(GlobalValues.PMI_FIREBASE_TOKEN,"");
    }

    public void setTokenFirebase(String tokenFirebase) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_FIREBASE_TOKEN,tokenFirebase).apply();
    }

    public String getIdFirebase() {
        return sharedPreferences.getString(GlobalValues.PMI_FIREBASE_ID,"");
    }

    public void setIdFirebase(String idFirebase) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_FIREBASE_ID,idFirebase).apply();
    }


    public Boolean getLogStatus(){
        return sharedPreferences.getBoolean(GlobalValues.PMI_LOG_STATUS,false);
    }

    public void setLogStatus(Boolean status){
        editor = sharedPreferences.edit();
        editor.putBoolean(GlobalValues.PMI_LOG_STATUS,status);
    }

    public void clearSession(){ sharedPreferences.edit().clear().apply();}
}
