package com.example.denisprawira.ta.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Model.Component;
import com.example.denisprawira.ta.R;

import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Component item, int position);
    }

    private List<Component> componentList;
    private Context context;
    private final OnItemClickListener listerner;

    public ComponentAdapter(Context context, List<Component> componentList, OnItemClickListener listerner){
        this.componentList = componentList;
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
        holder.bind(componentList.get(position),listerner);
        TextView component = holder.itemView.findViewById(R.id.component);
        component.setText(componentList.get(position).getComponent());

    }

    @Override
    public int getItemCount() {
        return componentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);

        }
        //begin of error
        public void bind(final Component item, final OnItemClickListener listener) {

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
