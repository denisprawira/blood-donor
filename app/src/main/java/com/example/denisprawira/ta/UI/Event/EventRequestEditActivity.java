package com.example.denisprawira.ta.UI.Event;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.denisprawira.ta.Adapter.UtdAdapter;
import com.example.denisprawira.ta.Helper.GlobalHelper;
import com.example.denisprawira.ta.Helper.RetrofitHelper;
import com.example.denisprawira.ta.Api.RetrofitServices;
import com.example.denisprawira.ta.Model.ServerResponse;
import com.example.denisprawira.ta.Model.Utd;
import com.example.denisprawira.ta.R;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRequestEditActivity extends AppCompatActivity implements View.OnClickListener {

    String idEvent,idUser, idUtd,userName,userPhoto,userTelp,userToken, utdName, utdPhoto, utdTelp, utdToken,title,description,instansi,target,lat,lng,address,document,date,startTime,endTime,status;
    EditText eventRequestEditUtdName,eventRequestEditTitle,eventRequestEditDescription,eventRequestEditInstansi,eventRequestEditTarget,eventRequestEditDate,eventRequestEditTime,eventRequestEditLocation,eventRequestEditDocument;
    ImageButton backEditEvent,eventRequestEditDocumentDetail;
    Button eventRequestEditAction;

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    Uri selectedFileUri;
    File attachment;

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int REQUEST_FILE_PICKER= 2;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String pmi, pmiToken;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);


    //PMI
    RecyclerView recyclerview_pmi;
    Dialog dialog;

    List<Utd> pmiList = new ArrayList<>();
    UtdAdapter pmiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_requst_edit);


        backEditEvent                   = findViewById(R.id.backEditEvent);
        eventRequestEditDocumentDetail  = findViewById(R.id.eventRequestEditDocumentDetail);
        eventRequestEditUtdName         = findViewById(R.id.eventRequestEditUtdName);
        eventRequestEditTitle           = findViewById(R.id.eventRequestEditTitle);
        eventRequestEditDescription     = findViewById(R.id.eventAddPmi);
        eventRequestEditInstansi        = findViewById(R.id.eventRequestEditInstansi);
        eventRequestEditTarget          = findViewById(R.id.eventRequestEditTarget);
        eventRequestEditDate            = findViewById(R.id.eventRequestEditDate);
        eventRequestEditTime            = findViewById(R.id.eventRequestEditTime);
        eventRequestEditLocation        = findViewById(R.id.eventRequestEditLocation);
        eventRequestEditDocument        = findViewById(R.id.eventAddDate);
        eventRequestEditAction          = findViewById(R.id.eventRequestEditAction);


        idEvent     = getIntent().getExtras().get("idEvent").toString();
        idUser      = getIntent().getExtras().get("idUser").toString();
        idUtd       = getIntent().getExtras().get("idUtd").toString();
        userName    = getIntent().getExtras().get("userName").toString();
        userPhoto   = getIntent().getExtras().get("userPhoto").toString();
        userTelp    = getIntent().getExtras().get("userTelp").toString();
        userToken   = getIntent().getExtras().get("userToken").toString();
        utdName     = getIntent().getExtras().get("utdName").toString();
        utdPhoto    = getIntent().getExtras().get("utdPhoto").toString();
        utdTelp     = getIntent().getExtras().get("utdTelp").toString();
        utdToken    = getIntent().getExtras().get("utdToken").toString();
        title       = getIntent().getExtras().get("title").toString();
        description = getIntent().getExtras().get("description").toString();
        instansi    = getIntent().getExtras().get("instansi").toString();
        target      = getIntent().getExtras().get("target").toString();
        lat         = getIntent().getExtras().get("lat").toString();
        lng         = getIntent().getExtras().get("lng").toString();
        address     = getIntent().getExtras().get("address").toString();
        document    = getIntent().getExtras().get("document").toString();
        date        = getIntent().getExtras().get("date").toString();
        startTime   = getIntent().getExtras().get("startTime").toString();
        endTime     = getIntent().getExtras().get("endTime").toString();


        eventRequestEditUtdName.setText(utdName);
        eventRequestEditTitle.setText(title);
        eventRequestEditDescription.setText(description);
        eventRequestEditInstansi.setText(instansi);
        eventRequestEditTarget.setText(target);
        eventRequestEditDate.setText(GlobalHelper.convertDate(date));
        eventRequestEditTime.setText(GlobalHelper.convertTime(startTime)+" - "+GlobalHelper.convertTime(endTime));
        eventRequestEditLocation.setText(address);
        eventRequestEditDocument.setText(document);

        backEditEvent.setOnClickListener(this);
        eventRequestEditUtdName.setOnClickListener(this);
        eventRequestEditDocumentDetail.setOnClickListener(this);
        eventRequestEditDate.setOnClickListener(this);
        eventRequestEditTime.setOnClickListener(this);
        eventRequestEditLocation.setOnClickListener(this);
        eventRequestEditDocument.setOnClickListener(this);
        eventRequestEditAction.setOnClickListener(this);

        loadPmi();

    }

    @Override
    public void onClick(View v) {

//        if(v==eventRequestEditDate){
//            showDatePickerDialog();
//        }else if(v==eventRequestEditLocation){
//            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//            try {
//                startActivityForResult(builder.build(EventRequestEditActivity.this), PLACE_PICKER_REQUEST);
//            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//                e.printStackTrace();
//            }
//        }else if(v==eventRequestEditDocumentDetail){
//            Intent intent = new Intent(EventRequestEditActivity.this, PhotoViewerActivity.class);
//            intent.putExtra("document",document);
//            startActivity(intent);
//        }else if(v==backEditEvent){
//            onBackPressed();
//        }else if(v==eventRequestEditTime){
//            Calendar now = Calendar.getInstance();
//            TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
//                    startTime   = hourOfDay+":"+minute;
//                    endTime     = hourOfDayEnd+":"+minuteEnd;
//                    eventRequestEditTime.setText(startTime+" - "+endTime);                }
//            },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE), false);
//            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialogInterface) {
//                    Log.d("TimePicker", "Dialog was cancelled");
//                }
//            });
//            tpd.show(getFragmentManager(), "Timepickerdialog");
//        }else if(v==eventRequestEditAction){
//
////            title       = eventRequestEditTitle.getText().toString();
////            description = eventRequestEditDescription.getText().toString();
////            instansi    = eventRequestEditInstansi.getText().toString();
////            target      = eventRequestEditTarget.getText().toString();
////
////            if(attachment==null){
////                Toast.makeText(this, "file null", Toast.LENGTH_SHORT).show();
////            }else{
////                Toast.makeText(this, "file not null", Toast.LENGTH_SHORT).show();
////            }
//            //updateEvent(idEvent,idUtd, title, description,instansi, target, date, startTime, endTime,address, lat, lng, attachment);
//
//        }else if(v==eventRequestEditDocument){
//            requestPermission();
//        }else if(v==eventRequestEditUtdName){
//            Toast.makeText(this, "akal sehat ku meronta - ronta", Toast.LENGTH_SHORT).show();
//            dialog.show();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_FILE_PICKER) {
//            if (resultCode == RESULT_OK) {
//                selectedFileUri = data.getData();
//                eventRequestEditDocument.setText(getFileName(getContentResolver(),selectedFileUri));
//                attachment  = new File(getRealPathFromURI(selectedFileUri));
//            }
//        }else if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, EventRequestEditActivity.this);
//                address  = place.getAddress().toString();
//                lat      = Double.toString(place.getLatLng().latitude);
//                lng      = Double.toString(place.getLatLng().longitude);
//                eventRequestEditLocation.setText(place.getAddress());
//            }
//        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

////         if(resultCode == REQUEST_FILE_PICKER) {
////            if (resultCode == RESULT_OK) {
////                selectedFileUri = data.getData();
////                //eventRequestEditDocument.setText(getFileName(EventRequestEditActivity.this.getContentResolver(),selectedFileUri));
////                eventRequestEditDocument.setText(String.valueOf(selectedFileUri));
////                attachment  = new File(getRealPathFromURI(selectedFileUri));
////                Toast.makeText(this, "file: "+attachment, Toast.LENGTH_SHORT).show();
////            }
//
//
//
//        }
    //}

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(EventRequestEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthReal = month+1;
                date = year+"-"+monthReal+"-"+dayOfMonth;
                eventRequestEditDate.setText(dayOfMonth+"/"+monthReal+"/"+year);
            }
        },year,month,day);

        datePickerDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fileChooser();//do your job
        }
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            fileChooser();//do your job
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = EventRequestEditActivity.this.getContentResolver().query(contentURI, null, null, null, null);
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

    private String getFileName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    ///for file picker
    public void fileChooser(){
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("image/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),REQUEST_FILE_PICKER);
    }

    public void loadPmi(){

        dialog = new Dialog(EventRequestEditActivity.this);
        dialog.setContentView(R.layout.layout_dialog_listutd);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
        recyclerview_pmi = dialog.findViewById(R.id.recyclerview_utd);
        recyclerview_pmi.setHasFixedSize(true);
        recyclerview_pmi.setLayoutManager(new LinearLayoutManager(EventRequestEditActivity.this));

        retrofitServices.loadUtd().enqueue(new Callback<List<Utd>>() {
            @Override
            public void onResponse(Call<List<Utd>> call, Response<List<Utd>> response) {
                List<Utd> pmi = response.body();
                pmiList.addAll(pmi);
            }
            @Override
            public void onFailure(Call<List<Utd>> call, Throwable t) {
                Toast.makeText(EventRequestEditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



//        pmiAdapter = new UtdAdapter(EventRequestEditActivity.this,pmiList, new UtdAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Utd item, int position) {
//                idUtd = item.getId();
//                dialog.dismiss();
//                eventRequestEditUtdName.setText(item.getNama());
//            }
//        });
        recyclerview_pmi.setAdapter(pmiAdapter);
    }


    public void updateEvent(String idEvent, String idUtd,String title, String description,String instansi, String target, String date, String startTime, String endTime, String address, String lat, String lng,File attachment){
        RequestBody param1 =  RequestBody.create(MultipartBody.FORM, idEvent);
        RequestBody param2 =  RequestBody.create(MultipartBody.FORM,idUtd);
        RequestBody param3 =  RequestBody.create(MultipartBody.FORM,title);
        RequestBody param4 =  RequestBody.create(MultipartBody.FORM,description);
        RequestBody param5 =  RequestBody.create(MultipartBody.FORM,instansi);
        RequestBody param6 =  RequestBody.create(MultipartBody.FORM,target);
        RequestBody param7 =  RequestBody.create(MultipartBody.FORM,date);
        RequestBody param8 =  RequestBody.create(MultipartBody.FORM,startTime);
        RequestBody param9 =  RequestBody.create(MultipartBody.FORM,endTime);
        RequestBody param10 =  RequestBody.create(MultipartBody.FORM,address);
        RequestBody param11 =  RequestBody.create(MultipartBody.FORM,lat);
        RequestBody param12 =  RequestBody.create(MultipartBody.FORM,lng);
        RequestBody file = RequestBody.create(MediaType.parse("*/*"), attachment);
        MultipartBody.Part param13 = MultipartBody.Part.createFormData("file",attachment.getName(),file);

        retrofitServices.updateEvent(param1, param2,param3, param4,param5,param6,param7,param8,param9,param10,param11,param12,param13).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                Toast.makeText(EventRequestEditActivity.this, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });




    }



}
