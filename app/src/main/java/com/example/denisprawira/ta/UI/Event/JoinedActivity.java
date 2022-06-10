package com.example.denisprawira.ta.UI.Event;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Adapter2.ViewPagerAdapter.JoinedviewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class JoinedActivity extends AppCompatActivity {

    RecyclerView joinedEventDonorRecyclerView;

    TabLayout tabLayoutJoined;
    ViewPager2 viewPagerJoined;
    ImageButton backJoinedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_event_donor);
        backJoinedEvent = findViewById(R.id.backJoinedEvent);
        tabLayoutJoined = findViewById(R.id.tabLayoutJoinedEvent);
        viewPagerJoined = findViewById(R.id.viewPagerJoined);
        viewPagerJoined.setAdapter(new JoinedviewPagerAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayoutJoined, viewPagerJoined, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:{
                        tab.setText("Acara");
                        tab.setIcon(R.drawable.ic_event_icon);
                        break;
                    }
                    case 1:{
                        tab.setText("Donor Pengganti");
                        tab.setIcon(R.drawable.ic_find_donor);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();
        backJoinedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        joinedEventDonorRecyclerView = findViewById(R.id.joinedEventDonorRecyclerView);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        joinedAdapter = new JoinedAdapter(fragmentManager,JoinedEventDonorActivity.this);
//        joinedEventDonorRecyclerView.setLayoutManager(new LinearLayoutManager(JoinedEventDonorActivity.this));
//        joinedEventDonorRecyclerView.setAdapter(joinedAdapter);
    }

}
