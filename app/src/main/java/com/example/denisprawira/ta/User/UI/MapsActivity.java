package com.example.denisprawira.ta.User.UI;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.firebase.geofire.GeoFire;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback ,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener{


    /////////////////////////////////////////////////////////View Declaration//////////////////////////////////////////////////////////////////////
    FloatingActionButton backFindDonor, checkMyLocation;
    LinearLayout searchContainer;

    CardView chooseGoldarah1,chooseGoldarah2,chooseGoldarah3,chooseGoldarah4,chooseGoldarah5,chooseGoldarah6,chooseGoldarah7,chooseGoldarah8;
    ImageView goldarahImage1,goldarahImage2,goldarahImage3,goldarahImage4,goldarahImage5,goldarahImage6,goldarahImage7,goldarahImage8;
    TextView goldarahText1,goldarahText2,goldarahText3,goldarahText4,goldarahText5,goldarahText6,goldarahText7,goldarahText8;

    LinearLayout layoutBottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    SeekBar seekRadius;
    Button searchButton, nextTo,confirmSelectDate;
    EditText searchDonorUTD, searchDonorTime;

    //PMI
    RecyclerView recyclerview_utd;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Utd> utdList = new ArrayList<>();


    UtdAdapter utdAdapter;
    float zoomLevel = 15.0f;
    Dialog dialogTimePicker ;

    SessionManager sessionManager;



    ////////////////////////////////////////////////////////Calendar Declaration///////////////////////////////////////////////////////////////////
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int day         = calendar.get(Calendar.DAY_OF_MONTH);
    int month       = calendar.get(Calendar.MONTH);
    int year        = calendar.get(Calendar.YEAR);

    ////////////////////////////////////////////////////////Animation Declaration///////////////////////////////////////////////////////////////////
    Animation animation1,animation2;

    /////////////////////////////////////////////////////////Location Services//////////////////////////////////////////////////////////////////////
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;
    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    /////////////////////////////////////////////////////////Maps///////////////////////////////////////////////////////////////////////////////////
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    Marker userMarker;
    Circle circle;


    /////////////////////////////////////////////////////////Database and Geofire///////////////////////////////////////////////////////////////////////////////////
    DatabaseReference user;
    GeoFire geoFire;



    /////////////////////////////////////////////////////////////////////////////////basic data type///////////////////////////////////////////////////////////////
    String searchUtdId,searchUtdName,searchUtdToken,searchDateTime,searchDate,searchTime;
    int searchGolDarah = 0;
    int searchRadius = 0;
    double latitude, longitude, userLatitude,userLongitude;
    boolean isFirstTime=true;

    RetrofitServices retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    IFCMServices ifcmServices ;
    IndicatorSeekBar seekBarRadius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_maps2);
        sessionManager = new SessionManager(MapsActivity.this);
        backFindDonor        = findViewById(R.id.backEventMap);
        checkMyLocation      = findViewById(R.id.checkMyLocation);
        layoutBottomSheet    = findViewById(R.id.bottom_sheet2);
        bottomSheetBehavior  = BottomSheetBehavior.from(layoutBottomSheet);
        nextTo               = findViewById(R.id.nextTo);
        seekBarRadius        = findViewById(R.id.seekBarRadius);
        searchContainer      = findViewById(R.id.searchContainer);
        searchButton         = findViewById(R.id.searchButton);
        searchDonorUTD       = findViewById(R.id.searchDonorUTD);
        searchDonorTime      = findViewById(R.id.searchDonorTime);



        dialogTimePicker = new Dialog(MapsActivity.this);
        dialogTimePicker.setContentView(R.layout.layout_dialog_timepicker);
        dialogTimePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmSelectDate       = dialogTimePicker.findViewById(R.id.confirmSelectDate);
        confirmSelectDate.setOnClickListener(this);


        /////////////////////////////////////////////////////////////////////////////animation declaration///////////////////////////////////////////////////////////
        animation1 = AnimationUtils.loadAnimation(MapsActivity.this,R.anim.slide_down_alpha);
        animation2 = AnimationUtils.loadAnimation(MapsActivity.this,R.anim.slide_up_alpha);

        /////////////////////////////////////////////////////////////////////////////view bottom sheet declaration////////////////////////////////////////////////////

        searchButton.setOnClickListener(this);
        searchDonorUTD.setOnClickListener(this);
        searchDonorTime.setOnClickListener(this);

        nextTo.setOnClickListener(this);
        backFindDonor.setOnClickListener(this);
        checkMyLocation.setOnClickListener(this);



        chooseGoldarah1 = findViewById(R.id.chooseGoldarah1);
        chooseGoldarah2 = findViewById(R.id.chooseGoldarah2);
        chooseGoldarah3 = findViewById(R.id.chooseGoldarah3);
        chooseGoldarah4 = findViewById(R.id.chooseGoldarah4);
        chooseGoldarah5 = findViewById(R.id.chooseGoldarah5);
        chooseGoldarah6 = findViewById(R.id.chooseGoldarah6);
        chooseGoldarah7 = findViewById(R.id.chooseGoldarah7);
        chooseGoldarah8 = findViewById(R.id.chooseGoldarah8);

        chooseGoldarah1.setOnClickListener(this);
        chooseGoldarah2.setOnClickListener(this);
        chooseGoldarah3.setOnClickListener(this);
        chooseGoldarah4.setOnClickListener(this);
        chooseGoldarah5.setOnClickListener(this);
        chooseGoldarah6.setOnClickListener(this);
        chooseGoldarah7.setOnClickListener(this);
        chooseGoldarah8.setOnClickListener(this);

        goldarahImage1  = findViewById(R.id.goldarahImage1);
        goldarahImage2  = findViewById(R.id.goldarahImage2);
        goldarahImage3  = findViewById(R.id.goldarahImage3);
        goldarahImage4  = findViewById(R.id.goldarahImage4);
        goldarahImage5  = findViewById(R.id.goldarahImage5);
        goldarahImage6  = findViewById(R.id.goldarahImage6);
        goldarahImage7  = findViewById(R.id.goldarahImage7);
        goldarahImage8  = findViewById(R.id.goldarahImage8);

        goldarahText1   = findViewById(R.id.goldarahText1);
        goldarahText2   = findViewById(R.id.goldarahText2);
        goldarahText3   = findViewById(R.id.goldarahText3);
        goldarahText4   = findViewById(R.id.goldarahText4);
        goldarahText5   = findViewById(R.id.goldarahText5);
        goldarahText6   = findViewById(R.id.goldarahText6);
        goldarahText7   = findViewById(R.id.goldarahText7);
        goldarahText8   = findViewById(R.id.goldarahText8);


        seekBarRadius.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                searchRadius = seekParams.progress;
                drawCircle(searchRadius);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        user = FirebaseDatabase.getInstance().getReference("User");
        geoFire = new GeoFire(user);

        ifcmServices = RetrofitClient.getClient("https://fcm.googleapis.com/").create(IFCMServices.class);

        retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
        setUpLocation();

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(i==BottomSheetBehavior.STATE_COLLAPSED){
                    searchContainer.startAnimation(animation2);
                    searchContainer.setVisibility(View.VISIBLE);
                }else if(i==BottomSheetBehavior.STATE_HIDDEN){
                    searchContainer.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


    }

    ////////////////////////////////////////GPS///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void stopLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    public void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            final double lat = lastLocation.getLatitude();
            final double lng = lastLocation.getLongitude();
            latitude = lat;
            longitude = lng;
            userLatitude = lat;
            userLongitude = lng;
            if(isFirstTime==true){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zoomLevel));
                isFirstTime=false;
            }


        }

    }

    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdate();

    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        displayLocation();
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}
                    , MY_PERMISSION_REQUEST_CODE);

        }else{
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }


    private void createLocationRequest(){
        locationRequest =  new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient(){
        googleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode!=ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICE_RES_REQUEST).show();
            }else{
                Toast.makeText(this,"this device is not supported",Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;

        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSION_REQUEST_CODE){
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    public void drawCircle(int radiusMeter){
        int radius = radiusMeter*1000;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(lastLocation!=null){


            if(userMarker!=null){
                userMarker.remove();
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude))
                        .title("User Marker").icon(getBitmapDescriptor(R.drawable.ic_marker_help_blood)));

            }else{
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude))
                        .title("User Marker").icon(getBitmapDescriptor(R.drawable.ic_marker_help_blood)));
            }
            if(circle!=null){
                circle.remove();
                circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude,longitude))
                        .radius(radius)
                        .strokeColor(getColorWithAlpha(Color.BLUE,0.1f))
                        .fillColor(getColorWithAlpha(Color.BLUE,0.4f)));
                zoomLevel = getZoomLevel(circle);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),getZoomLevel(circle)));
            }else{
                circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude,longitude))
                        .radius(radius)
                        .strokeColor(getColorWithAlpha(Color.BLUE,0.1f))
                        .fillColor(getColorWithAlpha(Color.BLUE,0.4f)));
                zoomLevel = getZoomLevel(circle);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),getZoomLevel(circle)));
            }


        }

    }

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public float getZoomLevel(Circle circle) {
        float zoomLevel = 0;
        if (circle != null){
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel =(int) (15.7 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));
            mapUiSetting(googleMap);
            if (!success) {
                Log.e("ERROR MAP", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("ERROR MAP", "Can't find style. Error: ", e);
        }

        mMap = googleMap;

        displayLocation();
//        try {
//            assert mapFragment.getView() != null;
//            final ViewGroup parent = (ViewGroup) mapFragment.getView().findViewWithTag("GoogleMapMyLocationButton").getParent();
//            parent.post(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        for (int i = 0, n = parent.getChildCount(); i < n; i++) {
//                            View view = parent.getChildAt(i);
//                            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                            // position on right bottom
//                            rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//                            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
//                            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                            rlp.rightMargin = rlp.leftMargin;
//                            rlp.bottomMargin = 25;
//                            view.requestLayout();
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        // Add a marker in Sydney and move the camera

    }






//    public void sendMessage(String token){
//
//        Data2 data = new Data2("help help","help","tolong tolong",CurrentUser.currentUser.getNama(),CurrentUser.currentUser.getPhoto());
//        Sender content = new Sender(token,data);
//        ifcmServices.sendMessage(content).enqueue(new Callback<FCMResponse>() {
//            @Override
//            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
//                if(response.body().success.equals("1")){
//                    Toast.makeText(MapsActivity.this,"berhasil mengirim notifikasi",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MapsActivity.this,"gagal mengirim pesan",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<FCMResponse> call, Throwable t) {
//                Toast.makeText(MapsActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    //masih dalam tahap percobaan
//    public void changeOffsetCenter(double latitude,double longitude) {
//        Point mappoint = mMap.getProjection().toScreenLocation(new LatLng(latitude, longitude));
//        mappoint.set(mappoint.x, mappoint.y+300); // change these values as you need , just hard coded a value if you want you can give it based on a ratio like using DisplayMetrics  as well
//        if(userMarker!=null){
//            userMarker.remove();
//            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude))
//                    .title("User Marker").icons(BitmapDescriptorFactory.fromResource(R.drawables.ic_blood_help)));
//
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMap.getProjection().fromScreenLocation(mappoint),15.0f));
//        }else{
//            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude))
//                    .title("User Marker").icons(BitmapDescriptorFactory.fromResource(R.drawables.ic_blood_help)));
//            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),15.0f));
//
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMap.getProjection().fromScreenLocation(mappoint),15.0f));
//
//        }
//    }


    public void changeOffsetCenter(double lat,double lng, float reqZoom){
            // Save current zoom

            LatLng ll = new LatLng(lat,lng);
            float originalZoom = mMap.getCameraPosition().zoom;

            // Move temporarily camera zoom
            mMap.moveCamera(CameraUpdateFactory.zoomTo(reqZoom));

            Point pointInScreen = mMap.getProjection().toScreenLocation(ll);

            Point newPoint = new Point();
//            newPoint.x = pointInScreen.x + offsetX;
//            newPoint.y = pointInScreen.y + offsetY;
            newPoint.x = pointInScreen.x;
            newPoint.y = pointInScreen.y + 250;

            LatLng newCenterLatLng = mMap.getProjection().fromScreenLocation(newPoint);

            // Restore original zoom
            mMap.moveCamera(CameraUpdateFactory.zoomTo(originalZoom));

            if(userMarker!=null){
                userMarker.remove();
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                        .title("User Marker").icon(getBitmapDescriptor(R.drawable.ic_marker_help_blood)));
            }else{
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                        .title("User Marker").icon(getBitmapDescriptor(R.drawable.ic_marker_help_blood)));
            }            // Animate a camera with new latlng center and required zoom.
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newCenterLatLng, reqZoom));


    }


    public void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(MapsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    searchTime   = hourOfDay+":"+minute;
                    searchDonorTime.setText(searchTime);

            }
        },hour,minute,true);

        timePickerDialog.show();
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





    //////////////////////////////////////////////////////////////////////////onclici block/////////////////////////////////////////////////////////////////////////


    @Override
    public void onClick(View v) {
        if(v==backFindDonor){
            onBackPressed();
        }else if(v==checkMyLocation) {
            Toast.makeText(this, "check my location", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                final double lat = lastLocation.getLatitude();
                final double lng = lastLocation.getLongitude();
                latitude = lat;
                longitude = lng;

                if(isFirstTime==true){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zoomLevel));
                    isFirstTime=false;
                }

            }
        }else if(v==nextTo){
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    searchContainer.startAnimation(animation1);
                    searchContainer.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

        }else if(v==chooseGoldarah1){
             searchGolDarah = 1;
             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText1.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);

             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah2){
             searchGolDarah = 2;
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText2.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);

             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah3){
             searchGolDarah = 3;
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText3.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);

             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah4){
             searchGolDarah = 4;
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText4.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));


             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);

             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah5){
             searchGolDarah = 5;
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText5.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);


             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah6){
             searchGolDarah = 6;

             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText6.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);

             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah7){
             searchGolDarah = 7;
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText7.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText8.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_border);


             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==chooseGoldarah8){
             searchGolDarah = 8;
             chooseGoldarah8.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
             goldarahImage8.setImageResource(R.drawable.ic_drop_blood_white);
             goldarahText8.setTextColor(getResources().getColor(R.color.colorAccent));

             goldarahText1.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText2.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText3.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText4.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText5.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText6.setTextColor(getResources().getColor(R.color.navyBlue));
             goldarahText7.setTextColor(getResources().getColor(R.color.navyBlue));

             goldarahImage1.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage2.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage3.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage4.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage5.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage6.setImageResource(R.drawable.ic_drop_blood_border);
             goldarahImage7.setImageResource(R.drawable.ic_drop_blood_border);


             chooseGoldarah1.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah2.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah3.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah4.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah6.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah7.setCardBackgroundColor(getResources().getColor(R.color.white));
             chooseGoldarah5.setCardBackgroundColor(getResources().getColor(R.color.white));
        }else if(v==searchDonorUTD){
            Intent intent = new Intent(MapsActivity.this,SearchUserDatabaseActivity.class);
            startActivityForResult(intent,1);
        }else if(v==searchButton){
            addFindDonor(sessionManager.getId(),sessionManager.getName(), searchGolDarah,searchUtdId,searchDateTime,"active",sessionManager.getPhoto());
        }else if(v==searchDonorTime){
            Date date2  = Calendar.getInstance().getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            searchDate      = dateFormat.format(date2);
            searchTime      = timeFormat.format(date2);
            selectTime();
        }else if(v==confirmSelectDate){
            searchDateTime = searchDate+" "+searchTime;
            searchDonorTime.setText(GlobalHelper.convertToDayOfWeek(searchDate)+", "+GlobalHelper.convertDayMonth(searchDate)+"  "+ GlobalHelper.convert24HoursTo12Hours(searchTime));
            dialogTimePicker.dismiss();
        }



    }


    public void addFindDonor(String searchCurrentUser, String searchCurrentUserName,final int searchGolDarah, final String searchUtdId, String searchDateTime, String searchStatus, final String searchUserPhoto){
        retrofitServices.addDonor(searchCurrentUser,searchCurrentUserName,searchGolDarah,searchUtdId,searchRadius,searchDateTime,searchStatus, searchUserPhoto).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                //String searchUtdId,searchUtdName,searchUtdToken,searchDateTime,searchDate,searchTime;
                Intent intent = new Intent(MapsActivity.this,FindDonorActivity.class);
                intent.putExtra("searchIdDonor",response.body().getMessage());
                intent.putExtra("searchUtdName",searchUtdName);
                intent.putExtra("searchUtdToken",searchUtdToken);
                intent.putExtra("searchGolDarah",String.valueOf(searchGolDarah));
                intent.putExtra("searchRadius",String.valueOf(searchRadius));
                intent.putExtra("searchLatitude",String.valueOf(latitude));
                intent.putExtra("searchLongitude",String.valueOf(longitude));
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void selectTime(){
        final SingleDateAndTimePicker singleDateAndTimePicker = (SingleDateAndTimePicker) dialogTimePicker.findViewById(R.id.timePickerDialog);
        singleDateAndTimePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                searchTime = timeFormat.format(date);
                searchDate = dateFormat.format(date);
            }
        });

        dialogTimePicker.show();
    }


    //////////////////////////////////////////////////////////////////////////custom fucntion beside geolocation function///////////////////////////////////////////
