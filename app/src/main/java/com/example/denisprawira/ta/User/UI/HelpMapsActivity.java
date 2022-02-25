package com.example.denisprawira.ta.User.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Api.RetrofitClient;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Interface.IFCMServices;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.User;
import com.example.denisprawira.ta.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpMapsActivity extends FragmentActivity implements View.OnClickListener,
        OnMapReadyCallback ,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener{


    /////////////////////////////////////////////////////////View Declaration//////////////////////////////////////////////////////////////////////
    FloatingActionButton backToDarurat, checkMyLocation;

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
    CardView cardView;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    SeekBar seekRadius;
    Button cariPendonor2,helpA,helpB,helpAB,helpO;


    int progressChangedValue = 0;
    double lattitude, longtitude, userLatitude, userLongTitude;

    RetrofitServices retrofitServices;
    IFCMServices ifcmServices ;
    SessionManager sessionManager;
    int golDarah = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_maps);
        sessionManager = new SessionManager(HelpMapsActivity.this);
        backToDarurat = findViewById(R.id.backEventMap);
        checkMyLocation = findViewById(R.id.checkMyLocation);
        cardView = findViewById(R.id.cariPendonor);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior  = BottomSheetBehavior.from(layoutBottomSheet);


        helpA.setOnClickListener(this);
        helpB.setOnClickListener(this);
        helpAB.setOnClickListener(this);
        helpO.setOnClickListener(this);

        cardView.setOnClickListener(this);
        backToDarurat.setOnClickListener(this);
        checkMyLocation.setOnClickListener(this);
        cariPendonor2.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        user = FirebaseDatabase.getInstance().getReference("User");
        geoFire = new GeoFire(user);

        ifcmServices = RetrofitClient.getClient("https://fcm.googleapis.com/").create(IFCMServices.class);

        retrofitServices = RetrofitHelper.getRetrofit("http://" + GlobalHelper.url + "/donor_darah/index.php/api/").create(RetrofitServices.class);
        setUpLocation();

        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                drawCircle(progressChangedValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
            userLatitude     = lat;
            userLongTitude   = lng;

            geoFire.setLocation(sessionManager.getId(), new GeoLocation(lat, lng), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if(userMarker!=null){
                        userMarker.remove();
                        userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                                .title("User Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),15.0f));



                    }else{
                        userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                                .title("User Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),15.0f));

                    }
                }
            });

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
            final double lat = lastLocation.getLatitude();
            final double lng = lastLocation.getLongitude();
            lattitude = lat;
            longtitude = lng;

            if(userMarker!=null){
                userMarker.remove();
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                        .title("User Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));

            }else{
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                        .title("User Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));
            }
            if(circle!=null){
                circle.remove();
                circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(lat,lng))
                        .radius(radius)
                        .strokeColor(getColorWithAlpha(Color.BLUE,0.1f))
                        .fillColor(getColorWithAlpha(Color.BLUE,0.4f)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),getZoomLevel(circle)));
            }else{
                circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(lat,lng))
                        .radius(radius)
                        .strokeColor(getColorWithAlpha(Color.BLUE,0.1f))
                        .fillColor(getColorWithAlpha(Color.BLUE,0.4f)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),getZoomLevel(circle)));
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

            if (!success) {
                Log.e("ERROR MAP", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("ERROR MAP", "Can't find style. Error: ", e);
        }

        mMap = googleMap;

        displayLocation();
        try {
            assert mapFragment.getView() != null;
            final ViewGroup parent = (ViewGroup) mapFragment.getView().findViewWithTag("GoogleMapMyLocationButton").getParent();
            parent.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0, n = parent.getChildCount(); i < n; i++) {
                            View view = parent.getChildAt(i);
                            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                            // position on right bottom
                            rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
                            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            rlp.rightMargin = rlp.leftMargin;
                            rlp.bottomMargin = 25;
                            view.requestLayout();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add a marker in Sydney and move the camera

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v==backToDarurat){
            onBackPressed();
        }else if(v==checkMyLocation){
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
        }else if(v==cardView){
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        }else if(v==cariPendonor2){
            //sendMessage("cox_Xi3j3XQ:APA91bHe5mcpk2XSA06LJ0Uy1t8Sqetaeur-XyI6tQeVS55POCWxVqBtoJHuwTlzuLj-Fd9JMuhTKkd8OnOhBTJcrxo22BUhU1VFcQc2PQY8GqKQXjhKHi0tytK2rW_Hbk_AH8M0nsg0");
            //findUser();

        }else if(v==helpA){
            golDarah=1;
            helpA.setTextColor(getResources().getColor(R.color.white));
            helpA.setBackgroundResource(R.drawable.theme_button_solid_radius);

            helpB.setTextColor(getResources().getColor(R.color.colorAccent));
            helpB.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpAB.setTextColor(getResources().getColor(R.color.colorAccent));
            helpAB.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpO.setTextColor(getResources().getColor(R.color.colorAccent));
            helpO.setBackgroundResource(R.drawable.theme_button_stroke_radius);

        }else if(v==helpB){
            golDarah=2;
            helpB.setTextColor(getResources().getColor(R.color.white));
            helpB.setBackgroundResource(R.drawable.theme_button_solid_radius);

            helpA.setTextColor(getResources().getColor(R.color.colorAccent));
            helpA.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpAB.setTextColor(getResources().getColor(R.color.colorAccent));
            helpAB.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpO.setTextColor(getResources().getColor(R.color.colorAccent));
            helpO.setBackgroundResource(R.drawable.theme_button_stroke_radius);
        }else if(v==helpAB){
            golDarah=3;
            helpAB.setTextColor(getResources().getColor(R.color.white));
            helpAB.setBackgroundResource(R.drawable.theme_button_solid_radius);

            helpB.setTextColor(getResources().getColor(R.color.colorAccent));
            helpB.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpA.setTextColor(getResources().getColor(R.color.colorAccent));
            helpA.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpO.setTextColor(getResources().getColor(R.color.colorAccent));
            helpO.setBackgroundResource(R.drawable.theme_button_stroke_radius);
        }else if(v==helpO){
            golDarah=4;
            helpO.setTextColor(getResources().getColor(R.color.white));
            helpO.setBackgroundResource(R.drawable.theme_button_solid_radius);

            helpB.setTextColor(getResources().getColor(R.color.colorAccent));
            helpB.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpAB.setTextColor(getResources().getColor(R.color.colorAccent));
            helpAB.setBackgroundResource(R.drawable.theme_button_stroke_radius);
            helpA.setTextColor(getResources().getColor(R.color.colorAccent));
            helpA.setBackgroundResource(R.drawable.theme_button_stroke_radius);
        }



    }

    private void findUser() {
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(lattitude,longtitude),progressChangedValue);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
            @Override
            public void onDataEntered(final DataSnapshot dataSnapshot, GeoLocation location) {
                if(!sessionManager.getId().equals(dataSnapshot.getKey())){

                    retrofitServices.loadNearestUser(String.valueOf(golDarah)).enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            List<User> user = response.body();
                            for(int i=0; i<user.size();i++){
                                if(user.get(i).getId().equals(dataSnapshot.getKey())){
                                    Toast.makeText(HelpMapsActivity.this,user.get(i).getNama(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HelpMapsActivity.this,user.get(i).getToken(),Toast.LENGTH_SHORT).show();
                                    //sendMessage(user.get(i).getToken());
                                    //sendMessage("cXANRcVCpAc:APA91bF5Wz8807I0Ohf8xQkZOxCW0r-GBqy59059DeRe1Ag96a5k9vCDC4ctP0VEKeSxTnx3D7csf5M2jSEuB8tP4HpDeqdKiZOSSx373LeC92T5DBmhfrvIhFddVUMUxrM08Ah60gnT");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(HelpMapsActivity.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onDataExited(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

//    public void sendMessage(String token){
//        Data2 data = new Data2(CurrentUser.currentUser.getId(),CurrentUser.currentUser.getNama(),GlobalHelper.TITLE_REQUESTDONOR,"Butuh pertolongan",CurrentUser.currentUser.getNama(),CurrentUser.currentUser.getPhoto());
//        Sender content = new Sender(token,data);
//        ifcmServices.sendMessage(content).enqueue(new Callback<FCMResponse>() {
//            @Override
//            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
//                if(response.body().success.equals("1")){
//                    Toast.makeText(HelpMapsActivity.this,"berhasil mengirim notifikasi",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(HelpMapsActivity.this,"gagal mengirim pesan",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<FCMResponse> call, Throwable t) {
//                Toast.makeText(HelpMapsActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
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

            if(userMarker!=null){
                userMarker.remove();
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                        .title("User Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));
            }else{
                userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                        .title("User Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_blood_help)));
            }            // Animate a camera with new latlng center and required zoom.
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newCenterLatLng, reqZoom));
    }


}