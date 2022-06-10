package com.example.denisprawira.ta.UI;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Adapter.BloodBankMainAdapter;
import com.example.denisprawira.ta.R;

public class BloodBankActivity extends AppCompatActivity {

    RecyclerView bloodBankRecyclerview;
    BloodBankMainAdapter bloodBankMainAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        bloodBankRecyclerview = findViewById(R.id.bloodBankRecyclerview);
        bloodBankRecyclerview.setHasFixedSize(true);
        setRecyclerViewEvent();
        loadBloodRequest();

    }

    public void setRecyclerViewEvent(){
        bloodBankMainAdapter =  new BloodBankMainAdapter(this);
        bloodBankRecyclerview.setAdapter(bloodBankMainAdapter);
        layoutManager = new LinearLayoutManager(this);
        bloodBankRecyclerview.setLayoutManager(layoutManager);
    }

    public void loadBloodRequest(){
//        RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
//        retrofitServices.loadBloodRequest("afd").enqueue(new Callback<RequestBlood>() {
//            @Override
//            public void onResponse(Call<RequestBlood> call, Response<RequestBlood> response) {
//                RequestBlood requestBlood =  response.body();
//                for(int i=0; i<requestBlood.getData().size();i++){
//                    String st = requestBlood.getData().get(i).getUnit();
//                    String[] parts = st.split(" ");
//                    String first = parts[0];//"hello"
//                    String second = parts[1];//"World"
//                    String third = parts[2];//"hello"
//                    String fourth = parts[3];
//                    if(third.equals("Provinsi")){
//                        Toast.makeText(BloodBankActivity.this, third, Toast.LENGTH_SHORT).show();
//                       // Toast.makeText(BloodBankActivity.this, first+" "+second+" "+third+" "+fourth, Toast.LENGTH_SHORT).show();
//                    }else{
//                       // Toast.makeText(BloodBankActivity.this, first+" "+second+" "+third, Toast.LENGTH_SHORT).show();
//                    }
//
////                    Toast.makeText(BloodBankActivity.this, st, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RequestBlood> call, Throwable t) {
//                Toast.makeText(BloodBankActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
