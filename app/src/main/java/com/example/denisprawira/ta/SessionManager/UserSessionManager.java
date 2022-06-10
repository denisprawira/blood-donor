package com.example.denisprawira.ta.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.denisprawira.ta.Api.Model.Pmi2;
import com.example.denisprawira.ta.Api.Model.User;
import com.example.denisprawira.ta.Values.GlobalValues;

public class UserSessionManager {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    Context context;

    public UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("currentUser",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //String id,name, email, phone, address,bloodId,bloodTypeName,img,status, tokenApi, tokenFirebase;

    public void setSession(User user){

        editor.putString(GlobalValues.USER_ID,user.getId());
        editor.putString(GlobalValues.USER_NAME,user.getName());
        editor.putString(GlobalValues.USER_EMAIL,user.getEmail());
        editor.putString(GlobalValues.USER_PHONE,user.getPhone());
        editor.putString(GlobalValues.USER_ADDRESS,user.getAddress());
        editor.putString(GlobalValues.USER_BLOOD_ID,user.getBlood().getId());
        editor.putString(GlobalValues.USER_BLOOD_TYPE_NAME,user.getBlood().getBloodType());
        editor.putString(GlobalValues.USER_IMG,user.getImg());
        editor.putString(GlobalValues.USER_ROLE,user.getRole());
        editor.putString(GlobalValues.USER_STATUS,user.getStatus());
        editor.putString(GlobalValues.USER_API_TOKEN,"");
        editor.putString(GlobalValues.USER_FIREBASE_TOKEN,"");
        editor.putString(GlobalValues.USER_FIREBASE_ID,"");
        editor.putBoolean(GlobalValues.USER_LOG_STATUS,true);
        editor.apply();
    }

    public void setSessionPmi(Pmi2 pmi){
        editor.putString(GlobalValues.PMI_ID,pmi.getId());
        editor.putString(GlobalValues.PMI_NAME,pmi.getName());
        editor.putString(GlobalValues.PMI_ADDRESS,pmi.getAddress());
        editor.putString(GlobalValues.PMI_LAT,pmi.getLat());
        editor.putString(GlobalValues.PMI_LNG,pmi.getLng());
        editor.putString(GlobalValues.PMI_IMG,pmi.getImg());
        editor.apply();
    }

    public String getId() {
        return sharedPreferences.getString(GlobalValues.USER_ID,"");
    }

    public void setId(String id) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_ID,id).apply();
    }

    public String getName() {
        return sharedPreferences.getString(GlobalValues.USER_NAME,"");
    }

    public void setName(String name) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_NAME,name).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(GlobalValues.USER_EMAIL,"");
    }

    public void setEmail(String email) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_EMAIL,email);
    }

    public String getPhone() {
        return sharedPreferences.getString(GlobalValues.USER_PHONE,"");
    }

    public void setPhone(String phone) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_PHONE,phone).apply();
    }

    public String getAddress() {
        return sharedPreferences.getString(GlobalValues.USER_ADDRESS,"");
    }

    public void setAddress(String address) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_ADDRESS,address).apply();
    }

    public String getBloodId() {
        return sharedPreferences.getString(GlobalValues.USER_BLOOD_ID,"");
    }

    public void setBloodId(String bloodId) {
        editor.putString(GlobalValues.USER_BLOOD_ID,bloodId);
    }

    public String getBloodTypeName() {
        return sharedPreferences.getString(GlobalValues.USER_BLOOD_TYPE_NAME,"");
    }

    public void setBloodTypeName(String bloodTypeName) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_BLOOD_TYPE_NAME,bloodTypeName);
    }

    public String getImg() {
        return sharedPreferences.getString(GlobalValues.USER_IMG,"");
    }

    public void setImg(String img) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_IMG,img);
    }

    public String getRole() {
        return sharedPreferences.getString(GlobalValues.USER_ROLE,"");
    }

    public void setRole(String role) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_ROLE,role);
    }

    public String getStatus() {
        return sharedPreferences.getString(GlobalValues.USER_STATUS,"");
    }

    public void setStatus(String status) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_STATUS,status);
    }

    public String getTokenApi() {
        return sharedPreferences.getString(GlobalValues.USER_API_TOKEN,"");
    }

    public void setTokenApi(String tokenApi) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_API_TOKEN,tokenApi).apply();
    }

    public String getTokenFirebase() {
        return sharedPreferences.getString(GlobalValues.USER_FIREBASE_TOKEN,"");
    }

    public void setTokenFirebase(String tokenFirebase) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_FIREBASE_TOKEN,tokenFirebase).apply();
    }

    public String getIdFirebase() {
        return sharedPreferences.getString(GlobalValues.USER_FIREBASE_ID,"");
    }

    public void setIdFirebase(String idFirebase) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.USER_FIREBASE_ID,idFirebase).apply();
    }

    public Boolean getLogStatus(){
        return sharedPreferences.getBoolean(GlobalValues.USER_LOG_STATUS,false);
    }

    public void setLogStatus(Boolean status){
        editor = sharedPreferences.edit();
        editor.putBoolean(GlobalValues.USER_LOG_STATUS,status);
    }

    //PMI SESSION DATA
    public String getPmiId() {
        return sharedPreferences.getString(GlobalValues.PMI_ID,"");
    }

    public void setPmiId(String pmiId) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_ID,pmiId).apply();
    }

    public String getPmiName() {
        return sharedPreferences.getString(GlobalValues.PMI_NAME,"");
    }

    public void setPmiName(String pmiName) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_NAME,pmiName).apply();
    }

    public String getPmiAddress() {
        return sharedPreferences.getString(GlobalValues.PMI_ADDRESS,"");
    }

    public void setPmiAddress(String pmiAddress) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_ADDRESS,pmiAddress).apply();
    }

    public String getPmiLat() {
        return sharedPreferences.getString(GlobalValues.PMI_LAT,"");
    }

    public void setPmiLat(String pmiLat) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_LAT,pmiLat).apply();
    }

    public String getPmiLng() {
        return sharedPreferences.getString(GlobalValues.PMI_LNG,"");
    }

    public void setPmiLng(String pmiLng) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_LNG,pmiLng).apply();

    }

    public String getPmiImg() {
        return sharedPreferences.getString(GlobalValues.PMI_IMG,"");
    }

    public void setPmiImg(String pmiImg) {
        editor = sharedPreferences.edit();
        editor.putString(GlobalValues.PMI_IMG,pmiImg).apply();

    }

    public void clearSession(){ sharedPreferences.edit().clear().apply();}
}
