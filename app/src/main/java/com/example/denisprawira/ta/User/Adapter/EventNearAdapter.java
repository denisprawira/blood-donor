package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;

import java.util.ArrayList;

public class EventNearAdapter extends RecyclerView.Adapter<EventNearAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Event item, int position);
    }
    ArrayList<Event> eventList;
    Context context;



    private final OnItemClickListener listerner;



    public EventNearAdapter(ArrayList<Event> eventList, Context context, OnItemClickListener listerner) {
        this.eventList = eventList;
        this.context = context;
        this.listerner = listerner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_near_event,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(eventList.get(i),listerner);
        Glide.with(context).load(eventList.get(i).getUtdPhoto()).into(viewHolder.nearEventPmiPhoto);
        viewHolder.nearEventTitle.setText(eventList.get(i).getTitle());
        viewHolder.nearEventDate.setText(GlobalHelper.convertDate(eventList.get(i).getDate()));
        viewHolder.nearEventAddress.setText(eventList.get(i).getAddress());
        viewHolder.nearEventStartTime.setText(GlobalHelper.convertTime(eventList.get(i).getStartTime()));
        viewHolder.nearEventEndTime.setText(GlobalHelper.convertTime(eventList.get(i).getEndTime()));
        long distance =  Math.round(Double.parseDouble(eventList.get(i).getDistance())*1000);
        if(distance>=1000){
            Double result = distance /1000.00;
            viewHolder.nearEventDistance.setText(String.format("%.2f",result )+" Km");
        }else{
            viewHolder.nearEventDistance.setText(String.valueOf(distance)+" M");
        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView nearEventPmiPhoto;
        TextView nearEventTitle, nearEventDate,nearEventAddress,nearEventStartTime,nearEventEndTime, nearEventDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nearEventPmiPhoto   = itemView.findViewById(R.id.nearEventPmiPhoto);
            nearEventTitle      = itemView.findViewById(R.id.nearEventTitle);
            nearEventDate       = itemView.findViewById(R.id.nearEventDate);
            nearEventAddress    = itemView.findViewById(R.id.nearEventAddress);
            nearEventStartTime  = itemView.findViewById(R.id.nearEventStartTime);
            nearEventEndTime    = itemView.findViewById(R.id.nearEventEndTime);
            nearEventDistance   = itemView.findViewById(R.id.nearEventDistance);

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
