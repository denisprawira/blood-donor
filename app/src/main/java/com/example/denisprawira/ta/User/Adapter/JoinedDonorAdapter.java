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
import com.example.denisprawira.ta.User.Model.Donor.JoinedDonor;
import com.example.denisprawira.ta.User.Model.Reminder;
import com.example.denisprawira.ta.User.Model.RetrofitResult;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.ReminderActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinedDonorAdapter extends RecyclerView.Adapter<JoinedDonorAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(JoinedDonor item, int position);
    }
    private ReminderDatabase rb;


    private List<JoinedDonor> donorList;
    private Context context;
    private final OnItemClickListener listerner;

    SessionManager sessionManager;

    //retrofit services
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    public JoinedDonorAdapter(Context context, List<JoinedDonor> donorList, OnItemClickListener listerner){
        this.donorList = donorList;
        this.context = context;
        this.listerner = listerner;
        sessionManager = new SessionManager(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_joined_donor, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        rb = new ReminderDatabase(context);
        holder.bind(donorList.get(position),listerner);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String[] option = {"Batal Mengikuti","Setting Reminder","get all Event in database"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,option);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0){
                            deleteJoinedEvent(sessionManager.getId(),donorList.get(position).getIdDonor());
                            Reminder temp = rb.getReminderByEvent(donorList.get(position).getIdDonor());
                            rb.deleteReminder(temp);
                        }else if(which==1){
                            Intent intent = new Intent(context,ReminderActivity.class);
                            intent.putExtra("idEvent",donorList.get(position).getIdDonor());
                            context.startActivity(intent);
                        }else if(which==2){
                            List<String> mTest = rb.getAllItems();
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,mTest);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setAdapter(adapter1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Reminder temp = rb.getReminderByEvent(donorList.get(position).getIdDonor());
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

        holder.donorJoinedDay.setText(GlobalHelper.convertDateTimeToDay(donorList.get(position).getTanggal()));
        holder.donorJoinedMonth.setText(GlobalHelper.convertDateTimeToMonth(donorList.get(position).getTanggal()));
        holder.donorJoinedUtdName.setText(donorList.get(position).getUtdName());
        holder.donorJoinedTime.setText(GlobalHelper.convertDateTimeToTime(donorList.get(position).getTanggal()));
        holder.donorJoinedAddress.setText(donorList.get(position).getUtdAddress());

    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView donorJoinedDay,donorJoinedMonth,donorJoinedUtdName,donorJoinedTime,donorJoinedAddress;
        public ViewHolder(View v) {
            super(v);
            donorJoinedDay       = v.findViewById(R.id.donorJoinedDay);
            donorJoinedMonth     = v.findViewById(R.id.donorJoinedMonth);
            donorJoinedUtdName   = v.findViewById(R.id.donorJoinedUtdName);
            donorJoinedTime      = v.findViewById(R.id.donorJoinedTime);
            donorJoinedAddress   = v.findViewById(R.id.donorJoinedAddress);



        }
        //begin of error
        public void bind(final JoinedDonor item, final OnItemClickListener listener) {

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
