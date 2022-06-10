package com.example.denisprawira.ta.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.SessionManager.PmiSessionManager;
import com.example.denisprawira.ta.SessionManager.UserSessionManager;
import com.example.denisprawira.ta.UI.Fragment.Signup.SignupWelcome;
import com.example.denisprawira.ta.UIPMI.MainPmiActivity;
import com.example.denisprawira.ta.UIPMI.PmiChooserActivity;


public class MainActivity extends AppCompatActivity {
    Button login,signup;
    UserSessionManager userSessionManager;
    PmiSessionManager pmiSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.welcome_login);
        signup = findViewById(R.id.welcome_signup);
        SignupWelcome signupWelcome= new SignupWelcome();
        userSessionManager = new UserSessionManager(MainActivity.this);
        pmiSessionManager = new PmiSessionManager(MainActivity.this);
        if(userSessionManager.getLogStatus()){
            if(userSessionManager.getRole().equals("pmi")){
                Intent intent = new Intent(MainActivity.this, PmiChooserActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.signup_fragment,signupWelcome).commit();
        }

    }


}
