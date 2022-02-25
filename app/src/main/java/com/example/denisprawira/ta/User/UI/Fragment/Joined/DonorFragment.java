package com.example.denisprawira.ta.User.UI.Fragment.Joined;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.JoinedDonorAdapter;
import com.example.denisprawira.ta.User.Helper.DividerItemDecoration;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Donor.JoinedDonor;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.DonorJoinedDetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonorFragment extends Fragment {



    RecyclerView donorJoinedRecyclerView;
    JoinedDonorAdapter joinedDonorAdapter;
    RecyclerView.LayoutManager  layoutmanager;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    ArrayList<JoinedDonor> joinedDonorList= new ArrayList<>() ;
    SessionManager sessionManager;
    public DonorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_joined_donor, container, false);
        sessionManager =  new SessionManager(getActivity());
        donorJoinedRecyclerView = view.findViewById(R.id.joinedDonorRecyclerView);
        loadJoinedDonor();
        initRecyclerView();
        return view;
    }

    public void loadJoinedDonor(){
        retrofitServices.loadjoinedDonor(sessionManager.getId()).enqueue(new Callback<List<JoinedDonor>>() {
            @Override
            public void onResponse(Call<List<JoinedDonor>> call, Response<List<JoinedDonor>> response) {
                List<JoinedDonor> donorList = response.body();
                joinedDonorList.addAll(donorList);
                joinedDonorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<JoinedDonor>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRecyclerView(){
        joinedDonorAdapter = new JoinedDonorAdapter(getContext(), joinedDonorList, new JoinedDonorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(JoinedDonor item, int position) {
                Intent intent = new Intent(getActivity(), DonorJoinedDetailActivity.class);
                intent.putExtra("idDonor",item.getIdDonor());
                intent.putExtra("idUser",item.getIdUser());
                intent.putExtra("idUtd",item.getIdUtd());
                intent.putExtra("tanggal",item.getTanggal());
                intent.putExtra("userName",item.getUserName());
                intent.putExtra("userPhoto",item.getUserPhoto());
                intent.putExtra("userTelp",item.getUserTelp());
                intent.putExtra("userToken",item.getUserToken());
                intent.putExtra("utdName",item.getUtdName());
                intent.putExtra("utdTelp",item.getUtdTelp());
                intent.putExtra("utdPhoto",item.getUtdPhoto());
                intent.putExtra("utdAddress",item.getUtdAddress());
                intent.putExtra("golDarah",item.getGolDarah());
                intent.putExtra("lat",item.getLat());
                intent.putExtra("lng",item.getLng());
                startActivity(intent);
            }
        });
        donorJoinedRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        donorJoinedRecyclerView.setHasFixedSize(true);
        donorJoinedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        donorJoinedRecyclerView.setAdapter(joinedDonorAdapter);

    }




}