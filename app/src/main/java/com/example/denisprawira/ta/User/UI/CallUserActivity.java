package com.example.denisprawira.ta.User.UI;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CallUserActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView callUserPhoto;
    TextView callDonorName, callDonorDistance,callDonorBlood,callDonorLocation,callDonorAddress;
    Button callDonorAccept,callDonorDecline;
    MediaPlayer mediaPlayer;

    SessionManager sessionManager;

    String idSender,sender,type,title,idType,message,golDarah,utd,senderImage,lat,lng,address,distance;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_user);
        sessionManager      = new SessionManager(CallUserActivity.this);
        callUserPhoto       = findViewById(R.id.callUserPhoto);
        callDonorName       = findViewById(R.id.callDonorName);
        callDonorDistance   = findViewById(R.id.callDonorDistance);
        callDonorBlood      = findViewById(R.id.callDonorBlood);
        callDonorLocation   = findViewById(R.id.callDonorLocation);
        callDonorAddress    = findViewById(R.id.callDonorAddress);
        callDonorAccept     = findViewById(R.id.callDonorAccept);
        callDonorDecline    = findViewById(R.id.callDonorDecline);

        callDonorAccept.setOnClickListener(this);
        callDonorDecline.setOnClickListener(this);

        idSender    = getIntent().getExtras().get("idSender").toString();
        sender      = getIntent().getExtras().get("sender").toString();
        type        = getIntent().getExtras().get("type").toString();
        title       = getIntent().getExtras().get("title").toString();
        idType      = getIntent().getExtras().get("idType").toString();
        message     = getIntent().getExtras().get("message").toString();
        golDarah    = getIntent().getExtras().get("golDarah").toString();
        utd         = getIntent().getExtras().get("utd").toString();
        senderImage = getIntent().getExtras().get("senderImage").toString();
        lat         = getIntent().getExtras().get("lat").toString();
        lng         = getIntent().getExtras().get("lng").toString();
        address     = getIntent().getExtras().get("address").toString();
        distance    = getIntent().getExtras().get("distance").toString();

        Toast.makeText(this, idType, Toast.LENGTH_SHORT).show();

        Glide.with(CallUserActivity.this).load(senderImage).into(callUserPhoto);
        callDonorName.setText(sender);
        long dis =  Math.round(Double.parseDouble(distance)*1000);
        if(dis>=1000){
            Double result = dis /1000.00;
            callDonorDistance.setText(String.format("%.2f",result )+" Km");
        }else{
            callDonorDistance.setText(String.valueOf(dis)+" Meter");
        }
        callDonorBlood.setText(golDarah);
        callDonorLocation.setText(utd);
        callDonorAddress.setText(address);




        mediaPlayer = MediaPlayer.create(this,R.raw.helpringtones);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


    }



    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    public void onClick(View v) {
        if(v==callDonorAccept){
            acceptDonor(sessionManager.getId(),idType);
        }else if(v==callDonorDecline){
            this.finish();
        }
    }

    public void acceptDonor(String idUser,String idType){
        retrofitServices.acceptDonor(idUser,idType).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(CallUserActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(CallUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
