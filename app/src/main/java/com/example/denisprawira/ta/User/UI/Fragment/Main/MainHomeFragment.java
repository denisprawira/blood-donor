package com.example.denisprawira.ta.User.UI.Fragment.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.RetrofitResult;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.BloodBankActivity;
import com.example.denisprawira.ta.User.UI.JoinedEventDonorActivity;
import com.example.denisprawira.ta.User.UI.MapsActivity;
import com.example.denisprawira.ta.User.UI.RequestEventDonorActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


public class MainHomeFragment extends Fragment implements View.OnClickListener{
//    ImageButton nextToGolDarah;

    DrawerLayout drawerLayout;
    CardView bloodBank, requestEvent,joinedEvent,findDonor;
    TextView eventNum;


    SessionManager sessionManager;
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    SharedPreferences sharedPreferences;
    public MainHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_home, container, false);
        sharedPreferences = getContext().getSharedPreferences("sha", MODE_PRIVATE);
        sessionManager = new SessionManager(getActivity());

        bloodBank = view.findViewById(R.id.bloodBank);
        requestEvent = view.findViewById(R.id.requestEvent);
        joinedEvent = view.findViewById(R.id.joinedEvent);
        findDonor = view.findViewById(R.id.findDonor);

        eventNum = view.findViewById(R.id.eventNum);
        bloodBank.setOnClickListener(this);
        requestEvent.setOnClickListener(this);
        findDonor.setOnClickListener(this);
        joinedEvent.setOnClickListener(this);

        ImageButton openDrawer= view.findViewById(R.id.imageButton);
        drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        countEvent();
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                countEvent();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);

        return view;
    }


    @Override
    public void onClick(View v){
        if(v==bloodBank){
            setCardViewAnimation(bloodBank);
            Intent intent  =  new Intent(getActivity(),BloodBankActivity.class);
            startActivity(intent);
        }else if(v==requestEvent){
            setCardViewAnimation(requestEvent);
            Intent intent = new Intent(getActivity(), RequestEventDonorActivity.class);
            startActivity(intent);
        }else if(v==joinedEvent){
            setCardViewAnimation(joinedEvent);
            Intent intent = new Intent(getActivity(),JoinedEventDonorActivity.class);
            startActivity(intent);
        }else if(v==findDonor){
            Intent intent = new Intent(getContext(),MapsActivity.class);
            startActivity(intent);
        }
    }

    public void setCardViewAnimation(CardView v){
        Animation cardViewAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.card_view_animation);
        v.startAnimation(cardViewAnimation);
    }

    public void countEvent(){
        retrofitServices.countEvent().enqueue(new Callback<List<RetrofitResult>>() {
            @Override
            public void onResponse(Call<List<RetrofitResult>> call, Response<List<RetrofitResult>> response) {
                List<RetrofitResult> num = response.body();
                for(int i=0; i<num.size();i++){
                    GlobalHelper.countEvent = num.get(i).getMessage();
                }
                eventNum.setText(GlobalHelper.countEvent);
            }

            @Override
            public void onFailure(Call<List<RetrofitResult>> call, Throwable t) {

            }
        });
    }
}