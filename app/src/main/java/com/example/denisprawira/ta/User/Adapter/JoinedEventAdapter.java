package com.example.denisprawira.ta.User.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.ReminderDatabase;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.User.Model.Reminder;
import com.example.denisprawira.ta.User.Model.RetrofitResult;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.ReminderActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinedEventAdapter extends RecyclerView.Adapter<JoinedEventAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Event item, int position);
    }
    private ReminderDatabase rb;


    private List<Event> eventList;
    private Context context;
    private final OnItemClickListener listerner;

    SessionManager sessionManager;
    //retrofit services
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    public JoinedEventAdapter(Context context, List<Event> eventList, OnItemClickListener listerner){
        this.eventList = eventList;
        this.context = context;
        this.listerner = listerner;
        sessionManager = new SessionManager(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_joined_event, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        rb = new ReminderDatabase(context);
        holder.bind(eventList.get(position),listerner);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String[] option = {"Batal Mengikuti","Setting Reminder","get all Event in database"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,option);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, option[which], Toast.LENGTH_SHORT).show();
                        if(which ==0){
                            deleteJoinedEvent(sessionManager.getId(),eventList.get(position).getIdEvent());
                            Reminder temp = rb.getReminderByEvent(eventList.get(position).getIdEvent());
                            rb.deleteReminder(temp);
                        }else if(which==1){
                            Intent intent = new Intent(context,ReminderActivity.class);
                            intent.putExtra("idEvent",eventList.get(position).getIdEvent());
                            context.startActivity(intent);
                        }else if(which==2){
                            List<String> mTest = rb.getAllItems();
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,mTest);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setAdapter(adapter1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Reminder temp = rb.getReminderByEvent(eventList.get(position).getIdEvent());
                                    Toast.makeText(context, temp.getReminderTitle(), Toast.LENGTH_SHORT).show();
                                    rb.deleteReminder(temp);
                                }
                            });
                            builder1.show();
                        }
                    }
                });
                builder.show();

                return true;
            }
        });
        if(eventList.get(position).getTitle().length()>27){
            String s = eventList.get(position).getTitle().substring(0,26);
            holder.eventJoinedTitle.setText(s+"...");
        }else{
            holder.eventJoinedTitle.setText(eventList.get(position).getTitle());
        }
        holder.eventJoinedDate.setText(GlobalHelper.convertDate(eventList.get(position).getDate()));
        holder.eventJoinedStartTime.setText(GlobalHelper.convertTime(eventList.get(position).getStartTime()));
        holder.eventJoinedEndTime.setText(GlobalHelper.convertTime(eventList.get(position).getEndTime()));
        if(eventList.get(position).getDescription().length()>140){
            String s = eventList.get(position).getDescription().substring(0,137);
            holder.eventJoinedDescription.setText(s+"...");
        }else{
            holder.eventJoinedDescription.setText(eventList.get(position).getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventJoinedTitle,eventJoinedDate,eventJoinedStartTime,eventJoinedEndTime,eventJoinedDescription;
        public ViewHolder(View v) {
            super(v);
            eventJoinedTitle       = v.findViewById(R.id.eventJoinedTitle);
            eventJoinedDate     = v.findViewById(R.id.eventJoinedDate);
            eventJoinedStartTime   = v.findViewById(R.id.eventJoinedStartTime);
            eventJoinedEndTime = v.findViewById(R.id.eventJonedEndTime);
            eventJoinedDescription= v.findViewById(R.id.eventJoinedDescription);
        }
        //begin of error
        public void bind(final Event item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(item,pos);
                }
            });
        }
        //end of error
    }

    public void deleteJoinedEvent(String idUser, String idEvent){
        retrofitServices.deleteJoinedEvent(idUser,idEvent).enqueue(new Callback<List<RetrofitResult>>() {
            @Override
            public void onResponse(Call<List<RetrofitResult>> call, Response<List<RetrofitResult>> response) {
                List<RetrofitResult> res = response.body();
                for(int i=0; i<res.size();i++){
                    Toast.makeText(context, res.get(i).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RetrofitResult>> call, Throwable t) {

            }
        });
    }
}
