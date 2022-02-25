package com.example.denisprawira.ta.User.UI.Fragment.Signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.R;


public class SignupStepOne extends Fragment implements View.OnClickListener{

    Button nextToGolDarah;
    EditText signUpNama;
    String nama;

    public SignupStepOne() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_signup_step1, container, false);

        nextToGolDarah = view.findViewById(R.id.nextToGolDarah);
        nextToGolDarah.setOnClickListener(this);
        signUpNama = view.findViewById(R.id.signUpNama);

        return view;
    }

    public void nextToEmail(String nama){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        signupStepTwo signupStepTwo = new signupStepTwo();

        Bundle data = new Bundle();
        data.putString("nama",nama);
        signupStepTwo.setArguments(data);

        ft.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right);
        ft.replace(R.id.signup_fragment, signupStepTwo);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.nextToGolDarah:{
                nama= signUpNama.getText().toString();
                if(nama.equals("")){
                    Toast.makeText(getContext(),"nama tolong diisi",Toast.LENGTH_SHORT).show();
                }else{
                    nextToEmail(nama);
                }
            }
        }
    }
}