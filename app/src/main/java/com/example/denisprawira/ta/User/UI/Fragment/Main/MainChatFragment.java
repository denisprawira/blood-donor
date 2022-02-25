package com.example.denisprawira.ta.User.UI.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.ChatsAdapter;
import com.example.denisprawira.ta.User.Adapter.ContactAdapter;
import com.example.denisprawira.ta.User.Helper.DividerItemDecoration;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Model.Chat.LastMessage;
import com.example.denisprawira.ta.User.Model.User;
import com.example.denisprawira.ta.User.Model.Chat.UserChat;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.ChatRoomActivity;
import com.example.denisprawira.ta.User.UI.SearchUserActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class MainChatFragment extends Fragment implements View.OnClickListener {

    //VIEW
    LinearLayout containerEmptyChat;
    FloatingActionButton addChat;
    RecyclerView contactRecycler, chatsRecyclerView;
    AlertDialog dialog;
    ImageView chatProfileCurrentUser;

    SessionManager sessionManager;

    //ADAPTER
    ContactAdapter contactAdapter;
    ChatsAdapter chatsAdapter;

    //FIREBASE RELATED
    ListenerRegistration chatListListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //LIST
    List<LastMessage> chatList= new ArrayList<>();
    List<UserChat> userList   = new ArrayList<>();
    List<User> chatsList = new ArrayList<>();

    //PRIMITIVE TYPE DATA


    public MainChatFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main_chat, container, false);
        sessionManager = new SessionManager(getActivity());

        SpeedDialView speedDialView = view.findViewById(R.id.speedDial);
        chatsRecyclerView = view.findViewById(R.id.chatsList);

        contactRecycler = new RecyclerView(getActivity());
        contactRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        contactRecycler.setHasFixedSize(true);
        chatsRecyclerView.setHasFixedSize(true);

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.speedDial, R.drawable.ic_add_white)
                        .setLabel(getString(R.string.app_name))
                        .setTheme(R.style.AppTheme_Dial)
                        .create());

        speedDialView.inflate(R.menu.menu_chat);


        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.addChatUser:
                        Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                        intent.putExtra("userStatus","user");
                        startActivity(intent);
                    return false; // true to keep the Speed Dial open
                    case R.id.addChatPmi:
                        Intent intent1 = new Intent(getActivity(),SearchUserActivity.class);
                        intent1.putExtra("userStatus","utd");
                        startActivity(intent1);
                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });
        initChatsRecyclerview();
        loadLastMessage();

        return view;
    }


    @Override
    public void onClick(View v) {

    }

    public void initChatsRecyclerview(){
        chatsAdapter = new ChatsAdapter(chatList, getActivity(), new ChatsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LastMessage item, int position) {

                Intent intent = new Intent(getActivity(),ChatRoomActivity.class);
                intent.putExtra("currentUserIdChat",sessionManager.getChatId());
                if(item.getSender().equals(sessionManager.getChatId())){
                    intent.putExtra("UserId",item.getReceiver());
                }else{
                    intent.putExtra("UserId",item.getSender());
                }
                intent.putExtra("UserName",item.getUserName());
                intent.putExtra("UserPhoto",item.getUserPhoto());
                intent.putExtra("UserToken",item.getToken());
                startActivity(intent);
            }
        });
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration deviderItemdecoration = new DividerItemDecoration(getActivity());
        chatsRecyclerView.addItemDecoration(deviderItemdecoration);
        chatsRecyclerView.setAdapter(chatsAdapter);
    }


//    private void checkChat(){
//        if(chatList.size()==0){
//            containerEmptyChat.setVisibility(View.VISIBLE);
//        }else{
//            containerEmptyChat.setVisibility(View.GONE);
//        }
//    }



