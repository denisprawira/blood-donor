package com.example.denisprawira.ta.Adapter2.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.denisprawira.ta.UI.Fragment.Joined.EventFragment;
import com.example.denisprawira.ta.UI.Fragment.Joined.DonorFragment;

public class JoinedviewPagerAdapter extends FragmentStateAdapter {


    public JoinedviewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new EventFragment();
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
