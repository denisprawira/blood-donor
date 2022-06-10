package com.example.denisprawira.ta.UI.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.Helper.RetrofitHelper;
import com.example.denisprawira.ta.Api.RetrofitServices;

import com.example.denisprawira.ta.R;

public class EventRequestUnapprovedActivity extends AppCompatActivity implements View.OnClickListener {

    //basic type data
    String idEvent, idUser, idUtd, userName, userPhoto, userTelp, userToken, utdName, utdPhoto, utdTelp, utdToken, title, description, instansi, target, lat, lng, address, document, date, startTime, endTime, distance;

    //object declaration
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    //view declaration
    TextView requestEventUnapproveTitle, requestEventUnapproveDescription;
    Button  requestEventUnapproveAction;
    ImageButton backEventUnapproved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_unapproved);

        requestEventUnapproveTitle       = findViewById(R.id.requestEventUnapproveTitle);
        requestEventUnapproveDescription = findViewById(R.id.requestEventUnapproveDescription);
        requestEventUnapproveAction = findViewById(R.id.requestEventUnapproveAction);

        backEventUnapproved.setOnClickListener(this);
        requestEventUnapproveAction.setOnClickListener(this);


        Intent intent  = getIntent();
        if(intent.hasExtra("idEvent")){
            idEvent         = getIntent().getExtras().get("idEvent").toString();
            idUser          = getIntent().getExtras().get("idUser").toString();
            idUtd           = getIntent().getExtras().get("idUtd").toString();
            userName        = getIntent().getExtras().get("userName").toString();
            userPhoto       = getIntent().getExtras().get("userPhoto").toString();
            userTelp        = getIntent().getExtras().get("userTelp").toString();
            userToken       = getIntent().getExtras().get("userToken").toString();
            utdName         = getIntent().getExtras().get("utdName").toString();
            utdPhoto        = getIntent().getExtras().get("utdPhoto").toString();
            utdTelp         = getIntent().getExtras().get("utdTelp").toString();
            utdToken        = getIntent().getExtras().get("utdToken").toString();
            title           = getIntent().getExtras().get("title").toString();
            description     = getIntent().getExtras().get("description").toString();
            instansi        = getIntent().getExtras().get("instansi").toString();
            target          = getIntent().getExtras().get("target").toString();
            lat             = getIntent().getExtras().get("lat").toString();
            lng             = getIntent().getExtras().get("lng").toString();
            address         = getIntent().getExtras().get("address").toString();
            document        = getIntent().getExtras().get("document").toString();
            date            = getIntent().getExtras().get("date").toString();
            startTime       = getIntent().getExtras().get("startTime").toString();
            endTime         = getIntent().getExtras().get("endTime").toString();
            distance        = getIntent().getExtras().get("distance").toString();

        }else{
//            String id = getIntent().getExtras().get("id").toString();
//            loadEventById(GlobalHelper.lat,GlobalHelper.lng,"Unapproved",id);
//            loadUnapprovedEvent(id);
        }
    }
//
//    private void loadEventById(String lt, String ln, String status, String id){
//        retrofitServices.loadEventById(lt,ln,status,id).enqueue(new Callback<List<Event>>() {
//            @Override
//            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                List<Event> eventList = response.body();
//                for(int i=0; i<eventList.size();i++){
//                    idEvent         = eventList.get(i).getIdEvent();
//                    idUser          = eventList.get(i).getIdUser();
//                    idUtd           = eventList.get(i).getIdUtd();
//                    userName        = eventList.get(i).getUserName();
//                    userPhoto       = eventList.get(i).getUserPhoto();
//                    userTelp        = eventList.get(i).getUserTelp();
//                    userToken       = eventList.get(i).getUserToken();
//                    utdName         = eventList.get(i).getUtdName();
//                    utdPhoto        = eventList.get(i).getUtdPhoto();
//                    utdTelp         = eventList.get(i).getUtdTelp();
//                    utdToken        = eventList.get(i).getUtdToken();
//                    title           = eventList.get(i).getTitle();
//                    description     = eventList.get(i).getDescription();
//                    instansi        = eventList.get(i).getInstansi();
//                    target          = eventList.get(i).getTarget();
//                    lat             = eventList.get(i).getLat();
//                    lng             = eventList.get(i).getLng();
//                    address         = eventList.get(i).getAddress();
//                    document        = eventList.get(i).getDocument();
//                    date            = eventList.get(i).getDate();
//                    startTime       = eventList.get(i).getStartTime();
//                    endTime         = eventList.get(i).getEndTime();
//                    distance        = eventList.get(i).getDistance();
//                    requestEventUnapproveTitle.setText(title);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<Event>> call, Throwable t) {
//                Toast.makeText(EventRequestUnapprovedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//
//    public void loadUnapprovedEvent(String idEvent){
//        retrofitServices.loadUnApprovedEvent(idEvent).enqueue(new Callback<List<UnapprovedEvent>>() {
//            @Override
//            public void onResponse(Call<List<UnapprovedEvent>> call, Response<List<UnapprovedEvent>> response) {
//                List<UnapprovedEvent> unapprovedEventList = response.body();
//                for(int i=0; i<unapprovedEventList.size();i++){
//                    requestEventUnapproveDescription.setText(unapprovedEventList.get(i).getDescription());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<UnapprovedEvent>> call, Throwable t) {
//                Toast.makeText(EventRequestUnapprovedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        if(v==requestEventUnapproveAction){
            Intent intent = new Intent(EventRequestUnapprovedActivity.this,EventRequestEditActivity.class);
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
        }else if(v==backEventUnapproved){
            onBackPressed();
        }
    }
}
