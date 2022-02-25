package com.example.denisprawira.ta.User.UI.Fragment.Event;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import android.widget.Toast;

import com.example.denisprawira.ta.User.Adapter.UtdAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Api.RetrofitClient;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Interface.IFCMServices;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Utd;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class EventStepFour extends Fragment implements View.OnClickListener{

    //view declaration
    EditText eventPmi,eventTarget,eventAttachment;
    Button submitEvent;

    //data declaration
    private static final int REQUEST_CODE = 1;
    String Eventtitle, instansi, date, startTime, endTime, lat, lng, address, description,pmi, target,pmiToken;

    Uri selectedFileUri;
    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);

    SessionManager sessionManager;

    //PMI
    RecyclerView recyclerview_pmi;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Utd> pmiList = new ArrayList<>();
    Dialog dialog;
    UtdAdapter pmiAdapter;

    String idSender,sender,type,title, idType, message,senderImage;

    File attachment;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    IFCMServices ifcmServices  = RetrofitClient.getClient("https://fcm.googleapis.com/").create(IFCMServices .class);

    public EventStepFour() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_event_step_four, container, false);
        sessionManager      = new SessionManager(getActivity());
        Eventtitle          = getArguments().getString("title");
        instansi            = getArguments().getString("instansi");
        date                = getArguments().getString("date");
        startTime           = getArguments().getString("startTime");
        endTime             = getArguments().getString("endTime");
        lat                 = getArguments().getString("lat");
        lng                 = getArguments().getString("lng");
        address             = getArguments().getString("address");
        description         = getArguments().getString("description");


        eventPmi            = view.findViewById(R.id.eventPMI);
        eventTarget         = view.findViewById(R.id.eventTarget);
        eventAttachment     = view.findViewById(R.id.eventAttachment);
        submitEvent         = view.findViewById(R.id.submitEvent);

        eventPmi.setOnClickListener(this);
        eventAttachment.setOnClickListener(this);
        submitEvent.setOnClickListener(this);
        loadPmi();
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==eventPmi){

            dialog.show();
        }else if(v==eventAttachment){
            requestPermission();
        }else if(v==submitEvent){
            Toast.makeText(getActivity(), "Submit Event: ", Toast.LENGTH_SHORT).show();
            target = eventTarget.getText().toString();
            submitEvent(Eventtitle, instansi, date, startTime, endTime, lat, lng, address,description,pmi,target,attachment,pmiToken);

        }
    }

    public void loadPmi(){
        AVLoadingIndicatorView avi = new AVLoadingIndicatorView(getActivity());
//        ViewGroup.LayoutParams params = avi.getLayoutParams();
//        params.height = GlobalHelper.toPixels(91);
//        params.width  = GlobalHelper.toPixels(89);
//        avi.setLayoutParams(params);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_listutd);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
        recyclerview_pmi = dialog.findViewById(R.id.recyclerview_utd);
        recyclerview_pmi.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview_pmi.setLayoutManager(layoutManager);

        retrofitServices.loadUtd().enqueue(new Callback<List<Utd>>() {
            @Override
            public void onResponse(Call<List<Utd>> call, Response<List<Utd>> response) {
                List<Utd> pmi = response.body();
                pmiList.addAll(pmi);
            }
            @Override
            public void onFailure(Call<List<Utd>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



//        pmiAdapter = new UtdAdapter(getContext(),pmiList, new UtdAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Utd item, int position) {
//                pmi = item.getId();
//                pmiToken = item.getToken();
//                dialog.dismiss();
//                eventPmi.setText(item.getNama());
//            }
//        });
        recyclerview_pmi.setAdapter(pmiAdapter);
    }

    public void fileChooser(){
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("image/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),REQUEST_CODE);
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


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                selectedFileUri = data.getData();
                eventAttachment.setText(getFileName(getActivity().getContentResolver(),selectedFileUri));
                attachment  = new File(getRealPathFromURI(selectedFileUri));
                Toast.makeText(getActivity(), "file: "+attachment, Toast.LENGTH_SHORT).show();
            }
        }
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


    public void submitEvent(String Eventtitle, String instansi, String date, String startTime, String endTime, String lat, String lng, String address, String description, String pmi, String target, File attachment, final String pmiToken){

        RequestBody param1 =  RequestBody.create(MultipartBody.FORM,sessionManager.getId());
        RequestBody param2 =  RequestBody.create(MultipartBody.FORM,Eventtitle);
        RequestBody param3 =  RequestBody.create(MultipartBody.FORM,instansi);
        RequestBody param4 =  RequestBody.create(MultipartBody.FORM,date);
        RequestBody param5 =  RequestBody.create(MultipartBody.FORM,startTime);
        RequestBody param6 =  RequestBody.create(MultipartBody.FORM,endTime);
        RequestBody param7 =  RequestBody.create(MultipartBody.FORM,lat);
        RequestBody param8 =  RequestBody.create(MultipartBody.FORM,lng);
        RequestBody param9 =  RequestBody.create(MultipartBody.FORM,address);
        RequestBody param10 =  RequestBody.create(MultipartBody.FORM,description);
        RequestBody param11 =  RequestBody.create(MultipartBody.FORM,pmi);
        RequestBody param12 =  RequestBody.create(MultipartBody.FORM,target);
        RequestBody file = RequestBody.create(MediaType.parse("*/*"), attachment);

        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

        MultipartBody.Part param13 = MultipartBody.Part.createFormData("file",attachment.getName(),file);

        retrofitServices.submitEvent(param1,param2,param3,param4,param5,param6,param7,param8,param9,param10,param11,param12,param13).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();
                Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


//    idSender    = remoteMessage.getData().get("idSender");
//    sender      = remoteMessage.getData().get("sender");
//    type        = remoteMessage.getData().get("type");
//    title       = remoteMessage.getData().get("title");
//    idType      = remoteMessage.getData().get("idType");
//    message     = remoteMessage.getData().get("message");
//    senderImage = remoteMessage.getData().get("senderImage");
//
//    {
//        "to":"dvU8Ao2WtyA:APA91bEmA1rty83A_TS_o_SDSo0HKF3AhasQ5bke7T6OSv7CTSwT_zodWaO3kM7aH0scnGIwTZm86MibjexkJ9EE14-_Ytpn1hjppHG4Hu1A3rdkZr1sirqclBt1FPXx4CT7hx-8XiFj",
//            "data" :{
//        "idSender": "1",
//                "sender"  : "Denis Prawira",
//                "type": "eventApproval",
//                "title": "Terdapat Event Baru",
//                "idType":"19",
//                "message":"ini sedang mengajukan event",
//                "senderImage":"http://192.168.1.68/donor_darah/assets/images/photoProfil/5e06ea82b1391d3343fd5b4016b7fea9.png"
//    }
//    }
//
//    sendNotificationEvent(title,message,senderImage);
//
    public void sendMessage(String token){


//        idSender    = CurrentUser.currentUser.getId();
//        sender      = CurrentUser.currentUser.getNama();
//        type        = GlobalHelper.TYPE_EVENTAPPROVAL;
//        title       = GlobalHelper.TITLE_EVENTAPPROVAL;
//        message     = GlobalHelper.MSG_EVENTAPPROVAL;
//        senderImage = CurrentUser.currentUser.getPhoto();
//
//        //Data2 data = new Data2(idSender,sender,type,title,idType,message,senderImage);
//        Sender content = new Sender(token,data);
//        ifcmServices.sendMessage(content).enqueue(new Callback<FCMResponse>() {
//            @Override
//            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
//                //Toast.makeText(getActivity(), response.body().success, Toast.LENGTH_SHORT).show();
//                if(response.body().success.equals("1")){
//                    Toast.makeText(getActivity(),"Anda telah mengajukan Event",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getActivity(),"Gagal mengajukan Event",Toast.LENGTH_SHORT).show();
//                }
//                Intent intent =  new Intent(getActivity(),HomeActivity.class);
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void onFailure(Call<FCMResponse> call, Throwable t) {
//                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }





}