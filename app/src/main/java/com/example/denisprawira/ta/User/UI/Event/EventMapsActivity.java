package com.example.denisprawira.ta.User.UI.Event;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denisprawira.ta.User.Adapter.UtdAdapter;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Api.RetrofitClient;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Interface.IFCMServices;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warkiz.widget.IndicatorSeekBar;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventMapsActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback ,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener{


    /////////////////////////////////////////////////////////View Declaration//////////////////////////////////////////////////////////////////////
    FloatingActionButton backEventMap, eventCurrentLocation;
    TextView eventMapTitle,eventMapDate,eventMapStartTime, eventMapEndTime,eventMapDescription,eventMapDistance;
    Button eventMapDetail;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior bottomSheetBehavior;

    ArrayList<Event> eventList = new ArrayList<>();

    UtdAdapter utdAdapter;
    float zoomLevel = 15.0f;
    double lattitude, longtitude;
    Dialog dialogTimePicker ;

    SessionManager sessionManager;



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
        setContentView(R.layout.activity_event_maps);
        sessionManager = new SessionManager(EventMapsActivity.this);
        backEventMap         = findViewById(R.id.backEventMap);
        eventCurrentLocation = findViewById(R.id.eventCurrentLocation);
        layoutBottomSheet    = findViewById(R.id.bottom_sheet_event);
        bottomSheetBehavior  = BottomSheetBehavior.from(layoutBottomSheet);

        eventMapTitle        = findViewById(R.id.eventMapTitle);
        eventMapDate         = findViewById(R.id.eventMapDate);
        eventMapStartTime    = findViewById(R.id.eventMapStartTime);
        eventMapEndTime      = findViewById(R.id.eventMapEndTime);
        eventMapDescription  = findViewById(R.id.eventMapDescription);
        eventMapDistance     = findViewById(R.id.eventMapDistance);
        eventMapDetail       = findViewById(R.id.eventMapDetail);

        eventCurrentLocation.setOnClickListener(this);
        eventMapDetail.setOnClickListener(this);
        backEventMap.setOnClickListener(this);


        /////////////////////////////////////////////////////////////////////////////animation declaration///////////////////////////////////////////////////////////
        animation1 = AnimationUtils.loadAnimation(EventMapsActivity.this,R.anim.slide_down_alpha);
        animation2 = AnimationUtils.loadAnimation(EventMapsActivity.this,R.anim.slide_up_alpha);

        /////////////////////////////////////////////////////////////////////////////view bottom sheet declaration////////////////////////////////////////////////////





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_event);
        mapFragment.getMapAsync(this);
        user = FirebaseDatabase.getInstance().getReference("User");
        geoFire = new GeoFire(user);

        ifcmServices = RetrofitClient.getClient("https://fcm.googleapis.com/").create(IFCMServices.class);

        retrofitServices = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
        setUpLocation();
        findNearEvent(GlobalHelper.lat, GlobalHelper.lng,"approved");


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

        googleMap.setOnMarkerClickListener(this);
        mMap = googleMap;
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



        displayLocation();
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





    //////////////////////////////////////////////////////////////////////////onclici block/////////////////////////////////////////////////////////////////////////


    @Override
    public void onClick(View v) {
        if(v==eventCurrentLocation) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                final double lat = lastLocation.getLatitude();
                final double lng = lastLocation.getLongitude();
                lattitude  = lat;
                longtitude = lng;

                geoFire.setLocation(sessionManager.getId(), new GeoLocation(lat, lng), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if(userMarker!=null){
                            userMarker.remove();
                            changeOffsetCenter(lattitude,longtitude, 15);
//                            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
//                                    .title("User Marker").icons(BitmapDescriptorFactory.fromResource(R.drawables.ic_blood_help)));
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),15.0f));

                        }else{
                            changeOffsetCenter(lattitude,longtitude, 15);
//                            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
//                                    .title("User Marker").icons(BitmapDescriptorFactory.fromResource(R.drawables.ic_blood_help)));
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),15.0f));

                        }
                    }
                });

            }
        }else if(v==backEventMap){
            onBackPressed();
        }

    }





    //////////////////////////////////////////////////////////////////////////custom fucntion beside geolocation function///////////////////////////////////////////

    public void findNearEvent(final String lat, final String lng, String status){
        retrofitServices.findNearEvent(lat,lng,status).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                eventList.clear();
                List<Event> event  =  response.body();
                eventList.addAll(event);
                for(int i=0;i<eventList.size();i++){
                    Marker marker;
                    marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(eventList.get(i).getLat()),Double.parseDouble(eventList.get(i).getLng())))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));
                    marker.setTag(eventList.get(i));
                }

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });

    }

    public void changeOffsetCenter(double lat,double lng, int reqZoom){
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

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newCenterLatLng, reqZoom));
    }


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
    public boolean onMarkerClick(Marker marker) {
        final Event event = (Event)marker.getTag();
        eventMapTitle.setText(event.getTitle());
        eventMapDate.setText(GlobalHelper.convertDate(event.getDate()));
        eventMapStartTime.setText(GlobalHelper.convertTime(event.getStartTime()));
        eventMapEndTime.setText(GlobalHelper.convertTime(event.getEndTime()));
        if(event.getDescription().length()>140){
            String s = event.getDescription().substring(0,137);
            eventMapDescription.setText(s+"...");
        }else{
            eventMapDescription.setText(event.getDescription());
        }
        long distance =  Math.round(Double.parseDouble(event.getDistance())*1000);
        if(distance>=1000){
            Double result = distance /1000.00;
            eventMapDistance.setText(String.format("%.2f",result )+" Km");
        }else{
            eventMapDistance.setText(String.valueOf(distance)+" M");
        }

        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        eventMapDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventMapsActivity.this,EventDetailActivity.class);
                intent.putExtra("idEvent",event.getIdEvent());
                intent.putExtra("idUser",event.getIdUser());
                intent.putExtra("idUtd",event.getIdUtd());
                intent.putExtra("userName",event.getUserName());
                intent.putExtra("userPhoto",event.getUserPhoto());
                intent.putExtra("userTelp",event.getUserTelp());
                intent.putExtra("userToken",event.getUserToken());
                intent.putExtra("utdName",event.getUtdName());
                intent.putExtra("utdPhoto",event.getUtdPhoto());
                intent.putExtra("utdTelp",event.getUtdTelp());
                intent.putExtra("utdToken",event.getUtdToken());
                intent.putExtra("title",event.getTitle());
                intent.putExtra("description",event.getDescription());
                intent.putExtra("instansi",event.getInstansi());
                intent.putExtra("target",event.getTarget());
                intent.putExtra("lat",event.getLat());
                intent.putExtra("lng",event.getLng());
                intent.putExtra("address",event.getAddress());
                intent.putExtra("document",event.getDescription());
                intent.putExtra("date",event.getDate());
                intent.putExtra("startTime",event.getStartTime());
                intent.putExtra("endTime",event.getEndTime());
                intent.putExtra("distance",event.getDistance());
                startActivity(intent);
            }
        });
        return false;
    }
}
