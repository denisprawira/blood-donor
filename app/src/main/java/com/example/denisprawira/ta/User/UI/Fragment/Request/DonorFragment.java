package com.example.denisprawira.ta.User.UI.Fragment.Request;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.RequestDonorAdapter;
import com.example.denisprawira.ta.User.Helper.DividerItemDecoration;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Event.DetailDonorRequestActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonorFragment extends Fragment {



    RecyclerView donorRequestRecyclerView;
    RequestDonorAdapter requestDonorAdapter;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    ArrayList<RequestDonor> requestDonorList= new ArrayList<>() ;

    SessionManager sessionManager;
    public DonorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_request_donor, container, false);
        sessionManager  = new SessionManager(getActivity());
        donorRequestRecyclerView = view.findViewById(R.id.requestDonorRecyclerView);
        loadJoinedDonor(sessionManager.getId());
        initRecyclerView();
        return view;
    }

    public void loadJoinedDonor(String idUser) {
        retrofitServices.loadRequestDonor(idUser).enqueue(new Callback<List<RequestDonor>>() {
            @Override
            public void onResponse(Call<List<RequestDonor>> call, Response<List<RequestDonor>> response) {
                List<RequestDonor> donorList = response.body();
                requestDonorList.addAll(donorList);
                requestDonorAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<RequestDonor>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRecyclerView(){
        requestDonorAdapter = new RequestDonorAdapter(getContext(), requestDonorList, new RequestDonorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RequestDonor item, int position) {
                Intent intent =  new Intent(getActivity(), DetailDonorRequestActivity.class);
                intent.putExtra("idDonor",item.getIdDonor());
                intent.putExtra("idUtd",item.getIdUtd());
                intent.putExtra("golDarah",item.getGolDarah());
                intent.putExtra("utdName",item.getUtdName());
                intent.putExtra("utdEmail",item.getUtdEmail());
                intent.putExtra("utdAddress",item.getUtdAddress());
                intent.putExtra("utdTelp",item.getUtdTelp());
                intent.putExtra("lat",item.getLat());
                intent.putExtra("lng",item.getLng());
                intent.putExtra("utdPhoto",item.getUtdPhoto());
                intent.putExtra("utdToken",item.getUtdToken());
                intent.putExtra("status",item.getStatus());
                intent.putExtra("tanggal",item.getTanggal());
                startActivity(intent);
            }
        });
        donorRequestRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        donorRequestRecyclerView.setHasFixedSize(true);
        donorRequestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        donorRequestRecyclerView.setAdapter(requestDonorAdapter);

    }

}