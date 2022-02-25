package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Model.Blood.Data;
import com.example.denisprawira.ta.R;

import java.util.List;

public class BloodStockAdapter extends RecyclerView.Adapter<BloodStockAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Data item, int position);
    }

    private List<Data> bloodStockList;
    private Context context;
    private final OnItemClickListener listerner;


    public BloodStockAdapter(Context context, List<Data> bloodStockList, OnItemClickListener listerner){
        this.bloodStockList = bloodStockList;
        this.context = context;
        this.listerner = listerner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_blood_list, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.bind(bloodStockList.get(i),listerner);
        holder.bloodStockGolDarah.setText(bloodStockList.get(i).getId());
        holder.bloodStockComponent.setText(bloodStockList.get(i).getProvinsi());
        holder.bloodStockPmi.setText(bloodStockList.get(i).getUnit());
        holder.bloodStockQty.setText(bloodStockList.get(i).getJumlah());


    }

    @Override
    public int getItemCount() {
        return bloodStockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView bloodStockGolDarah, bloodStockComponent, bloodStockPmi, bloodStockQty;
        public ViewHolder(View v) {
            super(v);
            bloodStockGolDarah  = v.findViewById(R.id.bloodStockGolDarah);
            bloodStockComponent = v.findViewById(R.id.bloodStockComponent);
            bloodStockPmi       = v.findViewById(R.id.bloodStockPmi);
            bloodStockQty       = v.findViewById(R.id.bloodStockQty);


        }
        //begin of error
        public void bind(final Data item, final OnItemClickListener listener) {

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
