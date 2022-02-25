package com.example.denisprawira.ta.User.UI.Fragment.RequestEvent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.R;


public class RequestEventApproved extends Fragment implements View.OnClickListener{

    String idEvent,idPmi,pmiName,pmiPhoto,pmiTelp,pmiToken,title,description,instansi,lat,lng,address,document,date,startTime,endTime,target,postTime,status;
    ImageButton backRequestEventApproved,editRequestEvent;
    Button eventApprovedOpenMap;
    TextView eventApprovedPmi,eventApprovedTitle, eventApprovedDescription,eventApprovedInstansi, eventApprovedTarget,eventApprovedDate,eventApprovedStartTime,eventApprovedEndTime,eventApprovedAddress;
    ImageView eventApprovedPmiPhoto;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);


    public RequestEventApproved() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_request_event_approved, container, false);

        eventApprovedPmi            = view.findViewById(R.id.eventApprovedPmi);
        eventApprovedTitle          = view.findViewById(R.id.eventApprovedTitle);
        eventApprovedDescription    = view.findViewById(R.id.eventApprovedDescription);
        eventApprovedInstansi       = view.findViewById(R.id.eventApprovedInstansi);
        eventApprovedTarget         = view.findViewById(R.id.eventApprovedTarget);
        eventApprovedDate           = view.findViewById(R.id.eventApprovedDate);
        eventApprovedStartTime      = view.findViewById(R.id.eventApprovedStartTime);
        eventApprovedEndTime        = view.findViewById(R.id.eventApprovedEndTime);
        eventApprovedAddress        = view.findViewById(R.id.eventApprovedAddress);
        eventApprovedPmiPhoto       = view.findViewById(R.id.eventApprovedPmiPhoto);
        editRequestEvent            = view.findViewById(R.id.editRequestEvent);

        eventApprovedOpenMap        = view.findViewById(R.id.eventApprovedOpenMap);

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

        eventApprovedPmi.setText(pmiName);
        eventApprovedTitle.setText(title);
        eventApprovedDescription.setText(description);
        eventApprovedInstansi.setText(instansi);
        eventApprovedTarget.setText(target);
        eventApprovedDate.setText(date);
        eventApprovedStartTime.setText(startTime);
        eventApprovedEndTime.setText(endTime);
        eventApprovedAddress.setText(address);

        Glide.with(getActivity()).load(pmiPhoto).into(eventApprovedPmiPhoto);

        backRequestEventApproved = view.findViewById(R.id.backRequestEventApproved);

        eventApprovedOpenMap.setOnClickListener(this);
        backRequestEventApproved.setOnClickListener(this);
        editRequestEvent.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v==backRequestEventApproved){
            getActivity().onBackPressed();
        }else if(v==editRequestEvent){
            RequestEventEdit requestEventEdit= new RequestEventEdit();

            Bundle data = new Bundle();
            data.putString("idEvent",idEvent);
            data.putString("idPmi",idPmi);
            data.putString("pmiName",pmiName);
            data.putString("pmiPhoto",pmiPhoto);
            data.putString("pmiTelp",pmiTelp);
            data.putString("pmiToken",pmiToken);
            data.putString("title",title);
            data.putString("description",description);
            data.putString("instansi",instansi);
            data.putString("lat",lat);
            data.putString("lng",lng);
            data.putString("address",address);
            data.putString("document",document);
            data.putString("date",date);
            data.putString("startTime",startTime);
            data.putString("endTime",endTime);
            data.putString("target",target);
            data.putString("postTime",postTime);
            data.putString("status",status);
            requestEventEdit.setArguments(data);

//            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up,
//                    R.animator.slide_down,
//                    R.animator.slide_up,
//                    R.animator.slide_down).add(R.id.request_event_fragment,requestEventEdit).addToBackStack(null).commit();

        }else if(v==eventApprovedOpenMap){
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }
    }





}