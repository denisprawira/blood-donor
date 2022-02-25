package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Model.Utd;
import com.example.denisprawira.ta.R;

import java.util.ArrayList;
import java.util.List;

public class UtdAdapter extends RecyclerView.Adapter<UtdAdapter.ViewHolder> implements Filterable {

    public interface  OnItemClickListener{
        void onItemClick(Utd item, int position);
    }

    private List<Utd> utdList;
    private List<Utd> utdListFull;
    private Context context;
    private final OnItemClickListener listerner;

    public UtdAdapter(Context context, List<Utd> utdList,List<Utd> utdListFull, OnItemClickListener listerner){
        this.utdList = utdList;
        this.context = context;
        this.utdListFull = utdListFull;
        this.listerner = listerner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_dialog_utd, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(utdList.get(position),listerner);
        TextView pmi = holder.itemView.findViewById(R.id.pmi);
        pmi.setText(utdList.get(position).getNama());

    }

    @Override
    public int getItemCount() {
        return utdList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);

        }
        //begin of error
        public void bind(final Utd item, final OnItemClickListener listener) {

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

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Utd> filteredList = new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filteredList.addAll(utdListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Utd item : utdListFull){
                    if(item.getNama().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            utdList.clear();
            utdList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
