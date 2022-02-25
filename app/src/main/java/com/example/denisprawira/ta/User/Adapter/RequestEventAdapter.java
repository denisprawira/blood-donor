package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;

import java.util.List;

public class RequestEventAdapter extends RecyclerView.Adapter<RequestEventAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Event item, int position);
    }

    private List<Event> eventList;
    private Context context;
    private final OnItemClickListener listerner;


    public RequestEventAdapter(Context context, List<Event> eventList, OnItemClickListener listerner){
        this.eventList = eventList;
        this.context = context;
        this.listerner = listerner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_request_event, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(eventList.get(i),listerner);
        if(eventList.get(i).getTitle().length()>27){
            String s = eventList.get(i).getTitle().substring(0,26);
            holder.eventRequestTittle.setText(s+"...");
        }else{
            holder.eventRequestTittle.setText(eventList.get(i).getTitle());
        }
        holder.eventRequestTittle.setText(eventList.get(i).getTitle());
        holder.eventRequestDate.setText(GlobalHelper.convertDate(eventList.get(i).getDate()));
        holder.eventRequestStartTime.setText(GlobalHelper.convertTime(eventList.get(i).getStartTime()));
        holder.eventRequestEndTime.setText(GlobalHelper.convertTime(eventList.get(i).getEndTime()));
        if(eventList.get(i).getDescription().length()>140){
            String s = eventList.get(i).getDescription().substring(0,137);
            holder.eventRequestDescription.setText(s+"...");
        }else{
            holder.eventRequestDescription.setText(eventList.get(i).getDescription());
        }
        if(eventList.get(i).getStatus().equals("Pending")){
            holder.eventRequestStatus.setText("Pending");
            holder.eventRequestStatus.setTextColor(Color.parseColor("#bc8600") );
            holder.eventRequestStatus.setBackground(context.getResources().getDrawable(R.drawable.theme_status_background_pending));
        }else if(eventList.get(i).getStatus().equals("Unapproved")){
            holder.eventRequestStatus.setText("Unapproved");
            holder.eventRequestStatus.setTextColor(Color.parseColor("#c41c00") );
            holder.eventRequestStatus.setBackground(context.getResources().getDrawable(R.drawable.theme_status_background_unapproved));
        }else if(eventList.get(i).getStatus().equals("Complete")){
            holder.eventRequestStatus.setText("Complete");
            holder.eventRequestStatus.setTextColor(Color.parseColor("#0051b2") );
            holder.eventRequestStatus.setBackground(context.getResources().getDrawable(R.drawable.theme_status_background_complete));
        }else if(eventList.get(i).getStatus().equals("Approved")){
            holder.eventRequestStatus.setText("Approved");
            holder.eventRequestStatus.setTextColor(Color.parseColor("#40ad00"));
            holder.eventRequestStatus.setBackground(context.getResources().getDrawable(R.drawable.theme_status_background_approved));
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventRequestTittle, eventRequestDate, eventRequestStartTime, eventRequestEndTime,eventRequestDescription,eventRequestStatus;
        public ViewHolder(View v) {
            super(v);
            eventRequestTittle      = v.findViewById(R.id.eventRequestTittle);
            eventRequestDate        = v.findViewById(R.id.eventRequestDate);
            eventRequestStartTime   = v.findViewById(R.id.eventRequestStartTime);
            eventRequestEndTime     = v.findViewById(R.id.eventRequestEndTime);
            eventRequestDescription = v.findViewById(R.id.eventRequestDescription);
            eventRequestStatus      = v.findViewById(R.id.eventRequestStatus);
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
