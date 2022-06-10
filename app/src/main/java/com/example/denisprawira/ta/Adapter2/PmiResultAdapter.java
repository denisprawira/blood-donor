package com.example.denisprawira.ta.Adapter2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Api.Model.Pmi;

import java.util.List;

public class PmiResultAdapter extends RecyclerView.Adapter<PmiResultAdapter.ViewHolder> {
    private List<Pmi> pmiList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Pmi item, int position);
    }

    public PmiResultAdapter(List<Pmi> pmiList, Context context, OnItemClickListener listener) {
        this.pmiList = pmiList;
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

        holder.bind(pmiList.get(position),listener);
        holder.pmiName.setText(pmiList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return pmiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pmiName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pmiName = itemView.findViewById(R.id.pmiActivityResultName);
        }

        public void bind(Pmi item, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  = getAdapterPosition();
                    listener.onItemClick(item,position);
                }
            });
        }
    }

    public void updateData(List<Pmi> list){
        pmiList = list;
    }
}
