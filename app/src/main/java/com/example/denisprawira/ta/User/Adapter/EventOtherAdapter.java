package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;

import java.util.ArrayList;

public class EventOtherAdapter extends RecyclerView.Adapter<EventOtherAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Event item, int position);
    }

    ArrayList<Event> eventList;
    Context context;

    private final OnItemClickListener listerner;

    public EventOtherAdapter(ArrayList<Event> eventList, Context context, OnItemClickListener listerner) {
        this.eventList = eventList;
        this.context = context;
        this.listerner = listerner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_other_event,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(eventList.get(i),listerner);
        viewHolder.otherEventTitle.setText(eventList.get(i).getTitle());
        viewHolder.otherEventDate.setText(GlobalHelper.convertDate(eventList.get(i).getDate()));
        viewHolder.otherEventStartTime.setText(GlobalHelper.convertTime(eventList.get(i).getStartTime()));
        viewHolder.otherEventEndTime.setText(GlobalHelper.convertTime(eventList.get(i).getEndTime()));
        viewHolder.otherEventAddress.setText(eventList.get(i).getAddress());

        long distance =  Math.round(Double.parseDouble(eventList.get(i).getDistance())*1000);
        if(distance>=1000){
            Double result = distance /1000.00;
            viewHolder.otherEventDistance.setText(String.format("%.2f",result )+" Km");
        }else{
            viewHolder.otherEventDistance.setText(String.valueOf(distance)+" Meter");
        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView otherEventTitle,otherEventDate,otherEventStartTime,otherEventEndTime,otherEventDistance,otherEventAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherEventTitle         = itemView.findViewById(R.id.otherEventTitle);
            otherEventDate          = itemView.findViewById(R.id.otherEventDate);
            otherEventStartTime     = itemView.findViewById(R.id.otherEventStartTime);
            otherEventEndTime       = itemView.findViewById(R.id.otherEventEndTime);
            otherEventDistance      = itemView.findViewById(R.id.otherEventDistance);
            otherEventAddress       = itemView.findViewById(R.id.otherEventAddress);
        }

        public void bind(final Event item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(item,pos);
                }
            });
        }
    }
}
