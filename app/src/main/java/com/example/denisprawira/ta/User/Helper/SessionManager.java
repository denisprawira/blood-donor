package com.example.denisprawira.ta.User.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.denisprawira.ta.User.Model.User;


public class SessionManager {

    public static SharedPreferences sharedPreferences ;
    public static SharedPreferences.Editor editor;

    Context context;
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("currentUser",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public  void setSession(User user ) {

        editor = sharedPreferences.edit();
        editor.putString(GlobalHelper.USER_ID,user.getId());
        editor.putString(GlobalHelper.USER_NAME,user.getNama());
        editor.putString(GlobalHelper.USER_PHONE,user.getTelp());
        editor.putString(GlobalHelper.USER_BLOOD,user.getGoldarah());
        editor.putString(GlobalHelper.USER_GENDER,user.getGender());
        editor.putString(GlobalHelper.USER_EMAIL,user.getEmail());
        editor.putString(GlobalHelper.USER_PHOTO,user.getPhoto());
        editor.putString(GlobalHelper.USER_TOKEN,user.getToken());
        editor.apply();
    }

    //get
    public String getId(){
        return sharedPreferences.getString(GlobalHelper.USER_ID,"");
    }
    public String getName(){
        return sharedPreferences.getString(GlobalHelper.USER_NAME,"");
    }
    public String getPhone(){
        return sharedPreferences.getString(GlobalHelper.USER_PHONE,"");
    }
    public String getBlood(){
        return sharedPreferences.getString(GlobalHelper.USER_BLOOD,"");
    }
    public String getGender(){
        return sharedPreferences.getString(GlobalHelper.USER_GENDER,"");
    }
    public String getEmail(){
        return sharedPreferences.getString(GlobalHelper.USER_EMAIL,"");
    }
    public String getPhoto(){
        return sharedPreferences.getString(GlobalHelper.USER_PHOTO,"");
    }
    public String getToken(){
        return sharedPreferences.getString(GlobalHelper.USER_TOKEN,"");
    }
    public  String getChatId(){
        return sharedPreferences.getString(GlobalHelper.USER_ID_CHAT,"");
    }
    public  boolean getLogStatus(){ return sharedPreferences.getBoolean(GlobalHelper.LOG_STATUS,false);}

    //set
    public void setName(String name){ editor.putString(GlobalHelper.USER_NAME,name).apply();}
    public void setPhone(String phone){editor.putString(GlobalHelper.USER_PHONE,phone).apply();}
    public void setBlood(String golDarah){editor.putString(GlobalHelper.USER_BLOOD,golDarah).apply();}
    public void setGender(String gender){editor.putString(GlobalHelper.USER_GENDER,gender).apply();}
    public void setEmail(String email){editor.putString(GlobalHelper.USER_EMAIL,email).apply();}
    public void setPhoto(String photo){editor.putString(GlobalHelper.USER_PHOTO,photo).apply();}
    public void setToken(String token){editor.putString(GlobalHelper.USER_TOKEN,token).apply();};
    public void setChatId(String chatId){editor.putString(GlobalHelper.USER_ID_CHAT,chatId).apply(); }
    public void setLogStatus(){ editor.putBoolean(GlobalHelper.LOG_STATUS,true).apply();}

    //Clear Session
    public void clearSession(){ sharedPreferences.edit().clear().apply();}





































}
