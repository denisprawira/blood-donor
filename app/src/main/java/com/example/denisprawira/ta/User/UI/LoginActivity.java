package com.example.denisprawira.ta.User.UI;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.User.Api.ApiUtils;
import com.example.denisprawira.ta.User.Api.Model.Response;
import com.example.denisprawira.ta.User.Api.Status;
import com.example.denisprawira.ta.User.Api.UserService;
import com.example.denisprawira.ta.R;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity{

    EditText loginEmail, loginPassword;
    Button loginButton;
    String email,password;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        userService = ApiUtils.getUserService();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(loginEmail.getText().toString(),loginPassword.getText().toString());
            }
        });

    }

    public void login(String email, String password){
        userService.login(email,password).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                assert response.body() != null;
                Toast.makeText(LoginActivity.this, "token: "+response.body().token, Toast.LENGTH_SHORT).show();
                if(response.body().status.equals(Status.success)){
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }


}
