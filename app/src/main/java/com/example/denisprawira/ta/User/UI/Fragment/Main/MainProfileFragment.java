package com.example.denisprawira.ta.User.UI.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.ProfileEditActivity;


public class MainProfileFragment extends Fragment implements View.OnClickListener {
    ImageView mainProfileImage;
    Button editProfile;
    TextView profileUserName, profilePhone,profileEmail, profileGoldarah,profileGender;

    SessionManager sessionManager;

    public MainProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_profile, container, false);
        sessionManager = new SessionManager(getActivity());
        mainProfileImage    = view.findViewById(R.id.mainProfileImage);
        profileUserName     = view.findViewById(R.id.profileUserName);
        profilePhone        = view.findViewById(R.id.profilePhone);
        profileEmail        = view.findViewById(R.id.profileEmail);
        profileGender       = view.findViewById(R.id.profileGender);
        profileGoldarah     = view.findViewById(R.id.profileGoldarah);
        editProfile         = view.findViewById(R.id.editProfile);
        
        editProfile.setOnClickListener(this);
        profileUserName.setText(sessionManager.getName());
        profilePhone.setText(sessionManager.getPhone());
        profileEmail.setText(sessionManager.getEmail());
        if(sessionManager.getBlood().equals("1")){
            profileGoldarah.setText("AB+");
        }else if(sessionManager.getBlood().equals("2")){
            profileGoldarah.setText("AB-");
        }else if(sessionManager.getBlood().equals("3")){
            profileGoldarah.setText("A+");
        }else if(sessionManager.getBlood().equals("4")){
            profileGoldarah.setText("A-");
        }else if(sessionManager.getBlood().equals("5")){
            profileGoldarah.setText("B+");
        }else if(sessionManager.getBlood().equals("6")){
            profileGoldarah.setText("B-");
        }else if(sessionManager.getBlood().equals("7")){
            profileGoldarah.setText("O+");
        }else if(sessionManager.getBlood().equals("8")){
            profileGoldarah.setText("O-");
        }

        profileGender.setText(sessionManager.getGender());

        Glide.with(getActivity()).load(sessionManager.getPhoto()).into(mainProfileImage);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        profileUserName.setText(sessionManager.getName());
        profilePhone.setText(sessionManager.getPhone());
        profileEmail.setText(sessionManager.getEmail());
        if(sessionManager.getBlood().equals("1")){
            profileGoldarah.setText("AB+");
        }else if(sessionManager.getBlood().equals("2")){
            profileGoldarah.setText("AB-");
        }else if(sessionManager.getBlood().equals("3")){
            profileGoldarah.setText("A+");
        }else if(sessionManager.getBlood().equals("4")){
            profileGoldarah.setText("A-");
        }else if(sessionManager.getBlood().equals("5")){
            profileGoldarah.setText("B+");
        }else if(sessionManager.getBlood().equals("6")){
            profileGoldarah.setText("B-");
        }else if(sessionManager.getBlood().equals("7")){
            profileGoldarah.setText("O+");
        }else if(sessionManager.getBlood().equals("8")){
            profileGoldarah.setText("O-");
        }else if(sessionManager.getBlood().equals("9")){
            profileGoldarah.setText("Tidak Tahu");
        }

        profileGender.setText(sessionManager.getGender());

        Glide.with(getActivity()).load(sessionManager.getPhoto()).into(mainProfileImage);
    }

    @Override
    public void onClick(View v) {
        if(v==editProfile){
            Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
            startActivity(intent);
        }
    }
}