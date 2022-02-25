package com.example.denisprawira.ta.User.UI;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.ContactAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Chat.UserChat;
import com.example.denisprawira.ta.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class SearchUserActivity extends AppCompatActivity {


    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    RecyclerView recyclerViewUser;
    ArrayList<UserChat> userList = new ArrayList<>();
    ArrayList<UserChat> userListFull = new ArrayList<>();
    ContactAdapter contactAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SessionManager sessionManager;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        sessionManager = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        recyclerViewUser = findViewById(R.id.recyclerViewUser);
        initRecyclerView();
        getContact(getIntent().getExtras().get("userStatus").toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void getContact(final String statusUser){
        CollectionReference notifs = db.collection("Users");
        notifs.whereEqualTo("userStatus", statusUser).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                    userList.clear();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        UserChat userChat = documentSnapshot.toObject(UserChat.class);
                        if(!statusUser.equals("utd")){
                            if(!userChat.getUserId().equals(sessionManager.getId())){
                                userList.add(userChat);
                                userListFull.add(userChat);
                            }
                        }else{
                            userList.add(userChat);
                            userListFull.add(userChat);
                        }


                    }
                    contactAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void initRecyclerView(){
        contactAdapter = new ContactAdapter(this, userList, userListFull, new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserChat item, int position) {
                Intent intent =  new Intent(SearchUserActivity.this, ChatRoomActivity.class);
                intent.putExtra("currentUserIdChat",sessionManager.getChatId());
                intent.putExtra("UserId",item.getUserChatId());
                intent.putExtra("UserName",item.getUserName());
                intent.putExtra("UserPhoto",item.getUserPhoto());
                intent.putExtra("UserToken",item.getUserToken());
                startActivity(intent);
            }
        });
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
        recyclerViewUser.setAdapter(contactAdapter);

    }
}
