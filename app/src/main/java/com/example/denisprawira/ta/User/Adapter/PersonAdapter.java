package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Model.Person;
import com.example.denisprawira.ta.R;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(Person item, int position);
    }

    private List<Person> personList;
    private Context context;
    private final OnItemClickListener listerner;

    public PersonAdapter(Context context, List<Person> persongList, OnItemClickListener listerner){
        this.personList = persongList;
        this.context = context;
        this.listerner = listerner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_person, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(personList.get(position),listerner);
        holder.personName.setText(personList.get(position).getNama());
        holder.personTelp.setText(personList.get(position).getTelp());



    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView personName,personTelp;
        public ViewHolder(View v) {
            super(v);
            personName = v.findViewById(R.id.personName);
            personTelp  = v.findViewById(R.id.personTelp);

        }
        //begin of error
        public void bind(final Person item, final OnItemClickListener listener) {

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
