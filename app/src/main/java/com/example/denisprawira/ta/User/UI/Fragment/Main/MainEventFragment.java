package com.example.denisprawira.ta.User.UI.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.MainAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Event.EventActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainEventFragment extends Fragment implements View.OnClickListener{

    FloatingActionButton addEvent;



    RecyclerView recyclerViewEvent;
    RecyclerView.LayoutManager layoutManager;



    ArrayList<Event> nearEventList=  new ArrayList<>();
    ArrayList<Event> anotherEventList = new ArrayList<>();
    ArrayList<Object> obj = new ArrayList<>();
    MainAdapter mainAdapter;

    RetrofitServices retrofitServices;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_event, container, false);

        retrofitServices  = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

        recyclerViewEvent = view.findViewById(R.id.recyclerViewEvent);
        recyclerViewEvent.setHasFixedSize(true);



        addEvent = view.findViewById(R.id.addEvent);
        addEvent.setOnClickListener(this);
        setRecyclerViewEvent();

        findNearEvent(GlobalHelper.lat,GlobalHelper.lng,"approved");
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findNearEvent(GlobalHelper.lat,GlobalHelper.lng,"approved");
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);

        return view;
    }





    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addEvent){
            Intent intent  =  new Intent(getContext(),EventActivity.class);
            startActivity(intent);
        }

    }

    public void findNearEvent(String lat, String lng,String status){
        retrofitServices.findNearEvent(lat,lng,status).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                anotherEventList.clear();
                nearEventList.clear();
                List<Event> event  =  response.body();
                for(int i=0; i<event.size(); i++){
                    long distance =  Math.round(Double.parseDouble(event.get(i).getDistance())*1000);
                    if(distance>25000){
                       anotherEventList.add(event.get(i));
                    }else{
                       nearEventList.add(event.get(i));
                    }
                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });

    }

    public void setRecyclerViewEvent(){
            mainAdapter =  new MainAdapter(getContext(),nearEventList,anotherEventList);
            recyclerViewEvent.setAdapter(mainAdapter);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerViewEvent.setLayoutManager(layoutManager);

    }



}