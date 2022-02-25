package com.example.denisprawira.ta.User.UI.Fragment.Joined;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.JoinedEventAdapter;
import com.example.denisprawira.ta.User.Helper.CustomRecyclerView;
import com.example.denisprawira.ta.User.Helper.DividerItemDecoration;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Event.EventDetailActivity;
import com.example.denisprawira.ta.User.UI.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {


    ArrayList<Event> joinedEventList= new ArrayList<>() ;

    RecyclerView.LayoutManager layoutManager;
    JoinedEventAdapter joinedEventAdapter;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    SessionManager sessionManager;
    CustomRecyclerView customRecyclerView;

    public EventFragment() {

    }

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_joined_event, container, false);
        sessionManager =  new SessionManager(getActivity());
        Toast.makeText(getContext(), "Event fragment", Toast.LENGTH_SHORT).show();
        customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.joinedEventRecyclerView);


        initRecyclerView();
        loadJoinedEvent();
        return view;
    }

    public void initRecyclerView(){
        joinedEventAdapter = new JoinedEventAdapter(getContext(), joinedEventList, new JoinedEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {
                Intent intent = new Intent(getActivity(),EventDetailActivity.class);
                intent.putExtra("idEvent",item.getIdEvent());
                intent.putExtra("idUser",item.getIdUser());
                intent.putExtra("idUtd",item.getIdUtd());
                intent.putExtra("userName",item.getUserName());
                intent.putExtra("userPhoto",item.getUserPhoto());
                intent.putExtra("userTelp",item.getUserTelp());
                intent.putExtra("userToken",item.getUserToken());
                intent.putExtra("utdName",item.getUtdName());
                intent.putExtra("utdPhoto",item.getUtdPhoto());
                intent.putExtra("utdTelp",item.getUtdTelp());
                intent.putExtra("utdToken",item.getUtdToken());
                intent.putExtra("title",item.getTitle());
                intent.putExtra("description",item.getDescription());
                intent.putExtra("instansi",item.getInstansi());
                intent.putExtra("target",item.getTarget());
                intent.putExtra("lat",item.getLat());
                intent.putExtra("lng",item.getLng());
                intent.putExtra("address",item.getAddress());
                intent.putExtra("document",item.getDescription());
                intent.putExtra("date",item.getDate());
                intent.putExtra("startTime",item.getStartTime());
                intent.putExtra("endTime",item.getEndTime());
                intent.putExtra("distance",item.getDistance());
                getActivity().startActivity(intent);
            }
        });
        customRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        customRecyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        customRecyclerView.setHasFixedSize(true);
        customRecyclerView.setAdapter(joinedEventAdapter);

    }

    public void loadJoinedEvent(){

        retrofitServices.loadJoinedEvent(sessionManager.getId(), GlobalHelper.lat,GlobalHelper.lng,"approved").enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> list = response.body();


                joinedEventList.addAll(list);
                joinedEventAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
     
            }
        });
    }







}