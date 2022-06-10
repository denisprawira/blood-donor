package com.example.denisprawira.ta.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.Helper.RetrofitHelper;
import com.example.denisprawira.ta.Api.RetrofitServices;
import com.example.denisprawira.ta.Model.Blood.Data;
import com.example.denisprawira.ta.Model.Blood.RequestBlood;
import com.example.denisprawira.ta.Model.Component;
import com.example.denisprawira.ta.Model.GolDarah;
import com.example.denisprawira.ta.Model.Utd;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BloodBankMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int ITEM_UPPER=0;
    static final int ITEM_BOTTOM = 1;

    String selectedGolDarah = "";
    String selectedComponent = "";
    String component = "";
    String goldarah = "";

    Context context;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);


    RecyclerView recyclerview_pmi;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Utd> pmiList = new ArrayList<>();
    Dialog dialogPmi, dialogComponent, dialogGolDarah;
    UtdAdapter pmiAdapter;

    RecyclerView recyclerview_goldarah;
    RecyclerView.LayoutManager layoutManagerGolDarah;
    List<GolDarah> golDarahList = new ArrayList<>();
    GolDarahAdapter golDarahAdapter;

    RecyclerView recyclerview_component;
    RecyclerView.LayoutManager layoutManagerComponent;
    List<Component> componentList= new ArrayList<>();
    ComponentAdapter componentAdapter;

    BloodStockAdapter bloodStockAdapter;
    List<Data> bloodStockList = new ArrayList<>();

    public BloodBankMainAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder vh = null;
        if (i == ITEM_UPPER) {
            view = layoutInflater.inflate(R.layout.layout_blood_form, viewGroup, false);
            vh = new ItemUpperViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.layout_blood_recyclerview, viewGroup, false);
            vh = new ItemBottomViewHolder(view);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType()== ITEM_UPPER){
            itemUpperView((ItemUpperViewHolder) viewHolder);
        }else if(viewHolder.getItemViewType()== ITEM_BOTTOM){
            itemBottomView((ItemBottomViewHolder) viewHolder);
        }
    }




    public void itemUpperView(final ItemUpperViewHolder item){

        item.backBloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
        });

        item.bloodBankGolDarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] golDarah = {"AB+","Ab-","A+","A-","B+","B-","O+","O-"};
                final String[] golDarahVar = {"ab_pos","ab_neg","a_pos","a_neg","b_pos","b_neg","o_pos","o_neg"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,golDarah);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedGolDarah = golDarahVar[which];
                        item.bloodBankGolDarah.setText(golDarah[which]);
                        goldarah  = golDarah[which];
                    }
                });
                builder2.show();
            }
        });

        item.bloodBankComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "open the door", Toast.LENGTH_SHORT).show();
                final String[] option = {"AHF","BC","FFP","Lekosit Aferesis","LP","LP Aferesis","PCL","PCLs","PRC","PRC Aferesis","PRP","TC","TC Aferesis","TC APR","TCP","WB","WB FRESH","WE"};
                final String[] optionVar = {"AHF","BC","FFP","Lekosit+Aferesis","LP","LP+Aferesis","PCL","PCLs","PRC","PRC+Aferesis","PRP","TC","TC+Aferesis","TC+APR","TCP","WB","WB+FRESH","WE"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,option);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedComponent = optionVar[which];
                        item.bloodBankComponent.setText(option[which]);
                        component = option[which];
                    }
                });
                builder.show();
                //dialogPmi.show();
            }
        });

        item.searchBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "sudah submit", Toast.LENGTH_SHORT).show();
                if(selectedGolDarah!=null&&selectedComponent!=null){
                    searchBlood(selectedGolDarah,selectedComponent);
                }else{
                    Toast.makeText(context, "Form pencarian tolong dilengkapi", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void itemBottomView(ItemBottomViewHolder item){
        bloodStockAdapter  =new BloodStockAdapter(context, bloodStockList, new BloodStockAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Data item, int position) {

            }
        });
        item.bloodStockRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        item.bloodStockRecyclerview.setAdapter(bloodStockAdapter);

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==ITEM_UPPER)
            return ITEM_UPPER;
        if(position == ITEM_BOTTOM)
            return ITEM_BOTTOM;
        return -1;
    }


    public class ItemUpperViewHolder extends RecyclerView.ViewHolder{
        EditText bloodBankGolDarah,bloodBankComponent;
        Button searchBlood;
        ImageButton backBloodBank;
        ItemUpperViewHolder( View itemView) {
            super(itemView);
            bloodBankGolDarah   = itemView.findViewById(R.id.bloodBankGolDarah);
            bloodBankComponent  = itemView.findViewById(R.id.bloodBankComponent);
            searchBlood         = itemView.findViewById(R.id.searchBlood);
            backBloodBank       = itemView.findViewById(R.id.backBloodBank);
        }
    }

    public class ItemBottomViewHolder extends RecyclerView.ViewHolder{
        RecyclerView bloodStockRecyclerview;
        ItemBottomViewHolder(View itemView) {
            super(itemView);
            bloodStockRecyclerview = itemView.findViewById(R.id.bloodRecyclerview);
        }
    }

