package com.example.denisprawira.ta.UI.Fragment.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Adapter2.EventAdapter;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Interface.HomeActivityListener;
import com.example.denisprawira.ta.R;

import com.example.denisprawira.ta.UI.Event.EventActivity;
import com.example.denisprawira.ta.UI.Event.EventDetailActivity;
import com.example.denisprawira.ta.UI.HomeActivity;
import com.example.denisprawira.ta.Values.GlobalValues;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainEventFragment extends Fragment implements View.OnClickListener{

    //DATA TYPE AND CONST
    ArrayList<Event> eventArrayList = new ArrayList<>();



    //COMPONENT VIEW
    FloatingActionButton addEvent;

    RecyclerView recyclerViewEvent;
    EventAdapter eventAdapter;
    LinearLayoutManager layoutManager;
    TextView textViewMainEventFragment;

    //CLASS INSTANCE
    UserService userService = ApiUtils.getUserService();
    HomeActivityListener homeActivityListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_event, container, false);
        addEvent = view.findViewById(R.id.addEvent);
        addEvent.setOnClickListener(this);
        recyclerViewEvent = view.findViewById(R.id.recyclerViewEvent);
        recyclerViewEvent.setHasFixedSize(true);
        textViewMainEventFragment = view.findViewById(R.id.textViewMainEventFragment);

        if(homeActivityListener.getLocation()!=null){
            Location location = homeActivityListener.getLocation();
            loadAllEvent(location.getLatitude(),location.getLongitude());
        }else{
            Log.e("error msg: ","location is null");
        }

        initRecyclerViewEvent();
        return view;
    }

    private void loadAllEvent(double lat,double lng){
        Log.e("error","load all event");
        userService.showAvailableEvent(lat,lng).enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response!=null){
                    eventArrayList.clear();
                    List<Event> listEvent = response.body();
                    if(listEvent.size()==0){
                        textViewMainEventFragment.setVisibility(View.VISIBLE);
                    }else{
                        textViewMainEventFragment.setVisibility(View.GONE);
                    }
                    eventArrayList.addAll(listEvent);
                    eventAdapter.notifyDataSetChanged();
                }else{
                    Log.e("error","response null");
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e(MainEventFragment.class.getSimpleName(),t.getMessage());
            }
        });
    }

    private void initRecyclerViewEvent(){
        eventAdapter = new EventAdapter(3,eventArrayList, getContext(), new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {
                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                intent.putExtra(GlobalValues.EVENT_INTENT, item);
                intent.putExtra(GlobalValues.EVENT_INTENT_TYPE,"blalba");
                startActivity(intent);
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewEvent.setAdapter(eventAdapter);
        recyclerViewEvent.setLayoutManager(layoutManager);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof HomeActivityListener){
            homeActivityListener = (HomeActivity) context;
        }else{
            throw new RuntimeException(context.toString()+"must implement interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeActivityListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addEvent){
            Intent intent  =  new Intent(getContext(),EventActivity.class);
            startActivity(intent);
        }

    }

//    public void findNearEvent(String lat, String lng,String status){
//        retrofitServices.findNearEvent(lat,lng,status).enqueue(new Callback<List<Event>>() {
//            @Override
//            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                anotherEventList.clear();
//                nearEventList.clear();
//                List<Event> event  =  response.body();
//                for(int i=0; i<event.size(); i++){
//                    long distance =  Math.round(Double.parseDouble(event.get(i).getDistance())*1000);
//                    if(distance>25000){
//                       anotherEventList.add(event.get(i));
//                    }else{
//                       nearEventList.add(event.get(i));
//                    }
//                }
//                mainAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<Event>> call, Throwable t) {
//
//            }
//        });
//
//    }

//    public void setRecyclerViewEvent(){
//            mainAdapter =  new MainAdapter(getContext(),nearEventList,anotherEventList);
//            recyclerViewEvent.setAdapter(mainAdapter);
//            layoutManager = new LinearLayoutManager(getContext());
//            recyclerViewEvent.setLayoutManager(layoutManager);
//
//    }



}