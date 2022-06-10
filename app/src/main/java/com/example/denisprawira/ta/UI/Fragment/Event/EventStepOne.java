package com.example.denisprawira.ta.UI.Fragment.Event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Api.Model.Event;


public class EventStepOne extends Fragment implements View.OnClickListener{

    EditText eventTitle, eventInstitution,eventDescription,eventAmount;
    Button nextToTwo;

    String title,institution,description,amount;

    public EventStepOne() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_event_step_one, container, false);
        eventTitle          = view.findViewById(R.id.eventAddTitle);
        eventInstitution    = view.findViewById(R.id.eventAddInstitution);
        eventDescription    = view.findViewById(R.id.eventAddDescription);
        eventAmount         = view.findViewById(R.id.eventAddAmount);
        nextToTwo           = view.findViewById(R.id.eventAddSubmit);

        nextToTwo.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        if(v==nextToTwo){

            title    = eventTitle.getText().toString();
            institution = eventInstitution.getText().toString();
            description = eventDescription.getText().toString();
            amount = eventAmount.getText().toString();
            Event event = new Event();
            event.setTitle(title);
            event.setInstitution(institution);
            event.setDescription(description);
            event.setTarget(amount);
            nextToTwo(event);
        }
    }

    public void nextToTwo(Event event){


        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        EventStepTwo eventStepTwo = new EventStepTwo();

        Bundle data = new Bundle();
        data.putParcelable("event",event);

        eventStepTwo.setArguments(data);

        ft.setCustomAnimations(R.animator.slide_up,
                R.animator.slide_down,
                R.animator.slide_up,
                R.animator.slide_down);
        ft.replace(R.id.event_fragment,eventStepTwo);
        ft.addToBackStack(null);
        ft.commit();
    }
}