//
//    public void loadGolDarah(final EditText golDarahEditText){
//        dialogGolDarah = new Dialog(context);
//        dialogGolDarah.setContentView(R.layout.layout_dialog_goldarah);
//        dialogGolDarah.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        recyclerview_goldarah = dialogGolDarah.findViewById(R.id.recyclerview_goldarah);
//        recyclerview_goldarah.setHasFixedSize(true);
//        layoutManagerGolDarah = new LinearLayoutManager(context);
//        recyclerview_goldarah.setLayoutManager(layoutManagerGolDarah);
//
//        retrofitServices.loadGolDarah().enqueue(new Callback<List<GolDarah>>() {
//            @Override
//            public void onResponse(Call<List<GolDarah>> call, Response<List<GolDarah>> response) {
//                List<GolDarah> list = response.body();
//                for(int i=0; i<list.size();i++){
//                    if(!list.get(i).getId().equals("9")){
//                        golDarahList.add(list.get(i));
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<GolDarah>> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        golDarahAdapter = new GolDarahAdapter(context, golDarahList, new GolDarahAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(GolDarah item, int position) {
//                golDarah = item.getId();
//                dialogGolDarah.dismiss();
//                golDarahEditText.setText(item.getGolDarah());
//            }
//        });
//        recyclerview_goldarah.setAdapter(golDarahAdapter);
//    }
//
//    public void loadComponent(final EditText componentEditText){
//        dialogComponent = new Dialog(context);
//        dialogComponent.setContentView(R.layout.layout_dialog_component);
//        dialogComponent.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        recyclerview_component = dialogComponent.findViewById(R.id.recyclerview_component);
//        recyclerview_component.setHasFixedSize(true);
//        layoutManagerComponent = new LinearLayoutManager(context);
//        recyclerview_component.setLayoutManager(layoutManagerComponent);
//
//        retrofitServices.loadBloodComponent().enqueue(new Callback<List<Component>>() {
//            @Override
//            public void onResponse(Call<List<Component>> call, Response<List<Component>> response) {
//                List<Component> list = response.body();
//                componentList.addAll(list);
//            }
//            @Override
//            public void onFailure(Call<List<Component>> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        componentAdapter = new ComponentAdapter(context, componentList, new ComponentAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Component item, int position) {
//                component = item.getId();
//                dialogComponent.dismiss();
//                componentEditText.setText(item.getComponent());
//            }
//        });
//        recyclerview_component.setAdapter(componentAdapter);
//    }
//
//    public void loadPmi(final EditText pmiEditText){
//        dialogPmi = new Dialog(context);
//        dialogPmi.setContentView(R.layout.layout_dialog_listutd);
//        dialogPmi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        recyclerview_pmi = dialogPmi.findViewById(R.id.recyclerview_utd);
//        recyclerview_pmi.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(context);
//        recyclerview_pmi.setLayoutManager(layoutManager);
//
//        retrofitServices.loadUtd().enqueue(new Callback<List<Utd>>() {
//            @Override
//            public void onResponse(Call<List<Utd>> call, Response<List<Utd>> response) {
//                List<Utd> pmi = response.body();
//                pmiList.addAll(pmi);
//            }
//            @Override
//            public void onFailure(Call<List<Utd>> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        pmiAdapter = new UtdAdapter(context,pmiList, new UtdAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Utd item, int position) {
//                pmi = item.getId();
//                dialogPmi.dismiss();
//                pmiEditText.setText(item.getNama());
//            }
//        });
//        recyclerview_pmi.setAdapter(pmiAdapter);
//    }


    public void searchBlood(String gol, String com){
        retrofitServices.loadBloodRequest(gol,com).enqueue(new Callback<RequestBlood>() {
            @Override
            public void onResponse(Call<RequestBlood> call, Response<RequestBlood> response) {
                RequestBlood requestBlood =  response.body();
                bloodStockList.clear();
                for(int i=0; i<requestBlood.getData().size();i++){
                    Data data = requestBlood.getData().get(i);
                    String st = requestBlood.getData().get(i).getUnit();
                    String[] parts = st.split(" ");
                    String lines[] = parts[3].split("\\r?\\n");
                    String pmiName = parts[0]+" "+parts[1]+" "+parts[2]+" "+lines[0];
                    data.setUnit(pmiName);
                    data.setId(goldarah);
                    data.setProvinsi(component);
                    if(!requestBlood.getData().get(i).getJumlah().equals("0")){
                        bloodStockList.add(data);
                    }
                }
                bloodStockAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RequestBlood> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
