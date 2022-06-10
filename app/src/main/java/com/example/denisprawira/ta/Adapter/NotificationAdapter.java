package com.example.denisprawira.ta.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.Helper.RetrofitHelper;
import com.example.denisprawira.ta.Api.RetrofitServices;
import com.example.denisprawira.ta.Model.Notif;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.Event.EventDetailActivity;
import com.example.denisprawira.ta.UI.Event.EventRequestUnapprovedActivity;
import com.example.denisprawira.ta.Values.GlobalValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Notif item, int position);
    }

    private List<Notif> notifList;
    private Context context;
    private final OnItemClickListener listerner;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notifs = db.collection("Notifs");

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    Intent intent;

    public NotificationAdapter(Context context, List<Notif> notifList, OnItemClickListener listerner) {
        this.notifList = notifList;
        this.context = context;
        this.listerner = listerner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notification, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(notifList.get(position), listerner);
        long pastTime = Long.parseLong(notifList.get(position).getTimeMilis());
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        String message = "";
        long result;
        long diffTime = (currentTime - pastTime) / 1000;
        if (diffTime < 1) {
            message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + 1 + " detik</font>";
        } else if (diffTime < 60) {
            message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + diffTime + " detik</font>";
        } else if (diffTime >= 60 && diffTime < 3600) {
            result = diffTime / 60;
            message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " menit</font>";
        } else if (diffTime >= 3600 && diffTime < 86400) {
            result = diffTime / 3600;
            message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " jam</font>";
        } else if (diffTime >= 86400 && diffTime < 604800) {
            result = diffTime / 86400;
            message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " hari</font>";
        } else if (diffTime >= 604800) {
            result = diffTime / 604800;
            message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " minggu</font>";
        }
        holder.notificationTitle.setText(Html.fromHtml(message));
        //loadUserById(notifList.get(position), notifList.get(position).getSenderId(), notifList.get(position).getSenderStatus(), notifList.get(position).getMessage(), diffTime, position, holder.contactName, holder.notifImage);

        //show view if type help
        if (notifList.get(position).getType().equals("help")) {
            holder.viewLine.setVisibility(View.VISIBLE);
        } else {
            holder.viewLine.setVisibility(View.GONE);
        }

        //set img
        if(notifList.get(position).getType().equals(GlobalValues.NOTIFICATION_HELP_REQUEST)){
            int drawableIdentifier = context.getResources().getIdentifier("ic_find_donor", "drawable", context.getPackageName());
            Glide.with(context)
                    .load(drawableIdentifier)
                    .apply(new RequestOptions().override(60,60))
                    .into( holder.notifImage);
        }

        if (notifList.get(position).getStatus().equals("0")) {
            holder.bubbleView.setVisibility(View.VISIBLE);
        } else {
            holder.bubbleView.setVisibility(View.GONE);
        }
        holder.cardViewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifList.get(position).getType().equals("eventApproval")) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra("id", notifList.get(position).getTypeId());
                    context.startActivity(intent);
                }else if(notifList.get(position).getType().equals("eventReject")){
                    Intent intent = new Intent(context, EventRequestUnapprovedActivity.class);
                    intent.putExtra("id",notifList.get(position).getTypeId());
                    context.startActivity(intent);
                }

                notifs.document(notifList.get(position).getId()).update("status", "1").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                holder.bubbleView.setVisibility(View.GONE);

//                if (notifList.get(position).getType().equals(GlobalHelper.TYPE_EVENTAPPROVAL)) {
////                    intent = new Intent(context,EventDetailActivity.class);
////                    intent.putExtra("id",notifList.get(position).getIdType());
////                    context.startActivity(intent);
//                } else if (notifList.get(position).getType().equals("help")) {
//
//                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }


    public void removeItem(int position) {
        notifList.remove(position);
        notifyItemRemoved(position);
    }

//    public void restoreItem(Notification notification, int position){
//
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView notificationTitle;
        CardView cardViewContact;
        View bubbleView, viewLine;
        ImageView notifImage;

        public ViewHolder(View v) {
            super(v);
            notificationTitle = v.findViewById(R.id.notificationTitle);
            cardViewContact = v.findViewById(R.id.cardViewContact);
            bubbleView = v.findViewById(R.id.bubbleView);
            notifImage = v.findViewById(R.id.notifImage);
            viewLine = v.findViewById(R.id.viewLine);
        }

        //begin of error
        public void bind(final Notif item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(item, pos);
                }
            });
        }
        //end of error
    }

//    public void loadUserById(final Notif notif, String idSender, String senderStatus, String msg, final long diffTime, final int position, final TextView contactName, final ImageView notifImage) {
//        retrofitServices.loadUserById(idSender, senderStatus).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//                notif.setSenderName(user.getNama());
////                notif.getImg(user.getPhoto());
//                String message = "";
//                long result;
//                if (diffTime < 1) {
//                    message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + 1 + " detik</font>";
//                } else if (diffTime < 60) {
//                    message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + diffTime + " detik</font>";
//                } else if (diffTime >= 60 && diffTime < 3600) {
//                    result = diffTime / 60;
//                    message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " menit</font>";
//                } else if (diffTime >= 3600 && diffTime < 86400) {
//                    result = diffTime / 3600;
//                    message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " jam</font>";
//                } else if (diffTime >= 86400 && diffTime < 604800) {
//                    result = diffTime / 86400;
//                    message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " hari</font>";
//                } else if (diffTime >= 604800) {
//                    result = diffTime / 604800;
//                    message = "<b>" + notifList.get(position).getSenderName() + " </b>" + notifList.get(position).getMessage() + ". <font color='#808080'>" + result + " minggu</font>";
//                }
//
//                contactName.setText(Html.fromHtml(message));
//
//                if (notifList.get(position).getImg() != null) {
//                    Glide.with(context).load(notifList.get(position).getImg()).into(notifImage);
//
//                } else {
//                    notifImage.setImageResource(R.drawable.empty_person);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//    }
}

