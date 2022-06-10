package com.example.denisprawira.ta.UI.ActivityResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Adapter2.PmiResultAdapter;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Values.GlobalValues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PmiActivityResult extends AppCompatActivity {

    TextView shitText;
    Button shitButton;
    SearchView pmiResultSearchView;

    UserService userService = ApiUtils.getUserService();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PmiResultAdapter pmiResultAdapter;
    ArrayList<Pmi> pmiArrayList = new ArrayList<>();
    ArrayList<Pmi> searchResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmi_result);
        recyclerView = findViewById(R.id.recyclerPmiResult);
        recyclerView.setHasFixedSize(true);
        initPmiResultRecyclerView();
        Toast.makeText(this, "this is pmi result", Toast.LENGTH_SHORT).show();
        pmiResultSearchView = findViewById(R.id.pmiResultSearchView);
        pmiResultSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                for(Pmi element : pmiArrayList){
                    if(element.getName().contains(s)&&!s.equals("")){
                        Log.e("pmi",element.getName());
                    }
                }

                return true;
            }
        });


//        shitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putExtra("shit",shitText.getText());
//                setResult(RESULT_OK,intent);
//                finish();
//            }
//        });

        userService.showPmi().enqueue(new Callback<List<Pmi>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Pmi>> call, Response<List<Pmi>> response) {
                List<Pmi> listPmi = response.body();
                assert listPmi != null;
                pmiArrayList.addAll(listPmi);
                pmiResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pmi>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    public void initPmiResultRecyclerView(){
        pmiResultAdapter = new PmiResultAdapter(pmiArrayList, this, new PmiResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pmi item, int position) {
                Intent intent = new Intent();
                intent.putExtra(GlobalValues.PMI_INTENT,item);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(pmiResultAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}