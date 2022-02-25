package com.example.denisprawira.ta.User.UI.Fragment.Event;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.denisprawira.ta.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


import java.util.Calendar;


import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class EventStepTwo extends Fragment implements View.OnClickListener{

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    //view declaration
    TextView eventDate,eventStartTime,eventEndTime,eventLocation;
    Button nextToThree;

    //data declaration
    private static final int PLACE_PICKER_REQUEST = 1;
    String title, instansi, date, startTime, endTime, lat, lng, address;
    int TIME_START=0;
    int TIME_END = 1;

    public EventStepTwo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_event_step_two, container, false);

        title           = getArguments().getString("title");
        instansi        = getArguments().getString("instansi");

        eventDate       = view.findViewById(R.id.eventDate);
        eventStartTime  = view.findViewById(R.id.eventStartTime);
        eventEndTime    = view.findViewById(R.id.eventEndTime);
        eventLocation   = view.findViewById(R.id.eventLocation);
        nextToThree     = view.findViewById(R.id.nextToThree);

        nextToThree.setOnClickListener(this);
        eventLocation.setOnClickListener(this);
        eventDate.setOnClickListener(this);
        eventStartTime.setOnClickListener(this);
        eventEndTime.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v == nextToThree){
            nextToThree(title, instansi, date, startTime, endTime, lat, lng, address);
        }else if(v==eventLocation){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }else if(v==eventDate){
            showDatePickerDialog();
        }else if(v==eventStartTime){
            showTimePickerDialog(TIME_START);
        }else if(v==eventEndTime){
            showTimePickerDialog(TIME_END);
        }
    }

    public void nextToThree(String title, String instansi, String date, String startTime, String endTime, String lat, String lng, String address){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        EventStepThree eventStepThree= new EventStepThree();

        Bundle data = new Bundle();
        data.putString("title", title);
        data.putString("instansi", instansi);
        data.putString("date", date);
        data.putString("startTime", startTime);
        data.putString("endTime", endTime);
        data.putString("lat", lat);
        data.putString("lng", lng);
        data.putString("address", address);
        eventStepThree.setArguments(data);


        ft.setCustomAnimations(R.animator.slide_up,
                R.animator.slide_down,
                R.animator.slide_up,
                R.animator.slide_down);
        ft.add(R.id.event_fragment,eventStepThree);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                address  = place.getAddress().toString();
                lat      = Double.toString(place.getLatLng().latitude);
                lng      = Double.toString(place.getLatLng().longitude);
                eventLocation.setText(place.getAddress());
            }
        }
    }

    public void showTimePickerDialog(final int code){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(code==TIME_START){
                    startTime   = hourOfDay+":"+minute;
                    eventStartTime.setText(startTime);
                }else if(code==TIME_END){
                    endTime     = hourOfDay+":"+minute;
                    eventEndTime.setText(endTime);
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
                eventDate.setText(dayOfMonth+"/"+monthReal+"/"+year);
            }
        },year,month,day);

        datePickerDialog.show();
    }
}