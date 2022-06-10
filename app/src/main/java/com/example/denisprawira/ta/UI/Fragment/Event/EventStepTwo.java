package com.example.denisprawira.ta.UI.Fragment.Event;
import android.Manifest;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
//import com.mapbox.mapboxsdk.places.R;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.denisprawira.ta.Helper.RealPathUtil;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.Api.Model.Response;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Helper.FileHelper;
import com.example.denisprawira.ta.Helper.SessionManager;
import com.example.denisprawira.ta.UI.ActivityResult.PmiActivityResult;
import com.example.denisprawira.ta.Values.GlobalValues;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;


import java.io.File;
import java.util.Calendar;
import java.util.Locale;


import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class EventStepTwo extends Fragment implements View.OnClickListener{

    //CLASS INSTANCE AND OBJECT DECLARATION
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    Event event;
    Uri selectedFileUri;
    File attachment;
    UserService userService = ApiUtils.getUserService();

    ActivityResultLauncher<Intent> activityFileResultLauncher ;
    ActivityResultLauncher<Intent> activityLocationResultLauncher;
    ActivityResultLauncher<Intent> activityPmiResultLauncher;
    SessionManager sessionManager;

    //VIEW COMPONENT
    EditText eventAddPmi,eventAddDate,eventAddTime,eventAddFile,eventAddLocation;
    Button eventAddSubmit;
    LinearLayout eventAddLocationContainerIcon ;

    //DATA TYPE DECLARATION
    private static final int PLACE_PICKER_REQUEST = 1;
    int TIME_START=0;
    int TIME_END = 1;
    String pmi,date,dateStart,img,address,lat,lng;
    String partImage;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public EventStepTwo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(),getString(R.string.mapbox_access_token));
        View view= inflater.inflate(R.layout.fragment_event_step_two, container, false);

        if(!getArguments().isEmpty()){
            event = getArguments().getParcelable("event");
        }else{
            Toast.makeText(getActivity(), "event is null", Toast.LENGTH_SHORT).show();
        }

        eventAddPmi         = view.findViewById(R.id.eventAddPmi);
        eventAddTime        = view.findViewById(R.id.eventAddTime);
        eventAddDate        = view.findViewById(R.id.eventAddDate);
        eventAddFile        = view.findViewById(R.id.eventAddFile);
        eventAddLocation    = view.findViewById(R.id.eventAddLocation);
        eventAddSubmit      = view.findViewById(R.id.eventAddSubmit);
        eventAddLocationContainerIcon  = view.findViewById(R.id.eventAddLocationContainerIcon);

        eventAddPmi.setClickable(true);
        eventAddDate.setClickable(true);
        eventAddTime.setClickable(true);
        eventAddFile.setClickable(true);
        eventAddLocation.setClickable(true);

        eventAddPmi.setFocusable(false);
        eventAddDate.setFocusable(false);
        eventAddTime.setFocusable(false);
        eventAddFile.setFocusable(false);
       // eventAddLocation.setFocusable(false);

        eventAddPmi.setOnClickListener(this);
        eventAddDate.setOnClickListener(this);
        eventAddTime.setOnClickListener(this);
        eventAddFile.setOnClickListener(this);
        eventAddSubmit.setOnClickListener(this);
        eventAddLocation.setOnClickListener(this);
        if(!eventAddLocation.getText().toString().isEmpty()){
            eventAddLocationContainerIcon.setVisibility(View.GONE);
        }
        eventAddLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int count, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int count, int i2) {
                Toast.makeText(getContext(), "this is sparta", Toast.LENGTH_SHORT).show();
                if(charSequence.length()>0){
                    eventAddLocationContainerIcon.setVisibility(View.GONE);
                }else{
                    eventAddLocationContainerIcon.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        sessionManager = new SessionManager(getActivity());

        onLocationResult();
        onFileResult();

        activityPmiResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK){
                    Pmi pmi = result.getData().getParcelableExtra(GlobalValues.PMI_INTENT);
                    String pmiName = pmi.getName();
                    String pmiId = pmi.getId();
                    event.setIdPmi(pmiId);
                    eventAddPmi.setText(pmiName);
                }
            }
        });



        return view;
    }

    public void placePicker(){
        Intent intent =   new PlacePicker.IntentBuilder()
                .accessToken(getString(R.string.mapbox_access_token))
                .placeOptions(PlacePickerOptions.builder()
                    .statingCameraPosition(new CameraPosition.Builder()
                        .target(new LatLng(-8.588641, 115.297787)).zoom(16).build())
                            .build())
                .build(getActivity());
        activityLocationResultLauncher.launch(intent);
    }

    public void filePicker(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(
                    getActivity(),
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
        activityFileResultLauncher.launch(Intent.createChooser(intent,"open Galery"));
    }

    public void onLocationResult(){
        activityLocationResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                CarmenFeature carmenFeature = PlacePicker.getPlace(result.getData());
                if (carmenFeature != null) {
                    Geometry point = carmenFeature.geometry();
                    double latitude = Point.fromJson(point.toJson()).latitude();
                    double longitude = Point.fromJson(point.toJson()).longitude();
                    eventAddLocation.setText(carmenFeature.placeName());
                    address = carmenFeature.placeName();
                    lat  = String.valueOf(latitude);
                    lng = String.valueOf(longitude);
                    Log.e("carmen",String.valueOf(carmenFeature.placeName()));
                }
            }
        });
    };

    public void onFileResult(){
        activityFileResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK && result.getData()!=null){
                    selectedFileUri = result.getData().getData();
                    String[] imageProjection ={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedFileUri,imageProjection,null,null,null);
                    eventAddFile.setText(FileHelper.getFileName(getActivity().getContentResolver(),selectedFileUri));
                    event.setImg(eventAddFile.getText().toString());
                    Context context = getContext().getApplicationContext();
                    String path = RealPathUtil.INSTANCE.getRealPath(context,selectedFileUri);
                    attachment = new File(path);
                    Log.e("result uri",selectedFileUri.toString());
                    Log.e("result path",path);
                    Log.e("result file",attachment.toString());
//                    if(cursor!=null){
//                        cursor.moveToFirst();
//                        int indexImg = cursor.getColumnIndex(imageProjection[0]);
//                        partImage = cursor.getString(indexImg);
//                        if(partImage!=null){
//                            Log.e("result",partImage);
//                            Log.e("result getpath",selectedFileUri.getPath());
//                            attachment = new File(partImage);
//                            Log.e("result",attachment.toString());
//                        }else{
//                            Context context = getContext().getApplicationContext();
//                            String path = RealPathUtil.INSTANCE.getRealPath(context,selectedFileUri);
//                            attachment = new File(path);
//                            Log.e("result uri",selectedFileUri.toString());
//                            Log.e("result path",path);
//                            Log.e("result file",attachment.toString());
//                        }
//
//
//                    }
                }
               // Toast.makeText(getActivity(), "file: "+attachment, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v == eventAddPmi){
            Intent intent = new Intent(getActivity(), PmiActivityResult.class);
            activityPmiResultLauncher.launch(intent);
        }else if(v==eventAddLocation){
            placePicker();
        }else if(v==eventAddDate){
            showDatePickerDialog();
        }else if(v==eventAddTime){
            showTimePickerDialog(TIME_START);
        }else if(v==eventAddFile){
            filePicker();
        }else if(v==eventAddSubmit){
            dateStart = date+" "+dateStart;
            event.setId(sessionManager.getId());
            event.setDateStart(dateStart);
            event.setAddress(address);
            event.setLat(lat);
            event.setLng(lng);
            event.setStatus("pending");
            addEvent();
//            Log.e("event user id",event.getId());
//            Log.e("event title",event.getTitle());
//            Log.e("event instansi",event.getInstitution());
//            Log.e("event description",event.getDescription());
//            Log.e("event amount",event.getAmount());
//            Log.e("event pmi",event.getIdPmi());
//            Log.e("event date start",event.getDateStart());
//            Log.e("event file name",event.getImg());
//            Log.e("event location address",event.getAddress());
//            Log.e("event  lat",event.getLat());
//            Log.e("event  lng",event.getLng());

        }
    }

    public void addEvent(){
        RequestBody eventUserId =  RequestBody.create(MultipartBody.FORM,event.getId());
        RequestBody eventPmiId =  RequestBody.create(MultipartBody.FORM,event.getIdPmi());
        RequestBody eventTitle =  RequestBody.create(MultipartBody.FORM,event.getTitle());
        RequestBody eventInstitution =  RequestBody.create(MultipartBody.FORM,event.getInstitution());
        RequestBody eventDescription =  RequestBody.create(MultipartBody.FORM,event.getDescription());
        RequestBody eventAmount =  RequestBody.create(MultipartBody.FORM,event.getTarget());
        RequestBody eventDateStart =  RequestBody.create(MultipartBody.FORM,event.getDateStart());
        RequestBody eventAddress =  RequestBody.create(MultipartBody.FORM,event.getAddress());
        RequestBody eventLat =  RequestBody.create(MultipartBody.FORM,event.getLat());
        RequestBody eventLng =  RequestBody.create(MultipartBody.FORM,event.getLng());
        RequestBody eventStatus =  RequestBody.create(MultipartBody.FORM,event.getStatus());

        if(attachment!=null){
            RequestBody file = RequestBody.create(MediaType.parse("multipart/form-file"), attachment);
            MultipartBody.Part eventFile = MultipartBody.Part.createFormData("file",attachment.getName(),file);

            userService.storeEvent(eventUserId,eventPmiId,eventTitle,eventInstitution,eventDescription,eventAmount,eventDateStart,eventAddress,eventLat,eventLng,eventStatus,eventFile).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if(response!=null){
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            });
        }else{
            userService.storeEvent(eventUserId,eventPmiId,eventTitle,eventInstitution,eventDescription,eventAmount,eventDateStart,eventAddress,eventLat,eventLng,eventStatus).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if(response!=null){
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            });
        }
    }

    public void showTimePickerDialog(final int code){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(code==TIME_START){

                    int  lengthMinute = (int)(Math.log10(minute)+1);
                    int  lengthHour = (int)(Math.log10(hourOfDay)+1);
                    if(lengthMinute<2&&lengthHour<2){
                        eventAddTime.setText("0"+hourOfDay+":0"+minute);
                    }else if(lengthHour<2){
                        eventAddTime.setText("0"+hourOfDay+":"+minute);
                    }else if(lengthMinute<2){
                        eventAddTime.setText(hourOfDay+":0"+minute);
                    }else{
                        eventAddTime.setText(hourOfDay+":"+minute);
                    }
                    dateStart = eventAddTime.getText().toString();
                }else if(code==TIME_END){
                    //endTime     = hourOfDay+":"+minute;

                }

            }
        },hour,minute,true);

        timePickerDialog.show();
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthReal = month+1;
                date = year+"-"+monthReal+"-"+dayOfMonth;
                String dateTime = dayOfMonth+"/"+monthReal+"/"+year;
                eventAddDate.setText(dateTime);
            }
        },year,month,day);
        datePickerDialog.show();
    }
}