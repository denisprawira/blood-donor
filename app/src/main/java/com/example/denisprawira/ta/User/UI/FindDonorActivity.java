package com.example.denisprawira.ta.User.UI;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.DonorResponseAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.User.Model.User;
import com.example.denisprawira.ta.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindDonorActivity extends AppCompatActivity implements View.OnClickListener {

    /////////////////////Primitive type data////////
    int globalTimer = 0;
    boolean timeOut = false;
    String searchIdDonor,searchUtdName,searchUtdToken,searchGolDarah,searchRadius,searchLatitude,searchLongitude;


    final Handler handler = new Handler();

    ArrayList<User> donorList = new ArrayList<>();

    RecyclerView donorRecyclerView;
    DonorResponseAdapter donorResponseAdapter;
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    SessionManager sessionManager;

    Button findDonorAction,findDonorSave;
    TextView donorTitle,findDonorLocation,findDonorBlood;
    LinearLayout linearLayoutDonor;
    AVLoadingIndicatorView avi;
    Dialog dialogSaveDonor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(FindDonorActivity.this);
        setContentView(R.layout.activity_find_donor);

        donorTitle          = findViewById(R.id.donorTitle);
        avi                 = findViewById(R.id.avi);
        findDonorAction     = findViewById(R.id.findDonorAction);
        linearLayoutDonor   = findViewById(R.id.linearLayoutDonor);
        findDonorLocation   = findViewById(R.id.findDonorLocation);
        findDonorBlood      = findViewById(R.id.findDonorBlood);
        donorRecyclerView   = findViewById(R.id.RecyclerViewDonor);


        searchIdDonor       = getIntent().getExtras().getString("searchIdDonor");
        searchUtdName       = getIntent().getExtras().getString("searchUtdName");
        searchUtdToken      = getIntent().getExtras().getString("searchPmiToken");
        searchGolDarah      = getIntent().getExtras().getString("searchGolDarah");
        searchRadius        = getIntent().getExtras().getString("searchRadius");
        searchLatitude      = getIntent().getExtras().getString("searchLatitude");
        searchLongitude     = getIntent().getExtras().getString("searchLongitude");


        findDonorAction.setOnClickListener(this);
        avi.show();

        findDonorLocation.setText(searchUtdName);

        if(searchGolDarah.equals("1")){
            findDonorBlood.setText("AB+");
        }else if(searchGolDarah.equals("2")){
            findDonorBlood.setText("AB-");
        }else if(searchGolDarah.equals("3")){
            findDonorBlood.setText("A+");
        }else if(searchGolDarah.equals("4")){
            findDonorBlood.setText("A-");
        }else if(searchGolDarah.equals("5")){
            findDonorBlood.setText("B+");
        }else if(searchGolDarah.equals("6")){
            findDonorBlood.setText("B-");
        }else if(searchGolDarah.equals("7")){
            findDonorBlood.setText("O+");
        }else if(searchGolDarah.equals("8")){
            findDonorBlood.setText("O-");
        }




        donorRecyclerView.setLayoutManager(new LinearLayoutManager(FindDonorActivity.this));
        donorResponseAdapter = new DonorResponseAdapter(FindDonorActivity.this, donorList, new DonorResponseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item, int position) {

            }
        });
        donorRecyclerView.setAdapter(donorResponseAdapter);
        startRepeating();
    }

    public void startRepeating(){
        runable.run();
    }

    public void stopRepeating(){
        handler.removeCallbacks(runable);
    }

    private Runnable runable = new Runnable() {
        @Override
        public void run() {
            globalTimer++;
            loadDonorResponse(searchIdDonor);

            handler.postDelayed(runable,1000);
            if(globalTimer==30){
                timeOut = true;
                if(donorList.size()==0){
                    showDialogSaveDonor();
                }
                findDonorAction.setText("Simpan ke Draft");
                stopRepeating();
            }


        }
    };

    public void loadDonorResponse(String idDonor){
        retrofitServices.loadDonorResponse(idDonor).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                donorList.clear();
                List<User> res = response.body();
                donorList.addAll(res);
                donorResponseAdapter.notifyDataSetChanged();
                if(donorList.size()>0){
                    donorTitle.setVisibility(View.VISIBLE);
                    donorTitle.setText("Bersedia menjadi Pendonor ("+donorList.size()+")");
                    avi.hide();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(FindDonorActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialogSaveDonor(){
        dialogSaveDonor = new Dialog(this);
        dialogSaveDonor.setContentView(R.layout.layout_dialog_donor_no_response);
        dialogSaveDonor.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSaveDonor.show();

        findDonorSave  = dialogSaveDonor.findViewById(R.id.findDonorSave);
        findDonorSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSaveDonor.dismiss();
                Intent intent = new Intent(FindDonorActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void deleteRequestDonor(String searchIdDonor, int searchRadius){
        retrofitServices.deleteRequestDonor(sessionManager.getId(),searchGolDarah,searchIdDonor,String.valueOf(searchRadius),searchLatitude,searchLongitude).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(FindDonorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(FindDonorActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runable);

    }

    @Override
    public void onClick(View v) {
        if(v==findDonorSave){
            dialogSaveDonor.dismiss();
        }else if(v==findDonorAction){
            if(!timeOut){
                deleteRequestDonor(searchIdDonor,Integer.parseInt(searchRadius)+5);
                onBackPressed();
            }else if(timeOut&&donorList.size()>0){
                onBackPressed();
            }
        }
    }
}
