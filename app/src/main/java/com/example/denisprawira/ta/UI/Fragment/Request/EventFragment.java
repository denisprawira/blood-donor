package com.example.denisprawira.ta.UI.Fragment.Request;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Adapter2.EventAdapter;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Helper.SessionManager;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.Event.EventDetailActivity;
import com.example.denisprawira.ta.Values.GlobalValues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {



    RecyclerView requestEventRecyclerView;
    View view;
    UserService userService = ApiUtils.getUserService();
    SessionManager sessionManager;
    Spinner requestEventSpinner;
    RecyclerView.LayoutManager layoutManager ;
    EventAdapter eventAdapter;
    TextView requestEventEmptyMessage;

    ArrayList<Event> eventArrayList = new ArrayList<>();

    //recyclerview

//    public EventFragment() {
//
//    }
//



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_request_event, container, false);
        requestEventSpinner = view.findViewById(R.id.request_event_spinner);
        requestEventEmptyMessage = view.findViewById(R.id.requestEventEmptyMessage);
        requestEventRecyclerView  = view.findViewById(R.id.requestEventRecyclerView);
        requestEventRecyclerView.setHasFixedSize(true);


        sessionManager = new SessionManager(getContext());
        initRecyclerView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.eventStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestEventSpinner.setAdapter(adapter);
        requestEventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("result",String.valueOf(i));
                if(i==0){
                    showByUserId(sessionManager.getId(),GlobalValues.EVENT_APPROVED);
                }else if(i==1){
                    showByUserId(sessionManager.getId(),GlobalValues.EVENT_PENDING);
                }else{
                    showByUserId(sessionManager.getId(),GlobalValues.EVENT_DISAPPROVED);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initRecyclerView(){
        eventAdapter = new EventAdapter(1, eventArrayList, getContext(), new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(GlobalValues.EVENT_INTENT,item);
                intent.putExtra(GlobalValues.EVENT_INTENT_TYPE,"pending");
                startActivity(intent);
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        requestEventRecyclerView.setAdapter(eventAdapter);
        requestEventRecyclerView.setLayoutManager(layoutManager);
    }

    public void showByUserId(String id, String status){
        userService.showEvent(id,status).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response!=null){
                    eventArrayList.clear();
                    List<Event> listEvent = response.body();
                    if(listEvent.size()==0){
                        Toast.makeText(getActivity(), "this list empty", Toast.LENGTH_SHORT).show();
                        requestEventEmptyMessage.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getActivity(),"this list not empty",Toast.LENGTH_SHORT).show();
                        requestEventEmptyMessage.setVisibility(View.GONE);
                    }
                    eventArrayList.addAll(listEvent);
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//
//    private void loadRequestEvent(){
//        retrofitServices.loadRequestEvent(sessionManager.getId()).enqueue(new Callback<List<Event>>() {
//            @Override
//            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                requestEventList.clear();
//                List<Event> eventList =  response.body();
//                requestEventList.addAll(eventList);
//                requestEventAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onFailure(Call<List<Event>> call, Throwable t) {
//                Log.e("error : ",t.getMessage());
//            }
//        });
//    }
//
//
//    private void initEventRequestRecyclerView(){
//        requestEventAdapter = new RequestEventAdapter(getActivity(), requestEventList, new RequestEventAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Event item, int position) {
//                if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_APPROVE)){
//                    Intent intent = new Intent(getActivity(), DetailEventRequestActivity.class);
//                    intent.putExtra("idEvent",item.getIdEvent());
//                    intent.putExtra("idUser",item.getIdUser());
//                    intent.putExtra("idUtd",item.getIdUtd());
//                    intent.putExtra("userName",item.getUserName());
//                    intent.putExtra("userPhoto",item.getUserPhoto());
//                    intent.putExtra("userTelp",item.getUserTelp());
//                    intent.putExtra("userToken",item.getUserToken());
//                    intent.putExtra("utdName",item.getUtdName());
//                    intent.putExtra("utdPhoto",item.getUtdPhoto());
//                    intent.putExtra("utdTelp",item.getUtdTelp());
//                    intent.putExtra("utdToken",item.getUtdToken());
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("description",item.getDescription());
//                    intent.putExtra("instansi",item.getInstansi());
//                    intent.putExtra("lat",item.getLat());
//                    intent.putExtra("lng",item.getLng());
//                    intent.putExtra("address",item.getAddress());
//                    intent.putExtra("document",item.getDocument());
//                    intent.putExtra("date",item.getDate());
//                    intent.putExtra("startTime",item.getStartTime());
//                    intent.putExtra("endTime",item.getEndTime());
//                    intent.putExtra("target",item.getTarget());
//                    intent.putExtra("postTime",item.getPostTime());
//                    intent.putExtra("status",item.getStatus());
//                    startActivity(intent);
//                }else if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_PENDING)){
//                    Intent intent = new Intent(getActivity(), DetailEventRequestActivity.class);
//                    intent.putExtra("idEvent",item.getIdEvent());
//                    intent.putExtra("idUser",item.getIdUser());
//                    intent.putExtra("idUtd",item.getIdUtd());
//                    intent.putExtra("userName",item.getUserName());
//                    intent.putExtra("userPhoto",item.getUserPhoto());
//                    intent.putExtra("userTelp",item.getUserTelp());
//                    intent.putExtra("userToken",item.getUserToken());
//                    intent.putExtra("utdName",item.getUtdName());
//                    intent.putExtra("utdPhoto",item.getUtdPhoto());
//                    intent.putExtra("utdTelp",item.getUtdTelp());
//                    intent.putExtra("utdToken",item.getUtdToken());
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("description",item.getDescription());
//                    intent.putExtra("instansi",item.getInstansi());
//                    intent.putExtra("lat",item.getLat());
//                    intent.putExtra("lng",item.getLng());
//                    intent.putExtra("address",item.getAddress());
//                    intent.putExtra("document",item.getDocument());
//                    intent.putExtra("date",item.getDate());
//                    intent.putExtra("startTime",item.getStartTime());
//                    intent.putExtra("endTime",item.getEndTime());
//                    intent.putExtra("target",item.getTarget());
//                    intent.putExtra("postTime",item.getPostTime());
//                    intent.putExtra("status",item.getStatus());
//                    startActivity(intent);
//                }else if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_UNAPPROVE)){
//                    Intent intent  =  new Intent(getActivity(), EventRequestUnapprovedActivity.class);
//                    intent.putExtra("id",item.getIdEvent());
//                    startActivity(intent);
//                }else if(item.getStatus().equals(GlobalHelper.TYPE_EVENT_COMPLETE)){
//                    Intent intent = new Intent(getActivity(), DetailEventRequestActivity.class);
//                    intent.putExtra("idEvent",item.getIdEvent());
//                    intent.putExtra("idUser",item.getIdUser());
//                    intent.putExtra("idUtd",item.getIdUtd());
//                    intent.putExtra("userName",item.getUserName());
//                    intent.putExtra("userPhoto",item.getUserPhoto());
//                    intent.putExtra("userTelp",item.getUserTelp());
//                    intent.putExtra("userToken",item.getUserToken());
//                    intent.putExtra("utdName",item.getUtdName());
//                    intent.putExtra("utdPhoto",item.getUtdPhoto());
//                    intent.putExtra("utdTelp",item.getUtdTelp());
//                    intent.putExtra("utdToken",item.getUtdToken());
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("description",item.getDescription());
//                    intent.putExtra("instansi",item.getInstansi());
//                    intent.putExtra("lat",item.getLat());
//                    intent.putExtra("lng",item.getLng());
//                    intent.putExtra("address",item.getAddress());
//                    intent.putExtra("document",item.getDocument());
//                    intent.putExtra("date",item.getDate());
//                    intent.putExtra("startTime",item.getStartTime());
//                    intent.putExtra("endTime",item.getEndTime());
//                    intent.putExtra("target",item.getTarget());
//                    intent.putExtra("postTime",item.getPostTime());
//                    intent.putExtra("status",item.getStatus());
//                    startActivity(intent);
//                }
//            }
//        });
//        requestEventRecyclerView.setAdapter(requestEventAdapter);
//        //make overlapping recyclerview with minus margin 180dp
//        //requestEventRecyclerView.addItemDecoration(new ItemDecorator(-180));
//        requestEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }

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