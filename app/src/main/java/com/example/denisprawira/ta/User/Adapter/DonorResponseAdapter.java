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
import com.example.denisprawira.ta.User.Model.User;
import com.example.denisprawira.ta.R;

import java.util.List;

public class DonorResponseAdapter extends RecyclerView.Adapter<DonorResponseAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(User item, int position);
    }

    private List<User> userList;
    private Context context;
    private final OnItemClickListener listerner;

    public DonorResponseAdapter(Context context, List<User> userList, OnItemClickListener listerner){
        this.userList = userList;
        this.context = context;
        this.listerner = listerner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_donor, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(userList.get(position),listerner);
        holder.donorName.setText(userList.get(position).getNama());
        Glide.with(context).load(userList.get(position).getPhoto()).into(holder.donorProfileImage);
        long distance =  Math.round(Double.parseDouble(userList.get(position).getDistance())*1000);
        if(distance>=1000){
            Double result = distance /1000.00;
            holder.donorDistance.setText(String.format("%.2f",result )+" Km");
        }else{
            holder.donorDistance.setText(String.valueOf(distance)+" M");
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView donorName, donorDistance;
        ImageView donorProfileImage;
        public ViewHolder(View v) {
            super(v);
            donorName           = v.findViewById(R.id.donorName);
            donorDistance       = v.findViewById(R.id.donorDistance);
            donorProfileImage   = v.findViewById(R.id.donorProfileImage);

        }
        //begin of error
        public void bind(final User item, final OnItemClickListener listener) {

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
}
