package com.example.denisprawira.ta.User.UI.Event;

import android.content.Intent;
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
import com.example.denisprawira.ta.User.Helper.AlarmReceiver;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.ReminderDatabase;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.User.Model.Reminder;
import com.example.denisprawira.ta.User.Model.RetrofitResult;
import com.example.denisprawira.ta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener {

    String idEvent,idUser, idUtd,userName,userPhoto,userTelp,userToken, utdName, utdPhoto, utdTelp, utdToken,title,description,instansi,target,lat,lng,address,document,date,startTime,endTime,distance;
    TextView eventDetailUserName,eventDetailTitle, eventDetailDeskripsi, eventDetailInstansi, eventDetailTarget,eventDetailDate, eventDetailStartTime, eventDetailEndTime,eventDetailAddress;
    ImageView eventDetailProfileImage;
    Button eventJoin,eventDetailOpenMap;
    ImageButton eventDetailMessage,eventDetailCall;
    SessionManager sessionManager;

    //save for database
    //all variabel needed

    private String reminderIdEvent;
    private String reminderTitle;
    private String reminderMessage;
    private String reminderTime;
    private String reminderRepeatStatus;
    private String reminderRepeatType;
    private String reminderRepeatInterval;
    private String reminderActive;
    private String reminderEventDate;
    private String reminderEventTime;

    FloatingActionButton eventDetailBack;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        sessionManager = new SessionManager(EventDetailActivity.this);

        eventDetailUserName     = findViewById(R.id.eventDetailUserName);
        eventDetailTitle        = findViewById(R.id.eventDetailTitle);
        eventDetailDeskripsi    = findViewById(R.id.eventDetailDeskripsi);
        eventDetailInstansi     = findViewById(R.id.eventDetailInstansi);
        eventDetailTarget       = findViewById(R.id.eventDetailTarget);
        eventDetailDate         = findViewById(R.id.eventDetailDate);
        eventDetailStartTime    = findViewById(R.id.eventDetailStartTime);
        eventDetailEndTime      = findViewById(R.id.eventDetailEndTime);
        eventDetailAddress      = findViewById(R.id.eventDetailAddress);
        eventDetailProfileImage = findViewById(R.id.eventDetailProfileImage);
        eventDetailCall         = findViewById(R.id.eventdetailCall);
        eventDetailMessage      = findViewById(R.id.eventDetailMessage);
        eventJoin               = findViewById(R.id.donorJoinedDetailDirection);
        eventDetailBack         = findViewById(R.id.eventRequestBackDetail);
        eventDetailOpenMap      = findViewById(R.id.eventDetailOpenMap);

        eventDetailCall.setOnClickListener(this);
        eventDetailMessage.setOnClickListener(this);
        eventJoin.setOnClickListener(this);
        eventDetailBack.setOnClickListener(this);
        eventDetailOpenMap.setOnClickListener(this);

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


            Glide.with(EventDetailActivity.this).load(userPhoto).into(eventDetailProfileImage);
            eventDetailUserName.setText(userName);
            eventDetailTitle.setText(title);
            eventDetailDeskripsi.setText(description);
            eventDetailInstansi.setText(instansi);
            eventDetailTarget.setText(target);
            eventDetailDate.setText(GlobalHelper.convertDate(date));
            eventDetailStartTime.setText(GlobalHelper.convertTime(startTime));
            eventDetailEndTime.setText(GlobalHelper.convertTime(endTime));
            eventDetailAddress.setText(address);
            checkJoinedEvent(sessionManager.getId(),idEvent);

        }else{
            String id = getIntent().getExtras().get("id").toString();
            loadEventById(GlobalHelper.lat,GlobalHelper.lng,"approved",id);
            checkJoinedEvent(sessionManager.getId(),id);
        }




    }

    @Override
    public void onClick(View v) {
        if(v==eventJoin){
            joinEvent(sessionManager.getId(),idEvent);
            saveReminder();
            onBackPressed();
        }else if(v==eventDetailBack){
            onBackPressed();
        }else if(v==eventDetailOpenMap){
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }else if (v==eventDetailMessage){
            sendSms();
        }else if(v==eventDetailCall){
            callPhone();
        }
    }

    private void joinEvent(String id, String idEvent) {
        retrofitServices.jointEvent(id,idEvent).enqueue(new Callback<List<RetrofitResult>>() {
            @Override
            public void onResponse(Call<List<RetrofitResult>> call, Response<List<RetrofitResult>> response) {
                List<RetrofitResult> result = response.body();

                for(int i=0; i<result.size();i++){
                    Toast.makeText(EventDetailActivity.this, result.get(i).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RetrofitResult>> call, Throwable t) {

            }
        });

    }


    private void checkJoinedEvent(String idUser,String idEvent){
        retrofitServices.checkJoinedEvent(idUser, idEvent).enqueue(new Callback<List<RetrofitResult>>() {
            @Override
            public void onResponse(Call<List<RetrofitResult>> call, Response<List<RetrofitResult>> response) {
                List<RetrofitResult> res = response.body();
                for(int i=0; i<res.size();i++){
                    if(res.get(i).getCode().equals("error")){
                        eventJoin.setEnabled(false);
                        eventJoin.setText("Telah Diikuti");
                        eventJoin.setBackgroundResource(R.drawable.theme_button_solid_radius_disable);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<RetrofitResult>> call, Throwable t) {

            }
        });
    }

    private void loadEventById(String lt, String ln, String status, String id){
        retrofitServices.loadEventById(lt,ln,status,id).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> eventList = response.body();
                for(int i=0; i<eventList.size();i++){
                    idEvent         = eventList.get(i).getIdEvent();
                    idUser          = eventList.get(i).getIdUser();
                    idUtd           = eventList.get(i).getIdUtd();
                    userName        = eventList.get(i).getUserName();
                    userPhoto       = eventList.get(i).getUserPhoto();
                    userTelp        = eventList.get(i).getUserTelp();
                    userToken       = eventList.get(i).getUserToken();
                    utdName         = eventList.get(i).getUtdName();
                    utdPhoto        = eventList.get(i).getUtdPhoto();
                    utdTelp         = eventList.get(i).getUtdTelp();
                    utdToken        = eventList.get(i).getUtdToken();
                    title           = eventList.get(i).getTitle();
                    description     = eventList.get(i).getDescription();
                    instansi        = eventList.get(i).getInstansi();
                    target          = eventList.get(i).getTarget();
                    lat             = eventList.get(i).getLat();
                    lng             = eventList.get(i).getLng();
                    address         = eventList.get(i).getAddress();
                    document        = eventList.get(i).getDocument();
                    date            = eventList.get(i).getDate();
                    startTime       = eventList.get(i).getStartTime();
                    endTime         = eventList.get(i).getEndTime();
                    distance        = eventList.get(i).getDistance();

                    Glide.with(EventDetailActivity.this).load(userPhoto).into(eventDetailProfileImage);
                    eventDetailUserName.setText(userName);
                    eventDetailTitle.setText(title);
                    eventDetailDeskripsi.setText(description);
                    eventDetailInstansi.setText(instansi);
                    eventDetailTarget.setText(target);
                    eventDetailDate.setText(GlobalHelper.convertDate(date));
                    eventDetailStartTime.setText(GlobalHelper.convertTime(startTime));
                    eventDetailEndTime.setText(GlobalHelper.convertTime(endTime));
                    eventDetailAddress.setText(address);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    public void saveReminder(){

        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.MONTH, GlobalHelper.convertToIntegerMonth(date));
        calendar.set(Calendar.YEAR, GlobalHelper.convertToIntegerYear(date));
        calendar.set(Calendar.DAY_OF_MONTH, GlobalHelper.convertToIntegerDay(date));
        calendar.set(Calendar.HOUR_OF_DAY, GlobalHelper.convertToIntegerHour(startTime));
        calendar.set(Calendar.MINUTE, GlobalHelper.convertToIntegerMinute(startTime));
        calendar.set(Calendar.SECOND, 0);


        reminderIdEvent        = idEvent;
        reminderTitle          = title ;
        reminderMessage        = "Event akan dimulai";
        reminderTime           = "Saat Event Dimulai";
        reminderRepeatStatus   = "false";
        reminderRepeatType     = "Jam";
        reminderRepeatInterval = "Setiap 1 Jam";
        reminderActive         = "true";
        reminderEventDate      = date ;
        reminderEventTime      = startTime ;


        ReminderDatabase rb = new ReminderDatabase(this);
        int ID = rb.addReminder(new Reminder(reminderIdEvent,reminderTitle,reminderTime,reminderMessage,reminderRepeatStatus,reminderRepeatType,reminderRepeatInterval,reminderActive,reminderEventDate,reminderEventTime));
//
        Calendar calendarNow  = Calendar.getInstance();
        long currentTime  = calendarNow.getTimeInMillis();
        long diff  = calendar.getTimeInMillis() - currentTime;
        new AlarmReceiver().setAlarm(getApplicationContext(), calendar, ID);


    }
}
