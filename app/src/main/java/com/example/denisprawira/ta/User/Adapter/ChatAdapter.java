package com.example.denisprawira.ta.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Model.Chat.Chat;
import com.example.denisprawira.ta.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{


    private ArrayList<Chat> chat = new ArrayList<>();
    private Context mContext;
    private String uri;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED=2;

    SessionManager sessionManager;

    public ChatAdapter(ArrayList<Chat> chat, String uri, Context context) {
        this.chat = chat;
        this.uri = uri;
        this.mContext = context;
        sessionManager = new SessionManager(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);

            return new SentMessageHolder(view);
        }else if(viewType==VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat chatList= (Chat) chat.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(chatList);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chatList);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(sessionManager.getChatId().equals(chat.get(position).getSender())){
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }


    //class SentMessageHolder
    public class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView chattext ;
        public SentMessageHolder(View itemView) {
            super(itemView);
            chattext = itemView.findViewById(R.id.ChatBubble2);

        }

        void bind(Chat message) {
            chattext.setText(message.message);
        }

    }

//    class ReceivedMessageHolder
    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView chattext ;
        ImageView chatImage;
        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            chattext = itemView.findViewById(R.id.ChatBubble);
            chatImage  = itemView.findViewById(R.id.ChatBubbleImage);
        }
        void bind(Chat message) {
            chattext.setText(message.message);
            Glide.with(mContext).load(uri).into(chatImage);


        }

    }



}
















