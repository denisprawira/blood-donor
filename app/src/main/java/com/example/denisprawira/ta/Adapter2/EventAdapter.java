package com.example.denisprawira.ta.Adapter2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Helper.TimeConverter;
import com.example.denisprawira.ta.Values.GlobalValues;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Event> eventList;
    private Context context;
    private OnItemClickListener listener;
    private int viewTypes;

    private final int VIEW_REQUEST = 1;
    private final int VIEW_JOINED = 2;



    public interface OnItemClickListener{
        void onItemClick(Event item, int position);
    }

    public EventAdapter(int viewTypes,List<Event> eventList, Context context, OnItemClickListener listener) {
        this.eventList = eventList;
        this.context = context;
        this.listener = listener;
        this.viewTypes = viewTypes;
    }

    public EventAdapter(List<Event> eventList, Context context, OnItemClickListener listener) {
        this.eventList = eventList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder vh = null;
        switch(viewTypes){
            case VIEW_REQUEST:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_card_request_event,parent,false);
                vh = new RequestViewHolder(view);
                break;
            case VIEW_JOINED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_card_joined_event,parent,false);
                vh = new JoinedViewHolder(view);
                break;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_list_other_event,parent,false);
                vh = new DefaultViewHolder(view);
        }
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewTypes==VIEW_REQUEST){
            requestView((RequestViewHolder) viewHolder,position);
        }else if(viewTypes==VIEW_JOINED){
            joinedView((JoinedViewHolder) viewHolder,position);
        }else{
            defaultView((DefaultViewHolder) viewHolder,position);
        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    ////////////////////////////// VIEWHOLDER //////////////////////////////////////
    public static class DefaultViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle, eventAddress, eventDistance, eventDate, eventStartTime, eventEndTime;
        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.otherEventTitle);
            eventAddress = itemView.findViewById(R.id.otherEventAddress);
            eventDistance = itemView.findViewById(R.id.otherEventDistance);
            eventDate = itemView.findViewById(R.id.otherEventDate);
            eventEndTime = itemView.findViewById(R.id.otherEventEndTime);
            eventStartTime = itemView.findViewById(R.id.otherEventStartTime);
        }

        public void bind(Event item, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  = getAdapterPosition();
                    listener.onItemClick(item,position);
                }
            });
        }
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView eventRequestTittle, eventRequestDate, eventRequestStartTime, eventRequestEndDate,eventRequestAddress,eventRequestStatus;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            eventRequestTittle = itemView.findViewById(R.id.eventRequestTittle);
            eventRequestDate = itemView.findViewById(R.id.eventRequestDate);
            eventRequestStartTime = itemView.findViewById(R.id.eventRequestStartTime);
            eventRequestAddress = itemView.findViewById(R.id.eventRequestAddress);
            eventRequestEndDate = itemView.findViewById(R.id.eventRequestEndTime);
            eventRequestStatus = itemView.findViewById(R.id.eventRequestStatus);
        }

        public void bind(Event item, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  = getAdapterPosition();
                    listener.onItemClick(item,position);
                }
            });
        }
    }

    public static class JoinedViewHolder extends RecyclerView.ViewHolder{
        TextView eventJoinedTitle,eventJoinedDate,eventJoinedStartDate,eventJoinedEndDate,eventJoinedAddress;
        public JoinedViewHolder(@NonNull View itemView) {
            super(itemView);
            eventJoinedTitle = itemView.findViewById(R.id.eventJoinedTitle);
            eventJoinedDate = itemView.findViewById(R.id.eventJoinedDate);
            eventJoinedStartDate = itemView.findViewById(R.id.eventJoinedStartTime);
            eventJoinedEndDate = itemView.findViewById(R.id.eventJoinedEndDate);
            eventJoinedAddress = itemView.findViewById(R.id.eventJoinedAddress);
        }

        public void bind(Event item, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  = getAdapterPosition();
                    listener.onItemClick(item,position);
                }
            });
        }
    }




    ////////////////////////////// VIEW //////////////////////////////////////
    public  void defaultView (final DefaultViewHolder defaultViewHolder,int position){
        defaultViewHolder.bind(eventList.get(position),listener);
        defaultViewHolder.eventTitle.setText(eventList.get(position).getTitle());
        defaultViewHolder.eventAddress.setText(eventList.get(position).getAddress());
        defaultViewHolder.eventDistance.setText(eventList.get(position).getDistance()+" KM");
        defaultViewHolder.eventDate.setText(TimeConverter.convertDateTimeToDay(eventList.get(position).getDateStart())+" "+TimeConverter.convertDateTimeToMonth(eventList.get(position).getDateStart()));
        defaultViewHolder.eventStartTime.setText(TimeConverter.convertDateTimeToTime(eventList.get(position).getDateStart()));
        //defaultViewHolder.eventEndTime.setText(TimeConverter.convertDateTimeToTime(eventList.get(position).getDateEnd()));
        Log.e("viewType holder",String.valueOf(viewTypes));
    }

    public  void requestView (final RequestViewHolder requestViewHolder,int position){
        requestViewHolder.bind(eventList.get(position),listener);
        requestViewHolder.eventRequestTittle.setText(eventList.get(position).getTitle());
        requestViewHolder.eventRequestAddress.setText(eventList.get(position).getAddress());
        requestViewHolder.eventRequestDate.setText(TimeConverter.convertDateTimeToDay(eventList.get(position).getDateStart())+" "+TimeConverter.convertDateTimeToMonth(eventList.get(position).getDateStart()));
        requestViewHolder.eventRequestStartTime.setText(TimeConverter.convertDateTimeToTime(eventList.get(position).getDateStart()));
        if(eventList.get(position).getDateEnd()==null){
            requestViewHolder.eventRequestEndDate.setText("--:--");
        }else{
            requestViewHolder.eventRequestEndDate.setText(TimeConverter.convertDateTimeToTime(eventList.get(position).getDateEnd()));
        }
        if(eventList.get(position).getStatus().equals(GlobalValues.EVENT_APPROVED)){
            requestViewHolder.eventRequestStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.theme_status_background_approved));
            requestViewHolder.eventRequestStatus.setTextColor(ContextCompat.getColor(context,R.color.colorStatusApproved));
        }else if(eventList.get(position).getStatus().equals(GlobalValues.EVENT_PENDING)){
            requestViewHolder.eventRequestStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.theme_status_background_pending));
            requestViewHolder.eventRequestStatus.setTextColor(ContextCompat.getColor(context,R.color.colorStatusPending));
        }
        requestViewHolder.eventRequestStatus.setText(eventList.get(position).getStatus());
        Log.e("viewType holder",String.valueOf(viewTypes));
    }

    public void joinedView(final JoinedViewHolder joinedViewHolder, int position){
        joinedViewHolder.bind(eventList.get(position),listener);
        joinedViewHolder.eventJoinedTitle.setText(eventList.get(position).getTitle());
        joinedViewHolder.eventJoinedAddress.setText(eventList.get(position).getAddress());
        joinedViewHolder.eventJoinedDate.setText(TimeConverter.convertDateTimeToDay(eventList.get(position).getDateStart())+" "+TimeConverter.convertDateTimeToMonth(eventList.get(position).getDateStart()));
        joinedViewHolder.eventJoinedStartDate.setText(TimeConverter.convertDateTimeToTime(eventList.get(position).getDateStart()));
        if(eventList.get(position).getDateEnd()==null){
            joinedViewHolder.eventJoinedEndDate.setText("--:--");
        }else{
            joinedViewHolder.eventJoinedEndDate.setText(TimeConverter.convertDateTimeToTime(eventList.get(position).getDateEnd()));
        }
    }







}
