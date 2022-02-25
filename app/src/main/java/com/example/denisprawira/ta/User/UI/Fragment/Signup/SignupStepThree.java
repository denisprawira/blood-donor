package com.example.denisprawira.ta.User.UI.Fragment.Signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.R;


public class SignupStepThree extends Fragment implements View.OnClickListener{
    Button nextToPhoto;
    String nama, email, telp, goldarah;
    String gender = "";

    CardView male,female;
    public SignupStepThree() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_signup_step3, container, false);


        assert getArguments() != null;
        nama        = getArguments().getString("nama");
        email       = getArguments().getString("email");
        telp        = getArguments().getString("telp");
        goldarah    = getArguments().getString("goldarah");

        male        = view.findViewById(R.id.signUpGenderMale);
        female      = view.findViewById(R.id.signUpGenderFemale);
        nextToPhoto = view.findViewById(R.id.nextToPhoto);
        nextToPhoto.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);




        return view;
    }

    public void nextToPhoto(String nama, String email, String telp, String goldarah,String gender){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        SignupStepFour signupStepFour = new SignupStepFour();

        Bundle data = new Bundle();
        data.putString("nama", nama);
        data.putString("email", email);
        data.putString("telp",telp);
        data.putString("goldarah",goldarah);
        data.putString("gender",gender);
        signupStepFour.setArguments(data);

        ft.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right);
        ft.replace(R.id.signup_fragment, signupStepFour);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        if(v==male){
            gender = "Laki-laki";
            male.animate().scaleY(0.95f).scaleX(0.95f).setDuration(150).setInterpolator(new AccelerateInterpolator()).start();
            female.animate().scaleY(1).scaleX(1).setDuration(150).setInterpolator(new AccelerateInterpolator()).start();
            male.setElevation(3);
            female.setElevation(6);
            male.setCardBackgroundColor(getResources().getColor(R.color.black_overlay));
            female.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==female){
            gender = "Perempuan";
            female.animate().scaleY(0.95f).scaleX(0.95f).setDuration(150).setInterpolator(new AccelerateInterpolator()).start();
            male.animate().scaleY(1).scaleX(1).setDuration(150).setInterpolator(new AccelerateInterpolator()).start();
            female.setElevation(3);
            male.setElevation(6);
            female.setCardBackgroundColor(getResources().getColor(R.color.black_overlay));
            male.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==nextToPhoto){
            nextToPhoto(nama, email, telp, goldarah,gender);
        }

    }



}