package com.example.denisprawira.ta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Model.GolDarah;
import com.example.denisprawira.ta.R;

import java.util.List;

public class GolDarahAdapter extends RecyclerView.Adapter<GolDarahAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(GolDarah item, int position);
    }

    private List<GolDarah> golDarahList;
    private Context context;
    private final OnItemClickListener listerner;

    public GolDarahAdapter(Context context, List<GolDarah> golDarahList, OnItemClickListener listerner){
        this.golDarahList = golDarahList;
        this.context = context;
        this.listerner = listerner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_dialog_component, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(golDarahList.get(position),listerner);
        TextView component = holder.itemView.findViewById(R.id.component);
        component.setText(golDarahList.get(position).getGolDarah());

    }

    @Override
    public int getItemCount() {
        return golDarahList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);

        }
        //begin of error
        public void bind(final GolDarah item, final OnItemClickListener listener) {

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
