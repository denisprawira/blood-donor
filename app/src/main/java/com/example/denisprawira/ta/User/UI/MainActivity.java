package com.example.denisprawira.ta.User.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Fragment.Signup.SignupWelcome;


public class MainActivity extends AppCompatActivity {
    Button login,signup;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.welcome_login);
        signup = findViewById(R.id.welcome_signup);
        SignupWelcome signupWelcome= new SignupWelcome();
        sessionManager = new SessionManager(MainActivity.this);
        Toast.makeText(this, String.valueOf(sessionManager.getLogStatus()), Toast.LENGTH_SHORT).show();
        if(sessionManager.getLogStatus()){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(MainActivity.this,String.valueOf(sessionManager.getLogStatus()),Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().add(R.id.signup_fragment,signupWelcome).commit();
        }


    }

}
