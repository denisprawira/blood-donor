package com.example.denisprawira.ta.UI.Fragment.Joined;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Adapter2.EventAdapter;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.SessionManager.UserSessionManager;
import com.example.denisprawira.ta.UI.Event.EventDetailActivity;
import com.example.denisprawira.ta.Values.GlobalValues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {


    ArrayList<Event> eventArrayList= new ArrayList<>() ;

    RecyclerView.LayoutManager layoutManager;
    EventAdapter eventAdapter;



    UserSessionManager userSessionManager ;
    UserService userService = ApiUtils.getUserService();

    RecyclerView recyclerView;
    TextView textViewJoinedEvent;
    public EventFragment() {

    }

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_joined_event, container, false);
        userSessionManager =  new UserSessionManager(getContext());
        recyclerView = view.findViewById(R.id.eventJoinedRecyclerView);
        textViewJoinedEvent = view.findViewById(R.id.textViewJoinedEvent);
        initRecyclerView();
        showJoined();
        return view;
    }

    public void initRecyclerView(){
        eventAdapter = new EventAdapter(2, eventArrayList, getContext(), new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(GlobalValues.EVENT_INTENT,item);
                intent.putExtra(GlobalValues.EVENT_INTENT_TYPE,"joined");
                startActivity(intent);
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void showJoined(){
        userService.showJoined(userSessionManager.getId()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
               eventArrayList.clear();
                List<Event> eventList = response.body();
               eventArrayList.addAll(eventList);
               if(eventList.size()==0){
                   textViewJoinedEvent.setVisibility(View.VISIBLE);
               }
               eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showJoined();
    }
}