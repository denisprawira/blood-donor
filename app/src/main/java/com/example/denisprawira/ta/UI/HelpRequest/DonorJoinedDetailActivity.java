package com.example.denisprawira.ta.UI.HelpRequest;

import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.R;

public class DonorJoinedDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView donorJoinedDetailProfileImage;
    ImageButton backDonorJoinedDetail,donorJoinedDetailCall,donorJoinedDetailMessage,donorJoinedDetailChat;
    TextView donorJoinedDetailUserName,donorJoinedDetailTime,donorJoinedDetailBlood,donorJoinedDetailUtdName,donorJoinedDetailAddress;
    Button donorJoinedDetailDirection;

    String idDonor,idUser,idUtd,tanggal,userName, userPhoto,userTelp,userToken,utdName,utdTelp,utdPhoto,utdAddress,golDarah,lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_joined_detail);

        backDonorJoinedDetail         = findViewById(R.id.backDonorJoinedDetail);
        donorJoinedDetailCall         = findViewById(R.id.donorJoinedDetailCall);
        donorJoinedDetailMessage      = findViewById(R.id.donorJoinedDetailMessage);
        donorJoinedDetailChat         = findViewById(R.id.donorJoinedDetailChat);
        donorJoinedDetailUserName     = findViewById(R.id.donorJoinedDetailUserName);
        donorJoinedDetailProfileImage = findViewById(R.id.donorJoinedDetailProfileImage);
        donorJoinedDetailTime         = findViewById(R.id.donorJoinedDetailTime);
        donorJoinedDetailBlood        = findViewById(R.id.donorJoinedDetailBlood);
        donorJoinedDetailUtdName      = findViewById(R.id.donorJoinedDetailUtdName);
        donorJoinedDetailAddress      = findViewById(R.id.donorJoinedDetailAddress);
        donorJoinedDetailDirection    = findViewById(R.id.donorJoinedDetailDirection);

        backDonorJoinedDetail.setOnClickListener(this);
        donorJoinedDetailCall.setOnClickListener(this);
        donorJoinedDetailMessage.setOnClickListener(this);
        donorJoinedDetailChat.setOnClickListener(this);
        donorJoinedDetailDirection.setOnClickListener(this);

        idDonor     = getIntent().getExtras().get("idDonor").toString();
        idUser      = getIntent().getExtras().get("idUser").toString();
        idUtd       = getIntent().getExtras().get("idUtd").toString();
        tanggal     = getIntent().getExtras().get("tanggal").toString();
        userName    = getIntent().getExtras().get("userName").toString();
        userPhoto   = getIntent().getExtras().get("userPhoto").toString();
        userTelp    = getIntent().getExtras().get("userTelp").toString();
        userToken   = getIntent().getExtras().get("userToken").toString();
        utdName     = getIntent().getExtras().get("utdName").toString();
        utdTelp     = getIntent().getExtras().get("utdTelp").toString();
        utdPhoto    = getIntent().getExtras().get("utdPhoto").toString();
        utdAddress  = getIntent().getExtras().get("utdAddress").toString();
        golDarah    = getIntent().getExtras().get("golDarah").toString();
        lat         = getIntent().getExtras().get("lat").toString();
        lng         = getIntent().getExtras().get("lng").toString();

        Glide.with(DonorJoinedDetailActivity.this).load(userPhoto).into(donorJoinedDetailProfileImage);
        donorJoinedDetailUserName.setText(userName);
        donorJoinedDetailTime.setText(GlobalHelper.convertDateTimeToTime(tanggal));
        donorJoinedDetailUtdName.setText(utdName);
        donorJoinedDetailBlood.setText(golDarah);
        donorJoinedDetailAddress.setText(utdAddress);

    }

    @Override
    public void onClick(View v) {
        if(v==backDonorJoinedDetail){
            onBackPressed();
        }else if(v==donorJoinedDetailMessage){
            sendSms();
        }else if(v==donorJoinedDetailCall){
            callPhone();
        }else if(v==donorJoinedDetailChat){
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
        }else if(v==donorJoinedDetailDirection){
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }
    }

    public void sendSms(){
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address",userTelp);
        startActivity(smsIntent);
    }

    public void callPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", userTelp, null));
        startActivity(intent);
    }
}
