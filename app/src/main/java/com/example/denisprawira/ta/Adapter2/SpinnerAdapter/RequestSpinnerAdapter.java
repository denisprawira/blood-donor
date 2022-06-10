package com.example.denisprawira.ta.Adapter2.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.denisprawira.ta.R;

import java.util.List;

public class RequestSpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<String> listStatus;

    public RequestSpinnerAdapter(Context context,List<String> listStatus) {
        this.context = context;
        this.listStatus = listStatus;
    }

    @Override
    public int getCount() {
        return listStatus !=null ? listStatus.size(): 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        View rootView = LayoutInflater.from(context).inflate(R.layout.);

        return null;
    }
}
