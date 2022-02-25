package com.example.denisprawira.ta.User.UI.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.NotificationAdapter;
import com.example.denisprawira.ta.User.Helper.RecycerViewItemTouchHelper;
import com.example.denisprawira.ta.User.Helper.RecyclerItemTouchHelperListener;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Model.Notif;
import com.example.denisprawira.ta.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class MainNotificationFragment extends Fragment implements View.OnClickListener,RecyclerItemTouchHelperListener {


    public MainNotificationFragment() {

    }

    FloatingActionButton notificationFAB;

    RecyclerView notificationRecyclerView;
    NotificationAdapter notificationAdapter;
    ListenerRegistration notificationListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration NotificationListener;
    ArrayList<Notif> notifList = new ArrayList<>();
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main_notification, container, false);
        sessionManager = new SessionManager(getActivity());
        notificationFAB = view.findViewById(R.id.notificationFAB);
        notificationFAB.setOnClickListener(this);
        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerview);
        notificationAdapter    = new NotificationAdapter(getActivity(), notifList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notif item, int position) {

            }

        });
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        notificationRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback item = new RecycerViewItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        ItemTouchHelper.SimpleCallback item2 = new RecycerViewItemTouchHelper(0,ItemTouchHelper.RIGHT,this);
        new ItemTouchHelper(item).attachToRecyclerView(notificationRecyclerView);
        new ItemTouchHelper(item2).attachToRecyclerView(notificationRecyclerView);
        getNotification();
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v==notificationFAB){
            //addUserData();
            getNotification();

        }
    }

    public void getNotification(){
        Query notifs = db.collection("Notifs");
         notifs.whereEqualTo("idReceiver",sessionManager.getId()).whereEqualTo("receiverStatus","user").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                    notifList.clear();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        Notif notif = documentSnapshot.toObject(Notif.class);

                        //loadUserById(notif.getIdSender(),notif.getSenderStatus(),notif);
                        notifList.add(notif);
                    }
                    notificationAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        CollectionReference notifs = db.collection("Notifs");
        if(viewHolder instanceof  NotificationAdapter.ViewHolder){
            int deleteIndex = viewHolder.getAdapterPosition();
            String id = notifList.get(viewHolder.getAdapterPosition()).getId();
            Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
            notifs.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });
            notificationAdapter.removeItem(deleteIndex);
        }
    }


}