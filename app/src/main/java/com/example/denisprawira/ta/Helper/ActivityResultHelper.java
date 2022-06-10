package com.example.denisprawira.ta.Helper;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.ActivityResult.PmiActivityResult;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;

public class ActivityResultHelper {

    private static ActivityResultLauncher<Intent> activityFileResultLauncher ;
    private static ActivityResultLauncher<Intent> activityLocationResultLauncher;
    private static ActivityResultLauncher<Intent> activityPmiResultLauncher;

    public static void loadPmiLauncher(Activity activity){
        Intent intent = new Intent(activity, PmiActivityResult.class);
        activityPmiResultLauncher.launch(intent);
    }
    public void placePicker(Activity activity){
        Intent intent =   new PlacePicker.IntentBuilder()
                .accessToken(activity.getString(R.string.mapbox_access_token))
                .placeOptions(PlacePickerOptions.builder()
                        .statingCameraPosition(new CameraPosition.Builder()
                                .target(new LatLng(-8.588641, 115.297787)).zoom(16).build())
                        .build())
                .build(activity);
        activityLocationResultLauncher.launch(intent);
    }

    public void locationPicker(){
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("image/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityFileResultLauncher.launch(intent);
    }



}
