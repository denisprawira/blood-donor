package com.example.denisprawira.ta.UI.HelpRequest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.HelpRequest;
import com.example.denisprawira.ta.Api.Model.Response;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Helper.FileHelper;
import com.example.denisprawira.ta.SessionManager.UserSessionManager;
import com.example.denisprawira.ta.UI.ActivityResult.BloodResultActivity;
import com.example.denisprawira.ta.UI.HomeActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RequestDonorDetail extends AppCompatActivity implements View.OnClickListener {

    EditText findDonorAddPatientName;
    EditText findDonorAddPatientBlood;
    EditText findDonorAddDescription;
    EditText findDonorAddAmount;
    EditText findDonorAddFile;
    ActivityResultLauncher<Intent> activityFileResultLauncher ;

    Button findDonorSubmit;

    UserService userService = ApiUtils.getUserService();
    UserSessionManager userSessionManager;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    Uri selectedFileUri;
    File attachment;

    HelpRequest helpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_donor_detail);
        findDonorAddPatientName = findViewById(R.id.findDonorAddPatientName);
        findDonorAddPatientBlood = findViewById(R.id.findDonorAddPatientBlood);
        findDonorAddDescription = findViewById(R.id.findDonorAddDescription);
        findDonorAddFile = findViewById(R.id.findDonorAddFile);
        findDonorAddAmount = findViewById(R.id.findDonorAddAmount);
        findDonorSubmit = findViewById(R.id.findDonorSubmit);
        findDonorAddFile.setOnClickListener(this);
        findDonorSubmit.setOnClickListener(this);
        findDonorAddPatientBlood.setOnClickListener(this);
        findDonorAddPatientBlood.setFocusable(false);

        findDonorAddFile.setFocusable(false);
        userSessionManager =  new UserSessionManager(this);
        helpRequest = new HelpRequest();
        onFileResult();
        if(getIntent().getStringExtra("idPmi")!=null){
            helpRequest.setIdPmi(getIntent().getStringExtra("idPmi"));
            Toast.makeText(RequestDonorDetail.this, "pmi id : "+helpRequest.getIdPmi(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onFileResult(){
        activityFileResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK && result.getData()!=null){
                    selectedFileUri = result.getData().getData();
                    findDonorAddFile.setText(FileHelper.getFileName(getContentResolver(),selectedFileUri));
                    helpRequest.setImg(findDonorAddFile.getText().toString());
                    attachment  = new File(FileHelper.getRealPathFromURI(selectedFileUri,RequestDonorDetail.this));
                }
                // Toast.makeText(getActivity(), "file: "+attachment, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==findDonorAddFile){
            filePicker();
        }else if(view==findDonorSubmit){
            helpRequest.setIdUser(userSessionManager.getId());
            helpRequest.setPatientName(findDonorAddPatientName.getText().toString());
            helpRequest.setBlood(findDonorAddPatientBlood.getText().toString());
            helpRequest.setDescription(findDonorAddDescription.getText().toString());
            helpRequest.setTarget(findDonorAddAmount.getText().toString());
            helpRequest.setStatus("pending");
            submitHelp(helpRequest.getIdUser(),helpRequest.getIdPmi(),helpRequest.getBlood(),helpRequest.getDescription(),helpRequest.getPatientName(),helpRequest.getTarget(),helpRequest.getStatus());
        }else if(view==findDonorAddPatientBlood){
            Intent intent = new Intent(RequestDonorDetail.this,BloodResultActivity.class);
            startActivity(intent);
        }
    }

    public void filePicker(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            Intent intent = new Intent();
            //sets the select file to all types of files
            intent.setType("image/*");
            //allows to select data and return it
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityFileResultLauncher.launch(intent);
        }
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("image/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityFileResultLauncher.launch(intent);
    }

    private void submitHelp(String userId,String pmiId,String bloodId,String description,String patientName,String amount,String status){
        RequestBody helpUserId =  RequestBody.create(MultipartBody.FORM,userId);
        RequestBody helpPmiId =  RequestBody.create(MultipartBody.FORM,pmiId);
        RequestBody helpBloodId =  RequestBody.create(MultipartBody.FORM,bloodId);
        RequestBody helpDescription =  RequestBody.create(MultipartBody.FORM,description);
        RequestBody helpPatientName =  RequestBody.create(MultipartBody.FORM,patientName);
        RequestBody helpAmount =  RequestBody.create(MultipartBody.FORM,amount);
        RequestBody helpStatus =  RequestBody.create(MultipartBody.FORM,status);
        if(attachment!=null){
            RequestBody file = RequestBody.create(MediaType.parse("*/*"), attachment);
            MultipartBody.Part helpFile = MultipartBody.Part.createFormData("file",attachment.getName(),file);
            userService.storeHelprequest(helpUserId,helpPmiId,helpBloodId,helpDescription,helpPatientName,helpAmount,helpStatus,helpFile).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if(response.body().getStatus()!=null){
                        Toast.makeText(RequestDonorDetail.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(RequestDonorDetail.this, HomeActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("help",t.getMessage());
                }
            });
        }else{
            userService.storeHelprequest(helpUserId,helpPmiId,helpBloodId,helpDescription,helpPatientName,helpAmount,helpStatus).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if(response.body().getStatus()!=null){
                        Toast.makeText(RequestDonorDetail.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(RequestDonorDetail.this, HomeActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("help",t.getMessage());
                }
            });
        }
    }


}