//    public void getContact(String statusUser){
//        CollectionReference notifs = db.collection("Users");
//        notifs.whereEqualTo("userStatus", statusUser).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if(queryDocumentSnapshots != null){
//                    userList.clear();
//                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
//                        UserChat userChat = documentSnapshot.toObject(UserChat.class);
//                        if(!userChat.getUserId().equals(sessionManager.getId())){
//                            userList.add(userChat);
//                        }
//
//                    }
//                    contactAdapter  = new ContactAdapter(getActivity(), userList, new ContactAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(UserChat item, int position) {
//
//                            Intent intent =  new Intent(getActivity(), ChatRoomActivity.class);
//                            intent.putExtra("currentUserIdChat",sessionManager.getChatId());
//                            intent.putExtra("UserId",item.getUserChatId());
//                            intent.putExtra("UserName",item.getUserName());
//                            intent.putExtra("UserPhoto",item.getUserPhoto());
//                            intent.putExtra("UserToken",item.getUserToken());
//                            startActivity(intent);
//                        }
//                    });
//                    contactRecycler.setAdapter(contactAdapter);
//                }
//            }
//        });
//    }
    public void loadLastMessage(){
        CollectionReference chat = db.collection("LastMessage/lastmessage/"+sessionManager.getChatId());
        chatListListener= chat.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                chatList.clear();
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    LastMessage lastMessage = doc.toObject(LastMessage.class);
                    if(lastMessage.getSender().equals(sessionManager.getChatId())){
                        loadUserChatList(lastMessage.getReceiver(),lastMessage);
                    }else{
                        loadUserChatList(lastMessage.getSender(),lastMessage);
                    }
                }


            }
        });
    }

    public void loadUserChatList(String idUser, final LastMessage lastMessage){
        CollectionReference users = db.collection("Users");
        users.whereEqualTo("userChatId",idUser).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentSnapshot docUser: queryDocumentSnapshots){
                    lastMessage.setUserName(docUser.get("userName").toString());
                    lastMessage.setUserPhoto(docUser.get("userPhoto").toString());
                    lastMessage.setToken(docUser.get("userToken").toString());
                    chatList.add(lastMessage);
//                    checkChat();
                }

                chatsAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void  loadUser(String UserId){
        CollectionReference users = db.collection("Users");
        users.document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(getActivity(), documentSnapshot.get("userName").toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), documentSnapshot.get("userPhoto").toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // end of class

    //    public void addUserDataFirebase(UserChat chatUser){
//        final CollectionReference chat = db.collection("Users");
//        chat.add(chatUser).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()) {
//                    DocumentReference docRef = task.getResult();
//                    String key = docRef.getId();
//                    chat.document(key).update("userChatId",key).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });
//                }
//            }
//        });
//    }

    //    public void loadUser(){
//        CollectionReference users = db.collection("Users");
//        users.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                userList.clear();
//                for (DocumentSnapshot doc : queryDocumentSnapshots) {
//                    User user = doc.toObject(User.class);
//                    if(!user.getUserId().equals(userId) && user.get){
//                        userList.add(user);
//                    }
//                }
//                personAdapter.notifyDataSetChanged();
//            }
//        });
//
//        personAdapter = new PersonAdapter(MainActivity.this, userList, new PersonAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(User item, int position) {
//                Intent intent =  new Intent(MainActivity.this,ChatRoomActivity.class);
//                intent.putExtra("userId",userId);
//                intent.putExtra("UserId",item.getUserId());
//                intent.putExtra("UserName",item.getUserName());
//                intent.putExtra("UserPhoto",item.getUserPhoto());
//                startActivity(intent);
//            }
//        });
//        contactRecycler.setAdapter(personAdapter);
//    }

//    public void addUserData(){
//        contact.addUserData().enqueue(new Callback<List<UserChat>>() {
//            @Override
//            public void onResponse(Call<List<UserChat>> call, Response<List<UserChat>> response) {
//                List<UserChat> userChat = response.body();
//                for(int i=0; i<userChat.size();i++){
//                    UserChat u = new UserChat();
//                    u.setUserId(userChat.get(i).getUserId());
//                    u.setUserName(userChat.get(i).getUserName());
//                    u.setUserPhoto(userChat.get(i).getUserPhoto());
//                    u.setUserToken(userChat.get(i).getUserToken());
//                    addUserDataFirebase(u);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<UserChat>> call, Throwable t) {
//
//            }
//        });
//    }
}