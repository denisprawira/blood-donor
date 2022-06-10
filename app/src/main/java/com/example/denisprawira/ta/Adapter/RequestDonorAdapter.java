package com.example.denisprawira.ta.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Api.Model.HelpRequest;
import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.Helper.RetrofitHelper;
import com.example.denisprawira.ta.Helper.SessionManager;
import com.example.denisprawira.ta.Api.RetrofitServices;
import com.example.denisprawira.ta.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.Model.Reminder;
import com.example.denisprawira.ta.Model.RetrofitResult;
import com.example.denisprawira.ta.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDonorAdapter extends RecyclerView.Adapter<RequestDonorAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(HelpRequest item, int position);
    }


    private List<HelpRequest> donorList;
    private Context context;
    private final OnItemClickListener listerner;
    SessionManager sessionManager;
    //retrofit services
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    public RequestDonorAdapter(Context context, List<HelpRequest> donorList, OnItemClickListener listerner){
        this.donorList = donorList;
        this.context = context;
        this.listerner = listerner;
        sessionManager = new SessionManager(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_request_donor, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(donorList.get(position),listerner);
        holder.donorRequestDay.setText(GlobalHelper.convertDateTimeToDay(donorList.get(position).getPostAt()));
        holder.donorRequestMonth.setText(GlobalHelper.convertDateTimeToMonth(donorList.get(position).getPostAt()));
        holder.donorRequestTime.setText(donorList.get(position).getUser().getName());
        holder.donorRequestUtdName.setText(GlobalHelper.convertDateTimeToTime(donorList.get(position).getPostAt()));
        //holder.donorRequestAddress.setText(donorList.get(position).getUtdAddress());
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView donorRequestDay,donorRequestMonth, donorRequestTime,donorRequestUtdName,donorRequestAddress;
        public ViewHolder(View v) {
            super(v);
            donorRequestDay      = v.findViewById(R.id.donorRequestDay);
            donorRequestMonth    = v.findViewById(R.id.donorRequestMonth);
            donorRequestTime     = v.findViewById(R.id.donorRequestTime);
            donorRequestUtdName  = v.findViewById(R.id.donorRequestUtdName);
            donorRequestAddress  = v.findViewById(R.id.donorRequestAddress);



        }
        //begin of error
        public void bind( HelpRequest item,  OnItemClickListener listener) {

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
