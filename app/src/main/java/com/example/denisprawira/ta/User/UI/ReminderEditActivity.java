package com.example.denisprawira.ta.User.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.denisprawira.ta.R;

public class ReminderEditActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REMINDER_ID = "Reminder_ID";

    CardView chooseGoldarah1,chooseGoldarah2,chooseGoldarah3,chooseGoldarah4,chooseGoldarah5,chooseGoldarah6,chooseGoldarah7,chooseGoldarah8;
    ImageView goldarahImage1,goldarahImage2,goldarahImage3,goldarahImage4,goldarahImage5,goldarahImage6,goldarahImage7,goldarahImage8;
    TextView goldarahText1,goldarahText2,goldarahText3,goldarahText4,goldarahText5,goldarahText6,goldarahText7,goldarahText8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_for_map);

//        chooseGoldarah1 = findViewById(R.id.chooseGoldarah1);
//        chooseGoldarah2 = findViewById(R.id.chooseGoldarah2);
//        chooseGoldarah3 = findViewById(R.id.chooseGoldarah3);
//        chooseGoldarah4 = findViewById(R.id.chooseGoldarah4);
//        chooseGoldarah5 = findViewById(R.id.chooseGoldarah5);
//        chooseGoldarah6 = findViewById(R.id.chooseGoldarah6);
//        chooseGoldarah7 = findViewById(R.id.chooseGoldarah7);
//        chooseGoldarah8 = findViewById(R.id.chooseGoldarah8);
//
//        chooseGoldarah1.setOnClickListener(this);
//        chooseGoldarah2.setOnClickListener(this);
//        chooseGoldarah3.setOnClickListener(this);
//        chooseGoldarah4.setOnClickListener(this);
//        chooseGoldarah5.setOnClickListener(this);
//        chooseGoldarah6.setOnClickListener(this);
//        chooseGoldarah7.setOnClickListener(this);
//        chooseGoldarah8.setOnClickListener(this);
//
//        goldarahImage1  = findViewById(R.id.goldarahImage1);
//        goldarahImage2  = findViewById(R.id.goldarahImage2);
//        goldarahImage3  = findViewById(R.id.goldarahImage3);
//        goldarahImage4  = findViewById(R.id.goldarahImage4);
//        goldarahImage5  = findViewById(R.id.goldarahImage5);
//        goldarahImage6  = findViewById(R.id.goldarahImage6);
//        goldarahImage7  = findViewById(R.id.goldarahImage7);
//        goldarahImage8  = findViewById(R.id.goldarahImage8);
//
//        goldarahText1   = findViewById(R.id.goldarahText1);
//        goldarahText2   = findViewById(R.id.goldarahText2);
//        goldarahText3   = findViewById(R.id.goldarahText3);
//        goldarahText4   = findViewById(R.id.goldarahText4);
//        goldarahText5   = findViewById(R.id.goldarahText5);
//        goldarahText6   = findViewById(R.id.goldarahText6);
//        goldarahText7   = findViewById(R.id.goldarahText7);
//        goldarahText8   = findViewById(R.id.goldarahText8);

    }

    @Override
    public void onClick(View v) {
//         if(v==chooseGoldarah1){
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText1.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah2){
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText2.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah3){
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText3.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah4){
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText4.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah5){
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText5.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah6){
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText6.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah7){
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText7.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_border);
//
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }else if(v==chooseGoldarah8){
//             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
//             goldarahImage8.setImageResource(R.drawables.ic_drop_blood_white);
//             goldarahText8.setTextColor(getResources().getColor(R.color.colorAccent));
//
//             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
//             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
//
//             goldarahImage1.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage2.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage3.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage4.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage5.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage6.setImageResource(R.drawables.ic_drop_blood_border);
//             goldarahImage7.setImageResource(R.drawables.ic_drop_blood_border);
//
//
//             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
//             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
//        }
    }
}
