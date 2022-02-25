package com.example.denisprawira.ta.User.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Fragment.Joined.DonorFragment;
import com.example.denisprawira.ta.User.UI.Fragment.Joined.EventFragment;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JoinedEventDonorActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    BubbleNavigationConstraintView bubbleNavigationConstraintView;

    Calendar calendar = Calendar.getInstance();
    int hour        = calendar.get(Calendar.HOUR);
    int minute      = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);


    ImageButton backJoinedEvent;
    ImageView eventJoinedProfileImage;
    TextView eventJoinedLabelDate,eventJoinedLabelDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_event_donor);



        eventJoinedLabelDate    = findViewById(R.id.eventJoinedLabelDate);
        eventJoinedLabelDay     = findViewById(R.id.eventJoinedLabelDay);


        eventJoinedLabelDate.setText(GlobalHelper.convertDayMonth(year+"-"+(month+1)+"-"+day)+" ,"+GlobalHelper.convertToYear(year+"-"+(month+1)+"-"+day));
        eventJoinedLabelDay.setText(GlobalHelper.convertToDayOfWeek(year+"-"+(month+1)+"-"+day));


        backJoinedEvent = findViewById(R.id.backJoinedEvent);
        backJoinedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        mViewPager = (ViewPager) findViewById(R.id.container);
        bubbleNavigationConstraintView = findViewById(R.id.floating_top_bar_navigation);





        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new EventFragment(), "Event");
        mSectionsPagerAdapter.addFragment(new DonorFragment(),"RequestDonor");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationConstraintView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bubbleNavigationConstraintView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                mViewPager.setCurrentItem(position, true);
            }
        });

    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList  = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            titleList.add(title);
        }
    }
}
