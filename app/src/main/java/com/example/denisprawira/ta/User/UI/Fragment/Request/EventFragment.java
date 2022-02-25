package com.example.denisprawira.ta.User.UI.Fragment.Request;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.RequestEventAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Event.DetailEventRequestActivity;
import com.example.denisprawira.ta.User.UI.Event.EventRequestUnapprovedActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {



    RequestEventAdapter requestEventAdapter;
    ArrayList<Event> requestEventList = new ArrayList<>();

    SessionManager sessionManager;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    RecyclerView requestEventRecyclerView;

    View view;

    public EventFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_request_event, container, false);
        requestEventRecyclerView  = view.findViewById(R.id.requestEventRecyclerView);
        sessionManager = new SessionManager(getActivity());
        initEventRequestRecyclerView();
        loadRequestEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       loadRequestEvent();
    }

    

    private void loadRequestEvent(){
        retrofitServices.loadRequestEvent(sessionManager.getId()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                requestEventList.clear();
                List<Event> eventList =  response.body();
                requestEventList.addAll(eventList);
                requestEventAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("error : ",t.getMessage());
            }
        });
    }


    private void initEventRequestRecyclerView(){
        requestEventAdapter = new RequestEventAdapter(getActivity(), requestEventList, new RequestEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {
                if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_APPROVE)){
                    Intent intent = new Intent(getActivity(), DetailEventRequestActivity.class);
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
                    intent.putExtra("lat",item.getLat());
                    intent.putExtra("lng",item.getLng());
                    intent.putExtra("address",item.getAddress());
                    intent.putExtra("document",item.getDocument());
                    intent.putExtra("date",item.getDate());
                    intent.putExtra("startTime",item.getStartTime());
                    intent.putExtra("endTime",item.getEndTime());
                    intent.putExtra("target",item.getTarget());
                    intent.putExtra("postTime",item.getPostTime());
                    intent.putExtra("status",item.getStatus());
                    startActivity(intent);
                }else if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_PENDING)){
                    Intent intent = new Intent(getActivity(), DetailEventRequestActivity.class);
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
                    intent.putExtra("lat",item.getLat());
                    intent.putExtra("lng",item.getLng());
                    intent.putExtra("address",item.getAddress());
                    intent.putExtra("document",item.getDocument());
                    intent.putExtra("date",item.getDate());
                    intent.putExtra("startTime",item.getStartTime());
                    intent.putExtra("endTime",item.getEndTime());
                    intent.putExtra("target",item.getTarget());
                    intent.putExtra("postTime",item.getPostTime());
                    intent.putExtra("status",item.getStatus());
                    startActivity(intent);
                }else if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_UNAPPROVE)){
                    Intent intent  =  new Intent(getActivity(), EventRequestUnapprovedActivity.class);
                    intent.putExtra("id",item.getIdEvent());
                    startActivity(intent);
                }else if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_COMPLETE)){
                    Intent intent = new Intent(getActivity(), DetailEventRequestActivity.class);
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
                    intent.putExtra("lat",item.getLat());
                    intent.putExtra("lng",item.getLng());
                    intent.putExtra("address",item.getAddress());
                    intent.putExtra("document",item.getDocument());
                    intent.putExtra("date",item.getDate());
                    intent.putExtra("startTime",item.getStartTime());
                    intent.putExtra("endTime",item.getEndTime());
                    intent.putExtra("target",item.getTarget());
                    intent.putExtra("postTime",item.getPostTime());
                    intent.putExtra("status",item.getStatus());
                    startActivity(intent);
                }
            }
        });
        requestEventRecyclerView.setAdapter(requestEventAdapter);
        //make overlapping recyclerview with minus margin 180dp
        //requestEventRecyclerView.addItemDecoration(new ItemDecorator(-180));
        requestEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //important to make overlapping recyclerview
//    public class ItemDecorator extends RecyclerView.ItemDecoration {
//        private final int mSpace;
//
//        public ItemDecorator(int space) {
//            this.mSpace = space;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view);
//            if (position != 0)
//                outRect.top = mSpace;
//        }
//    }






}