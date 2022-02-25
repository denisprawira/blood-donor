package com.example.denisprawira.ta.User.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.User.Adapter.UtdAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Utd;
import com.example.denisprawira.ta.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserDatabaseActivity extends AppCompatActivity {


    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    RecyclerView recyclerViewUser;
    ArrayList<Utd> utdList = new ArrayList<>();
    ArrayList<Utd> utdListFull = new ArrayList<>();
    UtdAdapter utdAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        recyclerViewUser = findViewById(R.id.recyclerViewUser);
        initRecyclerView();
        loadUtdData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                utdAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void loadUtdData(){
        retrofitServices.loadUtd().enqueue(new Callback<List<Utd>>() {
            @Override
            public void onResponse(Call<List<Utd>> call, Response<List<Utd>> response) {
                List<Utd> utd = response.body();
                utdListFull.addAll(utd);
                utdList.addAll(utd);
                utdAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Utd>> call, Throwable t) {
                Toast.makeText(SearchUserDatabaseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRecyclerView(){
        utdAdapter = new UtdAdapter(this, utdList, utdListFull, new UtdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Utd item, int position) {
                Intent intent = new Intent();
                intent.putExtra("idUtd",item.getId());
                intent.putExtra("utdName",item.getNama());
                intent.putExtra("utdToken",item.getToken());
                intent.putExtra("utdLat",item.getLat());
                intent.putExtra("utdLng",item.getLng());
                setResult(RESULT_OK,intent);
                finish();            }
        });
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(SearchUserDatabaseActivity.this));
        recyclerViewUser.setAdapter(utdAdapter);

    }
}
