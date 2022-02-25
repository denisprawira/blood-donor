package com.example.denisprawira.ta.User.UI.Event;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.PhotoViewerActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventRequestActivity extends AppCompatActivity implements View.OnClickListener {


    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    String idEvent,idUser, idUtd,userName,userPhoto,userTelp,userToken, utdName, utdPhoto, utdTelp, utdToken,title,description,instansi,target,lat,lng,address,document,date,startTime,endTime,status;

    TextView eventRequestDetailUserName,eventRequestDetailTitle, eventRequestDetailDeskripsi, eventRequestDetailInstansi, eventRequestDetailTarget,eventRequestDetailDate, eventRequestDetailStartTime, eventRequestDetailEndTime,eventRequestDetailAddress;
    ImageView eventRequestDetailUserPhoto;
    ImageButton eventRequestBackDetail,eventRequestDetailMessage, eventRequestDetailCall, eventRequestDetailChat;
    Button eventRequestDetailAction,eventRequestDetailDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_request);

        eventRequestBackDetail      = findViewById(R.id.eventRequestBackDetail);
        eventRequestDetailUserPhoto = findViewById(R.id.eventRequestDetailProfileImage);
        eventRequestDetailUserName  = findViewById(R.id.eventRequestDetailUserName);
        eventRequestDetailTitle     = findViewById(R.id.eventRequestDetailTitle);
        eventRequestDetailDeskripsi = findViewById(R.id.eventRequestDetailDeskripsi);
        eventRequestDetailInstansi  = findViewById(R.id.eventRequestDetailInstansi);
        eventRequestDetailTarget    = findViewById(R.id.eventRequestDetailTarget);
        eventRequestDetailDate      = findViewById(R.id.eventRequestDetailDate);
        eventRequestDetailStartTime = findViewById(R.id.eventRequestDetailStartTime);
        eventRequestDetailEndTime   = findViewById(R.id.eventRequestDetailEndTime);
        eventRequestDetailAddress   = findViewById(R.id.eventRequestDetailAddress);
        eventRequestDetailMessage   = findViewById(R.id.eventRequestDetailMessage);
        eventRequestDetailCall      = findViewById(R.id.eventRequestDetailCall);
        eventRequestDetailChat      = findViewById(R.id.eventRequestDetailChat);
        eventRequestDetailAction    = findViewById(R.id.eventRequestDetailAction);
        eventRequestDetailDocument  = findViewById(R.id.eventRequestDetailDocument);

        eventRequestBackDetail.setOnClickListener(this);
        eventRequestDetailMessage.setOnClickListener(this);
        eventRequestDetailCall.setOnClickListener(this);
        eventRequestDetailChat.setOnClickListener(this);
        eventRequestDetailAction.setOnClickListener(this);
        eventRequestDetailDocument.setOnClickListener(this);



        Intent intent  = getIntent();
        if(intent.hasExtra("idEvent")) {
            idEvent     = getIntent().getExtras().get("idEvent").toString();
            idUser      = getIntent().getExtras().get("idUser").toString();
            idUtd       = getIntent().getExtras().get("idUtd").toString();
            userName    = getIntent().getExtras().get("userName").toString();
            userPhoto   = getIntent().getExtras().get("userPhoto").toString();
            userTelp    = getIntent().getExtras().get("userTelp").toString();
            userToken   = getIntent().getExtras().get("userToken").toString();
            utdName     = getIntent().getExtras().get("utdName").toString();
            utdPhoto    = getIntent().getExtras().get("utdPhoto").toString();
            utdTelp     = getIntent().getExtras().get("utdTelp").toString();
            utdToken    = getIntent().getExtras().get("utdToken").toString();
            title       = getIntent().getExtras().get("title").toString();
            description = getIntent().getExtras().get("description").toString();
            instansi    = getIntent().getExtras().get("instansi").toString();
            target      = getIntent().getExtras().get("target").toString();
            lat         = getIntent().getExtras().get("lat").toString();
            lng         = getIntent().getExtras().get("lng").toString();
            address     = getIntent().getExtras().get("address").toString();
            document    = getIntent().getExtras().get("document").toString();
            date        = getIntent().getExtras().get("date").toString();
            startTime   = getIntent().getExtras().get("startTime").toString();
            endTime     = getIntent().getExtras().get("endTime").toString();
            status      = getIntent().getExtras().get("status").toString();


            Glide.with(DetailEventRequestActivity.this).load(utdPhoto).into(eventRequestDetailUserPhoto);
            eventRequestDetailUserName.setText(utdName);
            eventRequestDetailTitle.setText(title);
            eventRequestDetailDeskripsi.setText(description);
            eventRequestDetailInstansi.setText(instansi);
            eventRequestDetailTarget.setText(target);
            eventRequestDetailDate.setText(GlobalHelper.convertDate(date));
            eventRequestDetailStartTime.setText(GlobalHelper.convertTime(startTime));
            eventRequestDetailEndTime.setText(GlobalHelper.convertTime(endTime));
            eventRequestDetailAddress.setText(address);
        }else{
            String id = getIntent().getExtras().get("id").toString();
            loadRequestEventById(id);
        }

        if(status.equals(GlobalHelper.TYPE_EVENT_PENDING)){
            eventRequestDetailAction.setText("Edit");
        }else if(status.equals(GlobalHelper.TYPE_EVENT_APPROVE)){
            eventRequestDetailAction.setBackgroundResource(R.color.white);
            eventRequestDetailAction.setTextColor(Color.parseColor("#40ad00"));
            eventRequestDetailAction.setText("Approve");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRequestEventById(idEvent);
    }

    public void sendSms(){
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address",utdTelp);
        startActivity(smsIntent);
    }

    public void callPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", utdTelp, null));
        startActivity(intent);
    }

    public void loadRequestEventById(String id){
        retrofitServices.loadRequestEventById(id).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> eventList =  response.body();
                for(int i=0;i<eventList.size();i++){
                    idEvent     = eventList.get(i).getIdEvent();
                    idUser      = eventList.get(i).getIdUser();
                    idUtd       = eventList.get(i).getIdUtd();
                    userName    = eventList.get(i).getUserName();
                    userPhoto   = eventList.get(i).getUserPhoto();
                    userTelp    = eventList.get(i).getUserTelp();
                    userToken   = eventList.get(i).getUserToken();
                    utdName     = eventList.get(i).getUtdName();
                    utdPhoto    = eventList.get(i).getUtdPhoto();
                    utdTelp     = eventList.get(i).getUtdTelp();
                    utdToken    = eventList.get(i).getUtdToken();
                    title       = eventList.get(i).getTitle();
                    description = eventList.get(i).getDescription();
                    instansi    = eventList.get(i).getInstansi();
                    target      = eventList.get(i).getTarget();
                    lat         = eventList.get(i).getLat();
                    lng         = eventList.get(i).getLng();
                    address     = eventList.get(i).getAddress();
                    document    = eventList.get(i).getDocument();
                    date        = eventList.get(i).getDate();
                    startTime   = eventList.get(i).getStartTime();
                    endTime     = eventList.get(i).getEndTime();
                    status      = eventList.get(i).getStatus();


                    Glide.with(DetailEventRequestActivity.this).load(utdPhoto).into(eventRequestDetailUserPhoto);
                    eventRequestDetailUserName.setText(utdName);
                    eventRequestDetailTitle.setText(title);
                    eventRequestDetailDeskripsi.setText(description);
                    eventRequestDetailInstansi.setText(instansi);
                    eventRequestDetailTarget.setText(target);
                    eventRequestDetailDate.setText(GlobalHelper.convertDate(date));
                    eventRequestDetailStartTime.setText(GlobalHelper.convertTime(startTime));
                    eventRequestDetailEndTime.setText(GlobalHelper.convertTime(endTime));
                    eventRequestDetailAddress.setText(address);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==eventRequestDetailCall){
            callPhone();
        }else if(v==eventRequestDetailMessage){
            sendSms();
        }else if(v==eventRequestDetailChat){
            Toast.makeText(this, "event RequestBlood Detail Chat", Toast.LENGTH_SHORT).show();
        }else if(v==eventRequestDetailAction){
            if(status.equals(GlobalHelper.TYPE_EVENT_PENDING)){
                Intent intent = new Intent(DetailEventRequestActivity.this,EventRequestEditActivity.class);
                intent.putExtra("idEvent",idEvent);
                intent.putExtra("idUser",idUser);
                intent.putExtra("idUtd",idUtd);
                intent.putExtra("userName",userName);
                intent.putExtra("userPhoto",userPhoto);
                intent.putExtra("userTelp",userTelp);
                intent.putExtra("userToken",userToken);
                intent.putExtra("utdName",utdName);
                intent.putExtra("utdPhoto",utdPhoto);
                intent.putExtra("utdTelp",utdTelp);
                intent.putExtra("utdToken",utdToken);
                intent.putExtra("title",title);
                intent.putExtra("description",description);
                intent.putExtra("instansi",instansi);
                intent.putExtra("target",target);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("address",address);
                intent.putExtra("document",document);
                intent.putExtra("date",date);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);
                startActivity(intent);
            }else if(status.equals(GlobalHelper.TYPE_EVENT_APPROVE)){
                Toast.makeText(this, "Event ini masih Approve", Toast.LENGTH_SHORT).show();
            }
        }else if(v==eventRequestBackDetail){
            onBackPressed();
        }else if(v==eventRequestDetailDocument){
            Intent intent = new Intent(DetailEventRequestActivity.this, PhotoViewerActivity.class);
            intent.putExtra("document",document);
            startActivity(intent);
        }
    }
}
