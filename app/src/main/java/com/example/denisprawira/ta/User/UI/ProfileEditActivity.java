package com.example.denisprawira.ta.User.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.GolDarah;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editProfileUserName, editProfilePhone, editProfileEmail,editProfileGolDarah,editProfileGender;
    ImageView editProfileImage;
    Button editProfileAction;
    ImageButton editProfileImageChoose;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    SessionManager sessionManager;

    private Uri resultUri;
    File attachment;

    Uri selectedFileUri;


    String idUser ;

    String[] golDarah  = new String[9];
    String[] golDarahVar  = new String[9];
    String[] gender = {"Laki-laki", "Perempuan"};
    String profileUserName,profilePhone, profileEmail,profileGolDarah,profileGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sessionManager  = new SessionManager(ProfileEditActivity.this);
        idUser = sessionManager.getId();
        editProfileImageChoose  = findViewById(R.id.editProfileImageChoose);
        editProfileUserName     = findViewById(R.id.editProfileUserName);
        editProfilePhone        = findViewById(R.id.editProfilePhone);
        editProfileEmail        = findViewById(R.id.editProfileEmail);
        editProfileGolDarah     = findViewById(R.id.editProfileGolDarah);
        editProfileGender       = findViewById(R.id.editProfileGender);
        editProfileImage        = findViewById(R.id.editProfileImage);
        editProfileAction       = findViewById(R.id.editProfileAction);

        editProfileImageChoose.setOnClickListener(this);
        editProfileGender.setOnClickListener(this);
        editProfileGolDarah.setOnClickListener(this);
        editProfileAction.setOnClickListener(this);

        editProfileUserName.setText(sessionManager.getName());
        editProfilePhone.setText(sessionManager.getPhone());
        editProfileEmail.setText(sessionManager.getEmail());

        if(sessionManager.getBlood().equals("1")){
            editProfileGolDarah.setText("AB+");
        }else if(sessionManager.getBlood().equals("2")){
            editProfileGolDarah.setText("AB-");
        }else if(sessionManager.getBlood().equals("3")){
            editProfileGolDarah.setText("A+");
        }else if(sessionManager.getBlood().equals("4")){
            editProfileGolDarah.setText("A-");
        }else if(sessionManager.getBlood().equals("5")){
            editProfileGolDarah.setText("B+");
        }else if(sessionManager.getBlood().equals("6")){
            editProfileGolDarah.setText("B-");
        }else if(sessionManager.getBlood().equals("7")){
            editProfileGolDarah.setText("O+");
        }else if(sessionManager.getBlood().equals("8")){
            editProfileGolDarah.setText("O-");
        }else if(sessionManager.getBlood().equals("9")){
            editProfileGolDarah.setText("Tidak Tahu");
        }


        profileGolDarah = sessionManager.getBlood();

        editProfileGender.setText(sessionManager.getGender());

        Glide.with(ProfileEditActivity.this).load(sessionManager.getPhoto()).into(editProfileImage);;

        loadGolDarah();


    }
    
    public void loadGolDarah(){
        retrofitServices.loadGolDarah().enqueue(new Callback<List<GolDarah>>() {
            @Override
            public void onResponse(Call<List<GolDarah>> call, Response<List<GolDarah>> response) {
                List<GolDarah> listGolDarah = response.body();
                for(int i=0;i<listGolDarah.size();i++){
                    golDarah[i] = listGolDarah.get(i).getGolDarah();
                    golDarahVar[i] = listGolDarah.get(i).getId();
                }

            }

            @Override
            public void onFailure(Call<List<GolDarah>> call, Throwable t) {
                Toast.makeText(ProfileEditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void buildDialogGolDarah(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,android.R.layout.select_dialog_item,golDarah);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfileEditActivity.this);
        builder2.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileGolDarah = golDarahVar[which];
                editProfileGolDarah.setText(golDarah[which]);
            }
        });
        builder2.show();
    }

    public void buildDialogGender(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,android.R.layout.select_dialog_item,gender);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfileEditActivity.this);
        builder2.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileGender =  gender[which];
                editProfileGender.setText(gender[which]);
            }
        });
        builder2.show();
    }


    @Override
    public void onClick(View v) {
        if(v==editProfileGolDarah){
            buildDialogGolDarah();
        }else if(v==editProfileGender){
            buildDialogGender();
        }else if(v==editProfileAction){
            profileUserName = editProfileUserName.getText().toString();
            profilePhone    = editProfilePhone.getText().toString();
            profileEmail    = editProfileEmail.getText().toString();
            profileGender   = editProfileGender.getText().toString();

            if(resultUri==null){
                updateProfileNoImage(idUser,profileUserName,profileEmail,profilePhone,profileGolDarah,profileGender);
            }else{
                updateProfile(idUser,profileUserName,profileEmail,profilePhone,profileGolDarah,profileGender, resultUri.toString());
            }

        }else if(v==editProfileImageChoose){
            profileUserName = editProfileUserName.getText().toString();
            profilePhone    = editProfilePhone.getText().toString();
            profileEmail    = editProfileEmail.getText().toString();
            profileGender   = editProfileGender.getText().toString();

            //            Toast.makeText(this, "nama: "+sessionmanager.getName(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "nama: "+sessionmanager.getGender(), Toast.LENGTH_SHORT).show();
//            sessionmanager.setName(profileUserName);
//            sessionmanager.setGender(profileGender);
//            Toast.makeText(this, "after nama: "+sessionmanager.getName(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "after nama: "+sessionmanager.getGender(), Toast.LENGTH_SHORT).show();
            CropImage.activity().setAspectRatio(1,1).setCropShape(CropImageView.CropShape.RECTANGLE).start(ProfileEditActivity.this);
        }
    }

    public void updateProfile(String idUser, final String userName, final String email, final String phone, final String golDarah, final String gender, String uri){
        File photo = new File(getRealPathFromURI(Uri.parse(uri)));
        RequestBody param1 =  RequestBody.create(MultipartBody.FORM,idUser);
        RequestBody param2 =  RequestBody.create(MultipartBody.FORM,userName);
        RequestBody param3 =  RequestBody.create(MultipartBody.FORM,phone);
        RequestBody param4 =  RequestBody.create(MultipartBody.FORM,email);
        RequestBody param5 =  RequestBody.create(MultipartBody.FORM,golDarah);
        RequestBody param6 =  RequestBody.create(MultipartBody.FORM,gender);
        RequestBody file   =  RequestBody.create(MediaType.parse("*/*"), photo);

        MultipartBody.Part param7 = MultipartBody.Part.createFormData("file",photo.getName(),file);
        retrofitServices.updateProfile(param1,param2,param3,param4,param5,param6,param7).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(ProfileEditActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                sessionManager.setName(userName);
                sessionManager.setPhone(phone);
                sessionManager.setBlood(golDarah);
                sessionManager.setGender(gender);
                sessionManager.setEmail(email);
                sessionManager.setPhoto(response.body().getMessage());

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ProfileEditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProfileNoImage(String idUser, final String userName, final String email, final String phone, final String golDarah, final String gender){
        retrofitServices.updateProfile2(idUser,userName,phone,email,golDarah,gender).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(ProfileEditActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                sessionManager.setName(userName);
                sessionManager.setPhone(phone);
                sessionManager.setBlood(golDarah);
                sessionManager.setGender(gender);
                sessionManager.setEmail(email);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ProfileEditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Toast.makeText(this, "result uri: "+resultUri.toString(), Toast.LENGTH_SHORT).show();
                Glide.with(ProfileEditActivity.this).load(resultUri.toString()).into(editProfileImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
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
