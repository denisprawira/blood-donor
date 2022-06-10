package com.example.denisprawira.ta.UI.Fragment.Main;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;

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

import com.example.denisprawira.ta.Adapter.NotificationAdapter;
import com.example.denisprawira.ta.Helper.RecycerViewItemTouchHelper;
import com.example.denisprawira.ta.Helper.RecyclerItemTouchHelperListener;
import com.example.denisprawira.ta.Helper.SessionManager;
import com.example.denisprawira.ta.Model.Notif;
import com.example.denisprawira.ta.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainNotificationFragment extends Fragment implements RecyclerItemTouchHelperListener {


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

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerview);
        notificationAdapter    = new NotificationAdapter(getActivity(), notifList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notif item, int position) {

            }

        });
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getNotification();
        //notificationRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback item = new RecycerViewItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        ItemTouchHelper.SimpleCallback item2 = new RecycerViewItemTouchHelper(0,ItemTouchHelper.RIGHT,this);
        new ItemTouchHelper(item).attachToRecyclerView(notificationRecyclerView);
        new ItemTouchHelper(item2).attachToRecyclerView(notificationRecyclerView);

        return view;
    }



    public void getNotification(){
        notifList.clear();
        Query notifs = db.collection("Notifs");
         notifs.whereEqualTo("receiverStatus","user").orderBy("date",DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                 for(DocumentSnapshot snapshot: snapshotList){
                     Notif notif = snapshot.toObject(Notif.class);
                     notifList.add(notif);
                 }
                 notificationAdapter.notifyDataSetChanged();
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