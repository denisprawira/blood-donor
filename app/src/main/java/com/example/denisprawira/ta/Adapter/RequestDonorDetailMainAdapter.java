package com.example.denisprawira.ta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.Helper.RetrofitHelper;
import com.example.denisprawira.ta.Api.RetrofitServices;
import com.example.denisprawira.ta.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.Model.User;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.Event.RequestActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestDonorDetailMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    Calendar calendar = Calendar.getInstance();
    int hour        = calendar.get(Calendar.HOUR);
    int minute      = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<User> donorList = new ArrayList<>();

    DonorResponseAdapter donorResponseAdapter;
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    static final int ITEM_UPPER      = 0;
    static final int ITEM_BOTTOM     = 1;

    private Context context;

    FragmentManager fragmentManager;

    RequestDonor requestDonor;

    public RequestDonorDetailMainAdapter(Context context){
        this.context = context;
    }

    public RequestDonorDetailMainAdapter(RequestDonor requestDonor,FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context         = context;
        this.requestDonor    = requestDonor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder vh = null;
        switch(viewType){
            case ITEM_UPPER:
                view = layoutInflater.inflate(R.layout.layout_request_donor_detail_top,parent,false);
                vh =  new UpperViewHolder(view);
                break;
            case ITEM_BOTTOM:
               view = layoutInflater.inflate(R.layout.layout_request_donor_detail_bottom,parent,false);
                vh = new BottomViewHolder(view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.layout_request_donor_detail_bottom,parent,false);
                vh = new BottomViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType()==ITEM_UPPER){
            upperView((UpperViewHolder) viewHolder);
        }
        else if(viewHolder.getItemViewType()==ITEM_BOTTOM){
            bottomView((BottomViewHolder) viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==ITEM_UPPER)
            return ITEM_UPPER;
        if(position ==ITEM_BOTTOM)
            return ITEM_BOTTOM;
        return -1;
    }

    public class UpperViewHolder extends RecyclerView.ViewHolder{
        ImageButton backDetailDonor;
        TextView donorRequestDetailBlood,donorRequestDetailUtdName,donorRequestDetailTime,donorRequestDetailAddress;
        UpperViewHolder( View v) {
            super(v);
            backDetailDonor           = v.findViewById(R.id.backDetailDonor);
            donorRequestDetailBlood   = v.findViewById(R.id.donorRequestDetailBlood);
            donorRequestDetailUtdName = v.findViewById(R.id.donorRequestDetailUtdName);
            donorRequestDetailTime    = v.findViewById(R.id.donorRequestDetailTime);
            donorRequestDetailAddress = v.findViewById(R.id.donorRequestDetailAddress);
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerViewPendonor;
        BottomViewHolder( View v){
            super(v);
            recyclerViewPendonor = v.findViewById(R.id.recyclerViewPendonor);
        }
    }

    public void upperView(final UpperViewHolder holder){
        Toast.makeText(context, requestDonor.getGolDarah(), Toast.LENGTH_SHORT).show();
        holder.donorRequestDetailBlood.setText(requestDonor.getGolDarah());
        holder.donorRequestDetailUtdName.setText(requestDonor.getUtdName());
        holder.donorRequestDetailTime.setText(GlobalHelper.convertDateTimeToTime(requestDonor.getTanggal()));
        holder.donorRequestDetailAddress.setText(requestDonor.getUtdAddress());
        holder.backDetailDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RequestActivity.class));
            }
        });

    }

    public void bottomView(final BottomViewHolder holder){
        loadDonorResponse(requestDonor.getIdDonor());
        donorResponseAdapter = new DonorResponseAdapter(context, donorList, new DonorResponseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item, int position) {
                Toast.makeText(context, "adapter on click listener", Toast.LENGTH_SHORT).show();
            }
        });
        holder.recyclerViewPendonor.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewPendonor.setAdapter(donorResponseAdapter);


    }
//
    public void loadDonorResponse(String idDonor){
        retrofitServices.loadDonorResponse(idDonor).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                donorList.clear();
                List<User> res = response.body();
                donorList.addAll(res);
                donorResponseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
