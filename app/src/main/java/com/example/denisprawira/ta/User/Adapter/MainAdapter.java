package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Helper.CustomRecyclerView;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Event.EventDetailActivity;
import com.example.denisprawira.ta.User.UI.Event.EventMapsActivity;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    static final int ITEM_HORIZONTAL = 0;
    static final int ITEM_VERTICAL = 1;
    private Parcelable recyclerViewState;





    EventNearAdapter eventNearAdapter;
    EventOtherAdapter eventOtherAdapter;
    Context context;
    ArrayList<Object> items;
    ArrayList<Event> horList;
    ArrayList<Event> verList;

    public MainAdapter(Context context, ArrayList<Event> horList, ArrayList<Event> verList) {
        this.context = context;
        this.horList = horList;
        this.verList = verList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder vh = null;
        switch(i){
            case ITEM_HORIZONTAL:
                view = layoutInflater.inflate(R.layout.layout_event_top,viewGroup,false);
                vh =  new HorizontalViewHolder(view);
                break;
            case ITEM_VERTICAL:
                view = layoutInflater.inflate(R.layout.layout_event_bottom,viewGroup,false);
                vh = new VerticalViewHolder(view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.layout_event_bottom,viewGroup,false);
                vh = new VerticalViewHolder(view);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType()==ITEM_VERTICAL){
            verticalView((VerticalViewHolder) viewHolder);
        }else if(viewHolder.getItemViewType()==ITEM_HORIZONTAL){
            horizontalView((HorizontalViewHolder) viewHolder);
        }

    }



    public void verticalView(VerticalViewHolder verticalViewHolder){
        eventOtherAdapter =  new EventOtherAdapter(verList, context, new EventOtherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {
                Intent intent = new Intent(context,EventDetailActivity.class);
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
                context.startActivity(intent);
            }
        });
//        verticalViewHolder.customRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        verticalViewHolder.customRecyclerView.setAdapter(eventOtherAdapter);
        //verticalViewHolder.customRecyclerView.setEmptyView(verticalViewHolder.empty);
        if(eventOtherAdapter.getItemCount()==0){
            verticalViewHolder.eventLainnya.setVisibility(View.GONE);
        }else {
            verticalViewHolder.eventLainnya.setVisibility(View.VISIBLE);
        }
    }

    public void horizontalView(final HorizontalViewHolder horizontalViewHolder){
        horizontalViewHolder.eventMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EventMapsActivity.class);
                context.startActivity(intent);
            }
        });
            if(eventNearAdapter ==null){
                eventNearAdapter = new EventNearAdapter(horList, context, new EventNearAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Event item, int position) {
                        Intent intent = new Intent(context,EventDetailActivity.class);
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
                        context.startActivity(intent);
                    }
                });
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                horizontalViewHolder.recyclerview.setLayoutManager(linearLayoutManager);
                horizontalViewHolder.recyclerview.setAdapter(eventNearAdapter);
                horizontalViewHolder.recyclerview.setHasFixedSize(true);
                if(eventNearAdapter !=null){
                    eventNearAdapter.notifyItemRangeChanged(0, eventNearAdapter.getItemCount());
                    if(eventNearAdapter.getItemCount()==0){
                        horizontalViewHolder.eventTerdekat.setVisibility(View.GONE);
                    }else {
                        horizontalViewHolder.eventTerdekat.setVisibility(View.VISIBLE);
                    }
                }
            }else {
                if(eventNearAdapter !=null){
                    eventNearAdapter.notifyItemRangeChanged(0, eventNearAdapter.getItemCount());
                    if(eventNearAdapter.getItemCount()==0){
                        horizontalViewHolder.eventTerdekat.setVisibility(View.GONE);
                    }else {
                        horizontalViewHolder.eventTerdekat.setVisibility(View.VISIBLE);
                    }
                }

            }


//        final LinearSnapHelper snapHelper = new LinearSnapHelper();
//        horizontalViewHolder.recyclerview.setOnFlingListener(null);
//        snapHelper.attachToRecyclerView(horizontalViewHolder.recyclerview);
    }



    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==ITEM_VERTICAL)
            return ITEM_VERTICAL;
        if(position ==ITEM_HORIZONTAL)
            return ITEM_HORIZONTAL;
        return -1;
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        CustomRecyclerView customRecyclerView;
        //TextView empty;
        TextView eventLainnya;
        VerticalViewHolder( View itemView) {
            super(itemView);
            customRecyclerView  = (CustomRecyclerView) itemView.findViewById(R.id.verticalRecyclerview);
            //empty               = itemView.findViewById(android.R.id.empty);
            eventLainnya        = itemView.findViewById(R.id.eventLainnya);
        }
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerview;
        TextView eventTerdekat;
        CardView eventMap;
        HorizontalViewHolder( View itemView) {
            super(itemView);
            eventTerdekat = itemView.findViewById(R.id.eventTerdekat);
            recyclerview  = (RecyclerView)itemView.findViewById(R.id.horizontalRecyclerview);
            eventMap      = itemView.findViewById(R.id.eventMap);
        }
    }

    public class EmptyHolder extends RecyclerView.ViewHolder{

        EmptyHolder(View itemView) {
            super(itemView);

        }
    }



}
