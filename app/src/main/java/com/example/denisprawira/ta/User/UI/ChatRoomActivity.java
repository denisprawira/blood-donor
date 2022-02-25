package com.example.denisprawira.ta.User.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Adapter.ChatAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Api.RetrofitClient;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Interface.IFCMServices;
import com.example.denisprawira.ta.User.Model.Chat.Chat;
import com.example.denisprawira.ta.User.Model.Chat.LastMessage;
import com.example.denisprawira.ta.User.Model.Data2;
import com.example.denisprawira.ta.User.Model.FCMResponse;
import com.example.denisprawira.ta.User.Model.Sender;
import com.example.denisprawira.ta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView displayPicture;
    TextView chatName,chatMessage;

    ImageButton sendButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView chatRecycler;
    private ArrayList<Chat> chatList = new ArrayList<>() ;
    private ChatAdapter chatAdapter;
    private ListenerRegistration ChatMessageEventListener;

    IFCMServices ifcmServices = RetrofitClient.getClient("https://fcm.googleapis.com/").create(IFCMServices.class);
    SessionManager sessionManager;

    String msg;
    String currentUserIdChat,UserId,UserName, UserPhoto,UserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        sessionManager = new SessionManager(ChatRoomActivity.this);
        displayPicture = findViewById(R.id.chatImage);
        chatName = findViewById(R.id.chatName);

        chatRecycler = findViewById(R.id.chatRecyclerView);
        chatRecycler.setHasFixedSize(true);
        chatMessage = findViewById(R.id.chatMessage);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        currentUserIdChat = sessionManager.getChatId();


        UserId            = getIntent().getExtras().get("UserId").toString();
        UserName          = getIntent().getExtras().get("UserName").toString();
        UserToken         = getIntent().getExtras().get("UserToken").toString();


        if(getIntent().getExtras().get("UserPhoto")!=null){
             UserPhoto         = getIntent().getExtras().get("UserPhoto").toString();
            Glide.with(ChatRoomActivity.this).load(UserPhoto).into(displayPicture);
        }
        chatName.setText(UserName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChatRoom);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatRoomActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //createChat();
        iniChatRecyclerView();
        getChat();
    }

    public void getChat(){
        CollectionReference chat = db.collection("Chats/"+sessionManager.getChatId()+"/"+UserId);
        ChatMessageEventListener = chat.orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if(queryDocumentSnapshots != null){
                            chatList.clear();
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Chat chat = doc.toObject(Chat.class);
                                if (chat.getSender().equals(currentUserIdChat) && chat.getReceiver().equals(UserId) || chat.getReceiver().equals(currentUserIdChat) && chat.getSender().equals(UserId)) {
                                    chatList.add(chat);
                                    chatRecycler.smoothScrollToPosition(chatList.size() - 1);
                                }
                            }
                        }
                        chatAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void iniChatRecyclerView(){
        chatAdapter = new ChatAdapter(chatList, UserPhoto,this);
        chatRecycler.setAdapter(chatAdapter);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this));


        chatRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    chatRecycler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(chatList.size() > 0){
                                chatRecycler.smoothScrollToPosition(
                                        chatRecycler.getAdapter().getItemCount() - 1);
                            }

                        }
                    }, 100);
                }
            }
        });

    }

    public void addMessage(){
        final CollectionReference chat = db.collection("Chats/"+sessionManager.getChatId()+"/"+UserId);
        msg = chatMessage.getText().toString();
        final Chat newChat = new Chat();
        newChat.setSender(currentUserIdChat);
        newChat.setReceiver(UserId);
        newChat.setMessage(msg);
        newChat.setStatus(0);
        chat.add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                addMessageToPartner(newChat);
                addLastMessageCurrentUser(msg);
                addLastMessageChatPartner(msg);
                String key = documentReference.getId();
                chat.document(key).update("id",key).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });

    }

    public void addMessageToPartner(Chat newChat){
        final CollectionReference chat = db.collection("Chats/"+UserId+"/"+sessionManager.getChatId());
        chat.add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                sendMessage(UserToken);
                String key  = documentReference.getId();
                chat.document(key).update("id",key).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });
//        chat.add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                sendMessage(UserToken);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ChatRoomActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    public void addLastMessageCurrentUser(String msg){
        CollectionReference chat = db.collection("LastMessage/lastmessage/"+currentUserIdChat);
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        final LastMessage lastMessage = new LastMessage();
        lastMessage.setMessage(msg);
        lastMessage.setSender(currentUserIdChat);
        lastMessage.setReceiver(UserId);
        lastMessage.setTimeMilis(String.valueOf(currentTime));
        lastMessage.setStatus("1");
        chat.document(UserId).set(lastMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void addLastMessageChatPartner(String msg){
        CollectionReference chat = db.collection("LastMessage/lastmessage/"+UserId);
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        LastMessage lastMessage = new LastMessage();
        lastMessage.setMessage(msg);
        lastMessage.setSender(UserId);
        lastMessage.setReceiver(currentUserIdChat);
        lastMessage.setTimeMilis(String.valueOf(currentTime));
        lastMessage.setStatus("0");
        chat.document(sessionManager.getChatId()).set(lastMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }


    public void sendMessage(String token){
        Toast.makeText(this, "userToken"+token, Toast.LENGTH_SHORT).show();

        String type, chatId, message, senderName,senderPhoto,tkn;
        type        = GlobalHelper.TYPE_CHAT;
        chatId      = sessionManager.getChatId();
        message     = msg;
        senderName  = sessionManager.getName();
        senderPhoto = sessionManager.getPhoto();
        tkn         = sessionManager.getToken();

        Data2 data = new Data2(type,message,senderName,senderPhoto, chatId,tkn);
        Sender content = new Sender(token,data);
        ifcmServices.sendMessage(content).enqueue(new Callback<FCMResponse>() {
            @Override
            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                Toast.makeText(ChatRoomActivity.this, "berhasil berhasil berhasil ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FCMResponse> call, Throwable t) {
                Toast.makeText(ChatRoomActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateStatusReadChat(String idUser){
        CollectionReference chat = db.collection("LastMessage/lastmessage/"+idUser);
        chat.document(UserId).update("status","1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v==sendButton){
            addMessage();
            chatMessage.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChatRoomActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}