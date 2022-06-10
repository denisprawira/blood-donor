package com.example.denisprawira.ta.Adapter2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Api.Model.Blood;
import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.R;

import java.util.List;
import java.util.concurrent.BlockingDeque;

public class BloodResultAdapter extends RecyclerView.Adapter<BloodResultAdapter.ViewHolder> {
    private List<Blood> bloodList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Blood item, int position);
    }

    public BloodResultAdapter(List<Blood> bloodList, Context context, OnItemClickListener listener) {
        this.bloodList = bloodList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_dialog_pmi,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(bloodList.get(position),listener);
        holder.pmiName.setText(bloodList.get(position).getBloodType());

    }

    @Override
    public int getItemCount() {
        return bloodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pmiName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pmiName = itemView.findViewById(R.id.pmiActivityResultName);
        }

        public void bind(Blood item, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  = getAdapterPosition();
                    listener.onItemClick(item,position);
                }
            });
        }
    }

    public void updateData(List<Blood> list){
        bloodList = list;
    }
}
