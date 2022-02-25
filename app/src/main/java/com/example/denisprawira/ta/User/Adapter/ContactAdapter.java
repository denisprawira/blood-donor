package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Model.Chat.UserChat;
import com.example.denisprawira.ta.R;


import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {

    public interface  OnItemClickListener{
        void onItemClick(UserChat item, int position);
    }



    private List<UserChat> userList;
    private List<UserChat> userListFull;
    private Context context;
    private final OnItemClickListener listerner;

    public ContactAdapter(Context context, List<UserChat> userList,List<UserChat> userList2, OnItemClickListener listerner){
        this.userList = userList;
        this.context = context;
        this.listerner = listerner;
        userListFull = userList2;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(userList.get(position),listerner);
        ImageView contactImage  = holder.itemView.findViewById(R.id.userProfileImage);
        TextView textnama = holder.itemView.findViewById(R.id.userName);

        Glide.with(context).load(userList.get(position).getUserPhoto()).into(contactImage);
        textnama.setText(userList.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);

        }
        //begin of error
        public void bind(final UserChat item, final OnItemClickListener listener) {

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
            List<UserChat> filteredList = new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filteredList.addAll(userListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(UserChat item : userListFull){
                    if(item.getUserName().toLowerCase().contains(filterPattern)){
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
            userList.clear();
            userList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
