package com.example.denisprawira.ta.UI.Fragment.Request;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Adapter.RequestDonorAdapter;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.HelpRequest;
import com.example.denisprawira.ta.Api.Model.User;
import com.example.denisprawira.ta.Api.Model.User2;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Helper.DividerItemDecoration;
import com.example.denisprawira.ta.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.SessionManager.UserSessionManager;
import com.example.denisprawira.ta.UI.Event.DetailDonorRequestActivity;
import com.example.denisprawira.ta.Values.GlobalValues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonorFragment extends Fragment {



    RecyclerView donorRequestRecyclerView;
    RequestDonorAdapter requestDonorAdapter;
    Spinner requestDonorSpinner;

    UserService userService = ApiUtils.getUserService();
    ArrayList<HelpRequest> requestDonorList= new ArrayList<>() ;

    UserSessionManager userSessionManager;
    public DonorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_request_donor, container, false);
        userSessionManager  = new UserSessionManager(getActivity());
        donorRequestRecyclerView = view.findViewById(R.id.requestDonorRecyclerView);
        requestDonorSpinner = view.findViewById(R.id.request_donor_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.helprequestStatus,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       requestDonorSpinner.setAdapter(adapter);
        requestDonorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    Log.e("result",String.valueOf(i));
                }else if(i==1){
                    Log.e("result",String.valueOf(i));
                }else{
                    Log.e("result",String.valueOf(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadJoinedDonor(userSessionManager.getId(), GlobalValues.HELP_APPROVED);
        initRecyclerView();
        return view;
    }

    public void loadJoinedDonor(String idUser, String status) {
        userService.showHelprequest(idUser,status).enqueue(new Callback<List<HelpRequest>>() {
            @Override
            public void onResponse(Call<List<HelpRequest>> call, Response<List<HelpRequest>> response) {
                List<HelpRequest> helpRequests = response.body();
                requestDonorList.addAll(helpRequests);
                requestDonorAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<HelpRequest>> call, Throwable t) {

            }
        });
    }

    public void initRecyclerView(){
        requestDonorAdapter = new RequestDonorAdapter(getContext(), requestDonorList, new RequestDonorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HelpRequest item, int position) {
                Intent intent =  new Intent(getActivity(), DetailDonorRequestActivity.class);
                intent.putExtra("helpRequest",item);
                startActivity(intent);

            }
        });
        donorRequestRecyclerView.setAdapter(requestDonorAdapter);
        donorRequestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        donorRequestRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

    }

}