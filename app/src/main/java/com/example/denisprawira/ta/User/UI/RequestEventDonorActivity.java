package com.example.denisprawira.ta.User.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.example.denisprawira.ta.User.Adapter.RequestEventDonorMainAdapter;
import com.example.denisprawira.ta.R;

public class RequestEventDonorActivity extends AppCompatActivity {

    RecyclerView requestEventDonorRecyclerView;

    ViewPager mViewPager;
    RequestEventDonorMainAdapter requestEventDonorMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_event);


        requestEventDonorRecyclerView  = findViewById(R.id.requestEventDonorRecyclerView);
        FragmentManager fragmentManager= getSupportFragmentManager();
        requestEventDonorMainAdapter = new RequestEventDonorMainAdapter(fragmentManager,RequestEventDonorActivity.this);
        requestEventDonorRecyclerView.setLayoutManager(new LinearLayoutManager(RequestEventDonorActivity.this));
        requestEventDonorRecyclerView.setAdapter(requestEventDonorMainAdapter);

    }






}
