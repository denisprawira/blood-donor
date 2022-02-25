package com.example.denisprawira.ta.User.UI.Fragment.Event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.R;


public class EventStepOne extends Fragment implements View.OnClickListener{

    EditText eventTitle, eventInstansi;
    Button nextToTwo;

    String title,instansi;

    public EventStepOne() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_event_step_one, container, false);

        eventTitle      = view.findViewById(R.id.eventRequestEditDescription);
        eventInstansi   = view.findViewById(R.id.eventRequestEditDocument);
        nextToTwo       = view.findViewById(R.id.nextToTwo);

        nextToTwo.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        if(v==nextToTwo){
            title    = eventTitle.getText().toString();
            instansi = eventInstansi.getText().toString();
            nextToTwo(title,instansi);
        }
    }

    public void nextToTwo(String title, String instansi){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        EventStepTwo eventStepTwo = new EventStepTwo();

        Bundle data = new Bundle();
        data.putString("title", title);
        data.putString("instansi", instansi);
        eventStepTwo.setArguments(data);

        ft.setCustomAnimations(R.animator.slide_up,
                R.animator.slide_down,
                R.animator.slide_up,
                R.animator.slide_down);
        ft.add(R.id.event_fragment,eventStepTwo);
        ft.addToBackStack(null);
        ft.commit();
    }
}