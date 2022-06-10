package com.example.denisprawira.ta.UI.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.denisprawira.ta.Adapter.RequestDonorDetailMainAdapter;
import com.example.denisprawira.ta.Api.Model.HelpRequest;
import com.example.denisprawira.ta.Api.Model.User;
import com.example.denisprawira.ta.Api.Model.User2;
import com.example.denisprawira.ta.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.R;

import java.util.Locale;

public class DetailDonorRequestActivity extends AppCompatActivity {

    RecyclerView recyclerViewDetailDonor;
    RequestDonorDetailMainAdapter requestDonorDetailMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donor_request);

        HelpRequest helpRequest = getIntent().getParcelableExtra("helpRequest");

    }
}
