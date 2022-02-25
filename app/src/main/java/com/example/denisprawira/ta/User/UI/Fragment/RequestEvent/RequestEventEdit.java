package com.example.denisprawira.ta.User.UI.Fragment.RequestEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestEventEdit extends Fragment implements View.OnClickListener{


    String idEvent,idPmi,pmiName,pmiPhoto,pmiTelp,pmiToken,title,description,instansi,lat,lng,address,document,date,startTime,endTime,target,postTime,status;
    ImageButton backRequestEventEdit;
    Button saveEditEvent;
    EditText eventEditTitle,eventEditDescription,eventEditInstansi,eventEditTarget,eventEditDate,eventEditStartTime,eventEditEndTime;

    String editedTitle,editedDescription,editedInstansi,editedTarget,editedDate,editedStartTime,editedEndTime;

    int TIME_START=0;
    int TIME_END = 1;
    private static final int PLACE_PICKER_REQUEST = 1;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);


    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    public RequestEventEdit() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_request_event_edit, container, false);

        backRequestEventEdit  = view.findViewById(R.id.backRequestEventEdit);

        eventEditTitle              = view.findViewById(R.id.eventEditTitle);
        eventEditDescription        = view.findViewById(R.id.eventEditDescription);
        eventEditInstansi           = view.findViewById(R.id.eventEditInstansi);
        eventEditTarget             = view.findViewById(R.id.eventEditTarget);
        eventEditDate               = view.findViewById(R.id.eventEditDate);
        eventEditStartTime          = view.findViewById(R.id.eventEditStartTime);
        eventEditEndTime            = view.findViewById(R.id.eventEditEndTime);
        saveEditEvent               = view.findViewById(R.id.saveEditEvent);



        idEvent     = getArguments().getString("idEvent");
        idPmi       = getArguments().getString("idPmi");
        pmiName     = getArguments().getString("pmiName");
        pmiPhoto    = getArguments().getString("pmiPhoto");
        pmiTelp     = getArguments().getString("pmiTelp");
        pmiToken    = getArguments().getString("pmiToken");
        title       = getArguments().getString("title");
        description = getArguments().getString("description");
        instansi    = getArguments().getString("instansi");
        lat         = getArguments().getString("lat");
        lng         = getArguments().getString("lng");
        address     = getArguments().getString("address");
        document    = getArguments().getString("document");
        date        = getArguments().getString("date");
        startTime   = getArguments().getString("startTime");
        endTime     = getArguments().getString("endTime");
        target      = getArguments().getString("target");
        postTime    = getArguments().getString("postTime");
        status      = getArguments().getString("status");

        eventEditTitle.setText(title);
        eventEditDescription.setText(description);
        eventEditInstansi.setText(instansi);
        eventEditTarget.setText(target);
        eventEditDate.setText(date);
        eventEditStartTime.setText(startTime);
        eventEditEndTime.setText(endTime);


        backRequestEventEdit.setOnClickListener(this);
        saveEditEvent.setOnClickListener(this);
        eventEditDate.setOnClickListener(this);
        eventEditStartTime.setOnClickListener(this);
        eventEditEndTime.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v==backRequestEventEdit){
            getActivity().onBackPressed();
        }else if(v==saveEditEvent){
            editedTitle         = eventEditTitle.getText().toString();
            editedDescription   = eventEditDescription.getText().toString();
            editedInstansi      = eventEditInstansi.getText().toString();
            editedTarget        = eventEditTarget.getText().toString();
            editedDate          = eventEditDate.getText().toString();
            editedStartTime     = eventEditStartTime.getText().toString();
            editedEndTime       = eventEditEndTime.getText().toString();


            saveEditEvent(idEvent,editedTitle,editedDescription,editedInstansi,editedTarget,date,startTime,endTime);
        }else if(v==eventEditDate){
            showDatePickerDialog();
        }else if(v==eventEditStartTime){
            showTimePickerDialog(TIME_START);
        }else if(v==eventEditEndTime){
            showTimePickerDialog(TIME_END);
        }
    }

    public void saveEditEvent(String idEvent, String editedTitle,String editedDescription,String editedInstansi,String editedTarget,String editedDate,String editedStartTime,String editedEndTime){
        retrofitServices.editApprovedEvent(idEvent,editedTitle,editedDescription,editedInstansi,editedTarget,editedDate,editedStartTime,editedEndTime).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void showTimePickerDialog(final int code){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(code==TIME_START){
                    startTime   = hourOfDay+":"+minute;
                    eventEditStartTime.setText(startTime);
                }else if(code==TIME_END){
                    endTime     = hourOfDay+":"+minute;
                    eventEditEndTime.setText(endTime);
                }

            }
        },hour,minute,true);

        timePickerDialog.show();
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = year+"-"+month+"-"+dayOfMonth;
                eventEditDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);

        datePickerDialog.show();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, getActivity());
//                address  = place.getAddress().toString();
//                lat      = Double.toString(place.getLatLng().latitude);
//                lng      = Double.toString(place.getLatLng().longitude);
//                eventLocation.setText(place.getAddress());
//            }
//        }
//    }
//




}