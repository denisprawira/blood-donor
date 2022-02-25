package com.example.denisprawira.ta.User.UI.Fragment.Signup;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.denisprawira.ta.R;




public class signupStepTwo extends Fragment implements View.OnClickListener{
    Button nextToRhesus,aplus, bplus,abplus,oplus,aneg,bneg,abneg,oneg,tidakTahu;
    String nama,email,telp;
    String goldarah = "";
    public signupStepTwo() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_signup_step2, container, false);


        assert getArguments() != null;
        nama        = getArguments().getString("nama");
        email       = getArguments().getString("email");
        telp        = getArguments().getString("telp");

        aplus           = view.findViewById(R.id.aplus);
        bplus           = view.findViewById(R.id.bplus);
        abplus          = view.findViewById(R.id.abplus);
        oplus           = view.findViewById(R.id.oplus);
        aneg            = view.findViewById(R.id.aneg);
        bneg            = view.findViewById(R.id.bneg);
        abneg           = view.findViewById(R.id.abneg);
        oneg            = view.findViewById(R.id.oneg);
        tidakTahu       = view.findViewById(R.id.tidakTahu);
        nextToRhesus    = view.findViewById(R.id.nextToPhoto);

        aplus.setOnClickListener(this);
        bplus.setOnClickListener(this);
        abplus.setOnClickListener(this);
        oplus.setOnClickListener(this);
        aneg.setOnClickListener(this);
        bneg.setOnClickListener(this);
        abneg.setOnClickListener(this);
        oneg.setOnClickListener(this);
        aplus.setOnClickListener(this);
        tidakTahu.setOnClickListener(this);
        nextToRhesus.setOnClickListener(this);

        return view;
    }

    public void nextToPhoto(String nama, String email, String telp, String goldarah){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        SignupStepThree signupGenderFragment= new SignupStepThree();

        Bundle data = new Bundle();
        data.putString("nama", nama);
        data.putString("email", email);
        data.putString("telp",telp);
        data.putString("goldarah",goldarah);
        signupGenderFragment.setArguments(data);

        ft.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right);
        ft.replace(R.id.signup_fragment,signupGenderFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        if(v==aplus){
            chooseBlood("1",aplus,bplus,abplus,oplus,aneg,bneg,abneg,oneg,tidakTahu);
        }else if(v==bplus){
            chooseBlood("2",bplus,aplus,abplus,oplus,aneg,bneg,abneg,oneg,tidakTahu);
        }else if(v==abplus){
            chooseBlood("3",abplus,aplus,bplus,oplus,aneg,bneg,abneg,oneg,tidakTahu);
        }else if(v==oplus){
            chooseBlood("4",oplus,aplus,bplus,abplus,aneg,bneg,abneg,oneg,tidakTahu);
        }else if(v==aneg){
            chooseBlood("5",aneg,aplus,bplus,abplus,oplus,bneg,abneg,oneg,tidakTahu);
        }else if(v==bneg){
            chooseBlood("6",bneg,aplus,bplus,abplus,oplus,aneg,abneg,oneg,tidakTahu);
        }else if(v==abneg){
            chooseBlood("7",abneg,aplus,bplus,abplus,oplus,aneg,bneg,oneg,tidakTahu);
        }else if(v==oneg){
            chooseBlood("8",oneg,aplus,bplus,abplus,oplus,aneg,bneg,abneg,tidakTahu);
        }else if(v==tidakTahu){
            chooseBlood("9",tidakTahu,aplus,bplus,abplus,oplus,aneg,bneg,abneg,oneg);
        }else if(v==nextToRhesus){
            if (goldarah.equals("")) {
                Toast.makeText(getContext(), "tolong pilih salah satu golongan darah", Toast.LENGTH_SHORT).show();
            } else if (!goldarah.isEmpty()) {
                nextToPhoto(nama, email, telp, goldarah);
            }
        }

    }

    public void chooseBlood(String value,Button selected, Button non1, Button non2, Button non3, Button non4, Button non5, Button non6, Button non7, Button non8){
            selected.setTextColor(getResources().getColor(R.color.white));
            non1.setTextColor(getResources().getColor(R.color.colorAccent));
            non2.setTextColor(getResources().getColor(R.color.colorAccent));
            non3.setTextColor(getResources().getColor(R.color.colorAccent));
            non4.setTextColor(getResources().getColor(R.color.colorAccent));
            non5.setTextColor(getResources().getColor(R.color.colorAccent));
            non6.setTextColor(getResources().getColor(R.color.colorAccent));
            non7.setTextColor(getResources().getColor(R.color.colorAccent));
            non8.setTextColor(getResources().getColor(R.color.colorAccent));

            selected.setBackgroundResource(R.drawable.theme_button_solid_radius);
            non1.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non2.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non3.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non4.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non5.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non6.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non7.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            non8.setBackgroundResource(R.drawable.theme_button_stroke_ripple);
            goldarah=value;
    }

}