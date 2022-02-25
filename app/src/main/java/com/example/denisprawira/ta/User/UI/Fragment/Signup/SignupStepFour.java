package com.example.denisprawira.ta.User.UI.Fragment.Signup;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SignupStepFour extends Fragment implements View.OnClickListener{


    String url ="http://"+GlobalHelper.url+"/donor_darah/index.php/api/user/signUp";
    ImageButton UploadImage;
    EditText signUpRepassword;
    String nama, email, telp, goldarah,gender;
    Button nextToPassword;
    private Uri resultUri;

    File file;

    ImageView photoProfil;

    Bitmap profilePicture;

    public SignupStepFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_signup_step4, container, false);

        assert getArguments() != null;


        nama        = getArguments().getString("nama");
        email       = getArguments().getString("email");
        telp        = getArguments().getString("telp");
        goldarah    = getArguments().getString("goldarah");
        gender      = getArguments().getString("gender");

        photoProfil               = view.findViewById(R.id.callUserPhoto);
        UploadImage = view.findViewById(R.id.UploadImage);

        nextToPassword = view.findViewById(R.id.nextToPass);

        UploadImage.setOnClickListener(this);
        nextToPassword.setOnClickListener(this);





        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==nextToPassword){
            nextToPassword(nama, email, telp, goldarah,gender,resultUri.toString());
        }else if(v==UploadImage){
            CropImage.activity().setAspectRatio(1,1).setCropShape(CropImageView.CropShape.OVAL).start(getContext(), SignupStepFour.this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                Glide.with(getActivity()).load(resultUri.toString()).into(photoProfil);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void nextToPassword(String nama, String email, String telp, String goldarah,String gender, String uri){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        SignupPasswordFragment signupPasswordFragment= new SignupPasswordFragment();
        Bundle data = new Bundle();
        data.putString("nama", nama);
        data.putString("email", email);
        data.putString("telp",telp);
        data.putString("goldarah",goldarah);
        data.putString("gender",gender);
        data.putString("uri",uri);
//        signupPasswordFragment.setArguments(data);
//        ft.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right);
//        ft.replace(R.id.signup_fragment,signupPasswordFragment);
//        ft.addToBackStack(null);
//        ft.commit();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }



}