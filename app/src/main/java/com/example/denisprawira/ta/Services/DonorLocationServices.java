package com.example.denisprawira.ta.Services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.TimeUnit;

public class DonorLocationServices extends Service {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private static final String TAG = DonorLocationServices.class.getSimpleName();
    public static final String ACTION_NAME = DonorLocationServices.class.getSimpleName() + ".broadcast";
    public static final String INTENT_NAME = ACTION_NAME + ".location_update";

    private final IBinder iBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());

            }
        };
        createLocationRequest();
        getLastLocation();

//        final Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(ACTION_NAME);
//                intent.putExtra(INTENT_NAME, "hi it's me");
//                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//                handler.postDelayed(this, 5000);
//            }
//        };
//        handler.postDelayed(runnable, 5000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    public class LocalBinder extends Binder {
        public DonorLocationServices getService() {
            return DonorLocationServices.this;
        }
    }

    //customize function
    private void onNewLocation(Location location) {
        Intent intent = new Intent(ACTION_NAME);
        intent.putExtra(INTENT_NAME, location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void getLastLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            CancellationTokenSource cancellationToken = new CancellationTokenSource();
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,cancellationToken.getToken()).addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if(task.isSuccessful() && task.getResult()!=null){
                        onNewLocation(task.getResult());
                    }else{
                        Log.w(TAG,"failed to get location");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            });
        }catch (SecurityException e){
            Log.e(TAG,e.getMessage());
        }
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(TimeUnit.SECONDS.toMillis(30))
                .setFastestInterval(TimeUnit.SECONDS.toMillis(30))
                .setMaxWaitTime(TimeUnit.MINUTES.toMillis(2))
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void requestLocationsUpdates() {
        Log.e(TAG, "Requesting location updates call!!");
        startService(new Intent(getApplicationContext(), DonorLocationServices.class));
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }catch(SecurityException unlikely){
            Log.e(TAG,"location permission. could not request updates. "+unlikely);
        }
    }
}
