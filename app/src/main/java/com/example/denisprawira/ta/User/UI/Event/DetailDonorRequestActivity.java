package com.example.denisprawira.ta.User.UI.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.denisprawira.ta.User.Adapter.RequestDonorDetailMainAdapter;
import com.example.denisprawira.ta.User.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.R;

public class DetailDonorRequestActivity extends AppCompatActivity {

    RecyclerView recyclerViewDetailDonor;
    RequestDonorDetailMainAdapter requestDonorDetailMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donor_request);
        recyclerViewDetailDonor  = findViewById(R.id.recyclerViewDetailDonor);
        RequestDonor requestDonor  = new RequestDonor(
                getIntent().getExtras().get("idDonor").toString(),
                getIntent().getExtras().get("idUtd").toString(),
                getIntent().getExtras().get("golDarah").toString(),
                getIntent().getExtras().get("utdName").toString(),
                getIntent().getExtras().get("utdEmail").toString(),
                getIntent().getExtras().get("utdAddress").toString(),
                getIntent().getExtras().get("utdTelp").toString(),
                getIntent().getExtras().get("lat").toString(),
                getIntent().getExtras().get("lng").toString(),
                getIntent().getExtras().get("utdPhoto").toString(),
                getIntent().getExtras().get("utdToken").toString(),
                getIntent().getExtras().get("status").toString(),
                getIntent().getExtras().get("tanggal").toString()
        );

        FragmentManager fragmentManager = getSupportFragmentManager();
        requestDonorDetailMainAdapter = new RequestDonorDetailMainAdapter(requestDonor,fragmentManager,DetailDonorRequestActivity.this);
        recyclerViewDetailDonor.setLayoutManager(new LinearLayoutManager(DetailDonorRequestActivity.this));
        recyclerViewDetailDonor.setAdapter(requestDonorDetailMainAdapter);

    }
}
