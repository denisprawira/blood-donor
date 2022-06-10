package com.example.denisprawira.ta.UI.Fragment.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.LoginActivity;

public class SignupWelcome extends Fragment {
    Button nextToEmail, nextToLogin,nextToPmi;
    TextView welcomeTittle, welcomeMessage;
    View welcomeDivider;

    Animation animation, animation2,animation3;
    public SignupWelcome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup_welcome, container, false);
        nextToEmail=view.findViewById(R.id.welcome_signup);
        nextToLogin=view.findViewById(R.id.welcome_login);
        welcomeTittle = view.findViewById(R.id.welcomeTittle);
        welcomeMessage = view.findViewById(R.id.welcomeMessage);

        animation = AnimationUtils.loadAnimation(getContext(),R.anim.transition);
        animation2 = AnimationUtils.loadAnimation(getContext(),R.anim.transition2);
        animation3 = AnimationUtils.loadAnimation(getContext(),R.anim.transition3);


        welcomeTittle.startAnimation(animation);
        welcomeMessage.startAnimation(animation2);
        nextToEmail.startAnimation(animation3);
        nextToLogin.startAnimation(animation3);



        nextToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        nextToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                laodUer();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right);
                ft.replace(R.id.signup_fragment,new SignupStepOne());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }


}