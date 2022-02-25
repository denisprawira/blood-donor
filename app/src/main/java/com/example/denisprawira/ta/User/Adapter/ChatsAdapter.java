package com.example.denisprawira.ta.User.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Model.Chat.LastMessage;
import com.example.denisprawira.ta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    public interface  OnItemClickListener{
        void onItemClick(LastMessage item, int position);
    }
    private final OnItemClickListener listener;

    private List<LastMessage> chatList;
    private Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notifs = db.collection("Notifs");

    SessionManager sessionManager;

    String name;
    String photo;
    public ChatsAdapter(List<LastMessage> chatList, Context context, OnItemClickListener listener){
        this.chatList = chatList;
        this.context  = context;
        this.listener = listener;
        sessionManager = new SessionManager(context);
    }


    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card_chat_list, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ChatsAdapter.ViewHolder vh = new ChatsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatsAdapter.ViewHolder holder, final int position) {
        holder.bind(chatList.get(position),listener);
//        if(chatList.get(position).getUserName()==null&&chatList.get(position).getUserPhoto()==null){
//            Toast.makeText(context, "masuk gan", Toast.LENGTH_SHORT).show();
//            if(userId.equals(chatList.get(position).getReceiver())){
//                loadUser(holder.userName,holder.userPhoto,chatList.get(position).getSender(),chatList.get(position),position);
//            }else if(userId.equals(chatList.get(position).getSender())){
//                loadUser(holder.userName,holder.userPhoto,chatList.get(position).getReceiver(),chatList.get(position),position);
//            }
//        }else{
//            Toast.makeText(context, "sudah isi chat", Toast.LENGTH_SHORT).show();
            if(chatList.get(position).getStatus().equals("0")){
                holder.readIndicator.setVisibility(View.VISIBLE);
            }else{
                holder.readIndicator.setVisibility(View.GONE);
            }
            if(chatList.get(position).getUserName().length()>17){
                String s = chatList.get(position).getUserName().substring(0,14);
                holder.userName.setText(s+"...");
            }else{
                holder.userName.setText(chatList.get(position).getUserName());
            }
            Glide.with(context).load(chatList.get(position).getUserPhoto()).into(holder.userPhoto);

//        }



        if(chatList.get(position).getMessage().length()>65){
            String s = chatList.get(position).getMessage().substring(0,62);
            holder.userLastMessage.setText(s+"...");
        }else{
            holder.userLastMessage.setText(chatList.get(position).getMessage());
        }



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String[] option = {"Hapus Chat"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,option);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0){
                            if(chatList.get(position).getReceiver().equals(sessionManager.getChatId())){
                                deleteLastMessage(chatList.get(position).getSender());
                            }else{
                                deleteLastMessage(chatList.get(position).getReceiver());
                            }
                            if(sessionManager.getChatId().equals(chatList.get(position).getReceiver())){
                                deleteChat(chatList.get(position).getSender());
                            }else if(sessionManager.getChatId().equals(chatList.get(position).getSender())){
                                deleteChat(chatList.get(position).getReceiver());
                            }

                        }
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPhoto;
        View readIndicator;
        TextView userName, userLastMessage;
        CardView chatListCardView;
        public ViewHolder(View v) {
            super(v);
            userPhoto = v.findViewById(R.id.userProfileImage);
            userName  = v.findViewById(R.id.userName);
            userLastMessage  = v.findViewById(R.id.donorDistance);
            readIndicator = v.findViewById(R.id.readIndicator);
            chatListCardView = v.findViewById(R.id.chatListCardView);

        }
        //begin of error
        public void bind(final LastMessage item, final OnItemClickListener listener) {

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



//    public void  loadUser(final TextView userName, final ImageView userPhoto, String UserId, final LastMessage lastMessage, final int position){
//        CollectionReference users = db.collection("Users");
//        users.document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                userName.setText(documentSnapshot.get("userName").toString());
//                Glide.with(context).load(documentSnapshot.get("userPhoto").toString()).into(userPhoto);
//                name = documentSnapshot.get("userName").toString();
//                photo= documentSnapshot.get("userPhoto").toString();
//                lastMessage.setUserName(name);
//                lastMessage.setUserPhoto(photo);
//                chatList.set(position,lastMessage);
//            }
//        });
//    }



    public void deleteLastMessage(String chatPartner){
        CollectionReference chat = db.collection("LastMessage/lastmessage/"+sessionManager.getChatId());
        chat.document(chatPartner).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void deleteChat(String chatPartner){
        final CollectionReference chat = db.collection("Chats/"+sessionManager.getChatId()+"/"+chatPartner);
        chat.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        chat.document(documentSnapshot.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