//    public void loadUtd(){
//
//        dialog = new Dialog(MapsActivity.this);
//        dialog.setContentView(R.layout.layout_dialog_listutd);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        recyclerview_utd = dialog.findViewById(R.id.recyclerview_utd);
//        recyclerview_utd.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(MapsActivity.this);
//        recyclerview_utd.setLayoutManager(layoutManager);
//
//
//
//
//
//        utdAdapter = new UtdAdapter(MapsActivity.this,utdList, new UtdAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Utd item, int position) {
//                searchUtdId       = item.getId();
//                searchUtdName   = item.getNama();
//                searchUtdToken  = item.getToken();
//                dialog.dismiss();
//                if(userMarker!=null){
//                    userMarker.remove();
//                }
//
//
//                latitude = Double.parseDouble(item.getLat());
//                longitude = Double.parseDouble(item.getLng());
//                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude))
//                        .title(item.getNama()).icons(getBitmapDescriptor(R.drawables.ic_marker_help_blood)));
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),zoomLevel));
//                searchDonorUTD.setText(item.getNama());
//            }
//        });
//        recyclerview_utd.setAdapter(utdAdapter);
//    }

    private BitmapDescriptor getBitmapDescriptor(@DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @SuppressWarnings("MissingPermission")
    private void mapUiSetting(GoogleMap googleMap){
        UiSettings uiSettings = googleMap.getUiSettings();
        googleMap.setMyLocationEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            String id    = data.getStringExtra("idUtd");
            String nama  = data.getStringExtra("utdName");
            String token = data.getStringExtra("utdToken");
            String lat   = data.getStringExtra("utdLat");
            String lng   = data.getStringExtra("utdLng");
            latitude     = Double.parseDouble(lat);
            longitude    = Double.parseDouble(lng);
            searchUtdToken  = token;
            searchUtdId  = id;
            searchUtdName = nama;
            if(userMarker!=null){
                userMarker.remove();
            }
            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude))
                        .title(nama).icon(getBitmapDescriptor(R.drawable.ic_marker_help_blood)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),zoomLevel));
            searchDonorUTD.setText(nama);
            searchDonorUTD.setText(nama);
            //                searchUtdId       = item.getId();
//                searchUtdName   = item.getNama();
//                searchUtdToken  = item.getToken();
//                dialog.dismiss();
//                if(userMarker!=null){
//                    userMarker.remove();
//                }
//
//
//                latitude = Double.parseDouble(item.getLat());
//                longitude = Double.parseDouble(item.getLng());
//                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude))
//                        .title(item.getNama()).icons(getBitmapDescriptor(R.drawables.ic_marker_help_blood)));
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),zoomLevel));
//                searchDonorUTD.setText(item.getNama());

        }
    }
}
