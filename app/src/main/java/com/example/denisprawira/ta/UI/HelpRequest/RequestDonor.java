package com.example.denisprawira.ta.UI.HelpRequest;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.Broadcast.DonorLocationReceiver;
import com.example.denisprawira.ta.Observer.DonorLocationObserver;
import com.example.denisprawira.ta.Services.DonorLocationServices;
import com.example.denisprawira.ta.UI.ActivityResult.PmiActivityResult;
import com.example.denisprawira.ta.Values.GlobalValues;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;


import java.util.Observable;
import java.util.Observer;

public class RequestDonor extends AppCompatActivity implements Observer, OnMapReadyCallback,View.OnClickListener{

    private MapboxMap mapboxMap;
    private MapView mapView;
    private EditText searchDonorUtd;
    private Button searchDonorNext;

    //--permission
    private ActivityResultContracts.RequestMultiplePermissions requestMultiplePermissions;
    private ActivityResultLauncher<String[]> activityResultLauncher;

    ActivityResultLauncher<Intent> activityPmiResultLauncher;

    //--location
    DonorLocationServices localService = null;
    DonorLocationReceiver locationReceiver;
    DonorLocationObserver locationObserver;
    Location location = null;

    //--marker constant
    private final String MARKER_ID = "marker-icon-id";
    private final String MARKER_SOURCE_ID = "marker-source-id";
    private final String MARKER_SYMBOL_LAYER_ID = "marker-symbol-layer-id";
    private String idPmi ;
    private boolean mBound = false;
    final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_help_maps);
        mapView = findViewById(R.id.helpMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        searchDonorNext = findViewById(R.id.findDonorAddNext);
        searchDonorUtd = findViewById(R.id.findDonorAddUtd);

        searchDonorNext.setOnClickListener(this);
        searchDonorUtd.setOnClickListener(this);
        searchDonorUtd.setClickable(true);
        searchDonorUtd.setFocusable(false);

        locationReceiver = new DonorLocationReceiver();
        locationObserver = new DonorLocationObserver();

        checkpermission();
        locationObserver.getInstance().addObserver(this);


        activityPmiResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK){
                    Pmi pmi = result.getData().getParcelableExtra(GlobalValues.PMI_INTENT);
                    idPmi = pmi.getId();
                    addMarker(Point.fromLngLat(Double.parseDouble(pmi.getLng()),Double.parseDouble(pmi.getLat())));
                    searchDonorUtd.setText(pmi.getName());
                    Toast.makeText(RequestDonor.this, "id pmi: "+idPmi, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DonorLocationServices.LocalBinder binder = (DonorLocationServices.LocalBinder) iBinder;
            localService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            localService = null;
            mBound = false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkpermission(){
        requestMultiplePermissions = new ActivityResultContracts.RequestMultiplePermissions();
        activityResultLauncher = registerForActivityResult(requestMultiplePermissions, result -> {
            Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
            Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
            Boolean writeExternalStorage = result.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
            Boolean readExternalStorage = result.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false);
            Log.e("permission",""+fineLocationGranted+coarseLocationGranted+writeExternalStorage+readExternalStorage);
        });
        activityResultLauncher.launch(PERMISSIONS);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        if(location!=null){
           // setMapStyle(mapboxMap);
            //setCameraPosition(location);
        }
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
//                if(location!=null){
//                    addDestinationIconSymbolLayer(style,location);
//                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view==searchDonorUtd){
            Intent intent = new Intent(RequestDonor.this, PmiActivityResult.class);
            activityPmiResultLauncher.launch(intent);
        }else if(view==searchDonorNext){
            Intent intent = new Intent(RequestDonor.this,RequestDonorDetail.class);
            intent.putExtra("idPmi",idPmi);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, DonorLocationServices.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkpermission();
        }else{
            if(localService!=null){
                Log.e("location","update location services Home activity");
                localService.requestLocationsUpdates();
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Intent intent = (Intent) o;
        location = intent.getParcelableExtra(DonorLocationServices.INTENT_NAME);
        Log.e("location map: ",String.valueOf(location.getLatitude()));
        Log.e("location map: ",String.valueOf(location.getLongitude()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DonorLocationServices.ACTION_NAME);
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        mBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void addMarker(Point markerLocation){
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if(style.getSource(MARKER_ID)==null && style.getSource(MARKER_SOURCE_ID)==null){
                    addDestinationIconSymbolLayer(style,markerLocation);
                }else{
                    GeoJsonSource markerSource = style.getSourceAs(MARKER_SOURCE_ID);
                    if (markerSource != null) {
                        markerSource.setGeoJson(markerLocation);
                    }
                }
            }
        });
    }

    private void addDestinationIconSymbolLayer(Style style,Point markerPoint){
        style.addImage(MARKER_ID, BitmapFactory.decodeResource(this.getResources(),R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("marker-source-id");
        style.addSource(geoJsonSource);

        SymbolLayer markerSymbolLayler = new SymbolLayer(MARKER_SYMBOL_LAYER_ID,MARKER_SOURCE_ID);
        markerSymbolLayler.withProperties(iconImage(MARKER_ID),
                iconAllowOverlap(true),
                iconIgnorePlacement(true));
        style.addLayer(markerSymbolLayler);
        Point iconPoint = markerPoint;
        GeoJsonSource source = mapboxMap.getStyle().getSourceAs(MARKER_SOURCE_ID);
        if(source !=null){
            source.setGeoJson(Feature.fromGeometry(iconPoint));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkpermission();
        }else{
            // Get an instance of the component
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
            locationComponent.getCompassEngine();
        }
    }

}