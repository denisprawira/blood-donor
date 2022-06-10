package com.example.denisprawira.ta.Adapter2.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.denisprawira.ta.UI.Fragment.Request.DonorFragment;
import com.example.denisprawira.ta.UI.Fragment.Request.EventFragment;
import com.example.denisprawira.ta.UIPMI.Fragment.Event.RequestedEventFragment;

public class RequestedEventViewPagerAdapter extends FragmentStateAdapter {


    public RequestedEventViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RequestedEventFragment();
            case 1:
                return new DonorFragment();
            default:
                return new EventFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
