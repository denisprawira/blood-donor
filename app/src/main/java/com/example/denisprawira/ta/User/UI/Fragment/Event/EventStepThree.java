package com.example.denisprawira.ta.User.UI.Fragment.Event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.R;


public class EventStepThree extends Fragment implements View.OnClickListener{

    //view declaration
    TextView eventDescription;
    Button nextToFour;

    //data declaration
    private static final int PLACE_PICKER_REQUEST = 1;
    String title, instansi, date, startTime, endTime, lat, lng, address, description;

    public EventStepThree() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_event_step_three, container, false);

        title               = getArguments().getString("title");
        instansi            = getArguments().getString("instansi");
        date                = getArguments().getString("date");
        startTime           = getArguments().getString("startTime");
        endTime             = getArguments().getString("endTime");
        lat                 = getArguments().getString("lat");
        lng                 = getArguments().getString("lng");
        address             = getArguments().getString("address");

        eventDescription    = view.findViewById(R.id.eventDescription);
        nextToFour          = view.findViewById(R.id.nextToFour);

        nextToFour.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v == nextToFour){
            description = eventDescription.getText().toString();
            nextToFour(title, instansi, date, startTime, endTime, lat, lng, address,description);
        }
    }

    public void nextToFour(String title, String instansi, String date, String startTime, String endTime, String lat, String lng, String address,String description){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        EventStepFour eventStepFour =  new EventStepFour();

        Bundle data = new Bundle();
        data.putString("title", title);
        data.putString("instansi", instansi);
        data.putString("date", date);
        data.putString("startTime", startTime);
        data.putString("endTime", endTime);
        data.putString("lat", lat);
        data.putString("lng", lng);
        data.putString("address", address);
        data.putString("description",description);
        eventStepFour.setArguments(data);


        ft.setCustomAnimations(R.animator.slide_up,
                R.animator.slide_down,
                R.animator.slide_up,
                R.animator.slide_down);
        ft.add(R.id.event_fragment,eventStepFour);
        ft.addToBackStack(null);
        ft.commit();
    }

}