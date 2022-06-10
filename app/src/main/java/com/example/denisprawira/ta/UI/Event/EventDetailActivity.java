package com.example.denisprawira.ta.UI.Event;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Helper.SessionManager;
import com.example.denisprawira.ta.Helper.TimeConverter;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.SessionManager.UserSessionManager;
import com.example.denisprawira.ta.UI.Chat.ChatRoomActivity;
import com.example.denisprawira.ta.Values.GlobalValues;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.google.android.gms.common.util.CollectionUtils.listOf;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.models.Channel;
import io.getstream.chat.android.client.models.ConnectionData;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.client.utils.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

    String idEvent,idUser, idUtd,userName,userPhoto,userTelp,userToken, utdName, utdPhoto, utdTelp, utdToken,title,description,instansi,target,lat,lng,address,document,date,startTime,endTime,distance;
    TextView eventDetailUserName,eventDetailTitle, eventDetailDeskripsi, eventDetailInstansi, eventDetailTarget,eventDetailDate, eventDetailStartTime, eventDetailEndTime,eventDetailAddress;
    ImageView eventDetailProfileImage,eventDetailImageMap;
    Button eventJoin,eventDetailOpenMap;
    ImageButton eventDetailChat,eventDetailCall;
    SessionManager sessionManager;

    Event event;
    LinearLayout eventButtonContainer;
    CardView cardViewActionEvent;
    ImageButton eventDetailBack,eventDetailEdit;

    CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");

    UserService userService = ApiUtils.getUserService();
    Boolean isUserJoined = false;
    UserSessionManager userSessionManager ;

    User user = new User();
    ChatClient client = ChatClient.instance();

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        userSessionManager = new UserSessionManager(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapEventDetail);
        mapFragment.getMapAsync(this);

        eventDetailBack         = findViewById(R.id.eventDetailBack);
        eventDetailEdit         = findViewById(R.id.eventDetailEdit);
        eventDetailUserName     = findViewById(R.id.eventDetailUserName);
        eventDetailTitle        = findViewById(R.id.eventDetailTitle);
        eventDetailDeskripsi    = findViewById(R.id.eventDetailDescription);
        eventDetailInstansi     = findViewById(R.id.eventDetailInstitution);
        eventDetailTarget       = findViewById(R.id.eventDetailAmount);
        eventDetailDate         = findViewById(R.id.eventDetailDate);
        eventDetailStartTime    = findViewById(R.id.eventDetailStartTime);
        eventDetailEndTime      = findViewById(R.id.eventDetailEndTime);
        eventDetailAddress      = findViewById(R.id.eventDetailAddress);
        eventDetailProfileImage = findViewById(R.id.eventDetailProfileImage);
        eventDetailCall         = findViewById(R.id.eventdetailCall);
        eventDetailChat         = findViewById(R.id.eventDetailChat);
        eventJoin               = findViewById(R.id.joinEventButton);
        eventDetailOpenMap      = findViewById(R.id.eventDetailOpenMap);
        eventButtonContainer    = findViewById(R.id.eventButtonContainer);
        cardViewActionEvent     = findViewById(R.id.cardViewActionEvent);


        eventDetailBack.setOnClickListener(this);
        eventDetailEdit.setOnClickListener(this);
        eventDetailCall.setOnClickListener(this);
        eventDetailChat.setOnClickListener(this);
        eventJoin.setOnClickListener(this);
        eventDetailOpenMap.setOnClickListener(this);

        Intent intent  = getIntent();
        event = (Event) intent.getParcelableExtra(GlobalValues.EVENT_INTENT);
        if(intent.getStringExtra(GlobalValues.EVENT_INTENT_TYPE).equals("request")){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
            layoutParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.input_margin),0,this.getResources().getDimensionPixelSize(R.dimen.input_margin),this.getResources().getDimensionPixelSize(R.dimen.input_margin));
            eventButtonContainer.setVisibility(View.GONE);
            cardViewActionEvent.setLayoutParams(layoutParams);
        }else if(intent.getStringExtra(GlobalValues.EVENT_INTENT_TYPE).equals("joined")){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
            layoutParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.input_margin),0,this.getResources().getDimensionPixelSize(R.dimen.input_margin),this.getResources().getDimensionPixelSize(R.dimen.input_margin));
            eventButtonContainer.setVisibility(View.VISIBLE);
            cardViewActionEvent.setLayoutParams(layoutParams);
        }
        isUserJoined(userSessionManager.getId(),event.getId());
        loadUserFirebase(event.getIdPmi());
        eventDetailTitle.setText(event.getTitle());
        eventDetailDeskripsi.setText(event.getDescription());
        eventDetailInstansi.setText(event.getInstitution());
        eventDetailTarget.setText(event.getTarget());
        eventDetailDate.setText(TimeConverter.convertDateTimeToDate(event.getDateStart()));
        eventDetailStartTime.setText(TimeConverter.convertDateTimeToTime(event.getDateStart()));
        getPmi(event.getId());
        if(event.getDateEnd()!=null){
            eventDetailEndTime.setText(TimeConverter.convertDateTimeToTime(event.getDateEnd()));
        }else{
            eventDetailEndTime.setText("-- : --");
        }
        eventDetailAddress.setText(event.getAddress());
        connectUser();

    }

    public int dpToPx(float dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    public void getPmi(String id){
        userService.loadPmi(id).enqueue(new Callback<Pmi>() {
            @Override
            public void onResponse(Call<Pmi> call, Response<Pmi> response) {
                if(response.body()!=null){
                    eventDetailUserName.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<Pmi> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    public void onClick(View v) {
        if(v==eventJoin){
            if(isUserJoined){
                joinEvent(userSessionManager.getId(),event.getId(),0);
            }else{
                joinEvent(userSessionManager.getId(),event.getId(),1);
            }



        }else if(v==eventDetailOpenMap){
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+event.getLat()+","+event.getLng());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
//            if (mapIntent.resolveActivity(getPackageManager()) != null) {
//
//            }
//
        }else if (v== eventDetailChat){
            Toast.makeText(this, client.getCurrentUser().getId(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, client.getCurrentUser().getId(), Toast.LENGTH_SHORT).show();
            client.createChannel("messaging", listOf(client.getCurrentUser().getId(), userId)
                ).enqueue(new io.getstream.chat.android.client.call.Call.Callback<Channel>() {
                @Override
                public void onResult(@NotNull Result<Channel> result) {
                    if(result.isSuccess()){
                        Intent intent = new Intent(EventDetailActivity.this, ChatRoomActivity.class);
                        intent.putExtra("cid",result.data().getCid());
                        startActivity(intent);
                    }else{
                        Log.e("UsersAdapter", result.error().getMessage().toString());
                    }
                }

            });
        }else if(v==eventDetailCall){
            callPhone();
        }else if(v==eventDetailBack){
            onBackPressed();
        }else if(v==eventDetailEdit){
            Intent intent = new Intent(EventDetailActivity.this,EventEditActivity.class);
            intent.putExtra("event",event);
            startActivity(intent);
        }
    }

    public void joinEvent(String idUser, String idEvent, int status){
        userService.joinEvent(idUser,idEvent,status).enqueue(new Callback<com.example.denisprawira.ta.Api.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.denisprawira.ta.Api.Model.Response> call, Response<com.example.denisprawira.ta.Api.Model.Response> response) {
                Toast.makeText(EventDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(status==1){
                    eventJoin.setText("Ikuti Acara");
                    eventJoin.setBackgroundResource(R.drawable.theme_button_solid_radius);
                }else{
                    eventJoin.setText("Telah Diikuti");
                    eventJoin.setBackgroundResource(R.drawable.theme_button_solid_radius_disapprove);
                }
                eventJoin.invalidate();
            }

            @Override
            public void onFailure(Call<com.example.denisprawira.ta.Api.Model.Response> call, Throwable t) {
                Log.e("result",t.getMessage());
            }
        });
    }
    public void isUserJoined(String idUser,String idEvent){
        userService.checkJoinedEvent(idUser,idEvent).enqueue(new Callback<com.example.denisprawira.ta.Api.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.denisprawira.ta.Api.Model.Response> call, Response<com.example.denisprawira.ta.Api.Model.Response> response) {
                if(response!=null){
                    if(response.body().getStatus().equals("success")){
                        isUserJoined = true;
                        eventJoin.setText("Telah Diikuti");
                        eventJoin.setBackgroundResource(R.drawable.theme_button_solid_radius_disapprove);
                    }
                }
            }

            @Override
            public void onFailure(Call<com.example.denisprawira.ta.Api.Model.Response> call, Throwable t) {
                Log.e("result",t.getMessage());
            }
        });
    }

//    private void checkJoinedEvent(String idUser,String idEvent){
//        retrofitServices.checkJoinedEvent(idUser, idEvent).enqueue(new Callback<List<RetrofitResult>>() {
//            @Override
//            public void onResponse(Call<List<RetrofitResult>> call, Response<List<RetrofitResult>> response) {
//                List<RetrofitResult> res = response.body();
//                for(int i=0; i<res.size();i++){
//                    if(res.get(i).getCode().equals("error")){
//                        eventJoin.setEnabled(false);
//                        eventJoin.setText("Telah Diikuti");
//                        eventJoin.setBackgroundResource(R.drawable.theme_button_solid_radius_disable);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RetrofitResult>> call, Throwable t) {
//
//            }
//        });
//    }

    public void loadUserFirebase(String pmiId){
        usersCollection.whereEqualTo("userId",pmiId).whereEqualTo("userStatus","pmi").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    userId = queryDocumentSnapshots.getDocuments().get(0).getId();
                }else{
                    Toast.makeText(EventDetailActivity.this, "user is not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void connectUser(){
        user.setId(userSessionManager.getIdFirebase());
        Map<String, String> data = new HashMap<String, String>();
        data.put("name",userSessionManager.getName());
        data.put("image",userSessionManager.getImg());
        String token = client.devToken(user.getId());
        client.connectUser(user, token).enqueue(new io.getstream.chat.android.client.call.Call.Callback<ConnectionData>() {
            @Override
            public void onResult(@NotNull Result<ConnectionData> result) {
                if(result.isSuccess()){
                    Log.e("result",result.data().getUser().getId());
                }else{
                    Log.e("result","failed to connect user");
                }
            }
        });
    }



    public void callPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", userTelp, null));
        startActivity(intent);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        map.getUiSettings().setAllGesturesEnabled(false);
        LatLng pos = null;
        if(event.getLat()==null || event.getLat() == null){
            pos = new LatLng(-8.588641,115.297787);
        }else{
            pos = new LatLng(Double.parseDouble(event.getLat()),Double.parseDouble(event.getLng()));
        }
        map.addMarker(new MarkerOptions().position(pos).title("this is marker")
        );
        map.setMinZoomPreference(19f);
        map.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }
}
