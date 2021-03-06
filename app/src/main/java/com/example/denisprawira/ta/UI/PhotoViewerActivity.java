package com.example.denisprawira.ta.UI;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class PhotoViewerActivity extends AppCompatActivity {

    private PhotoDraweeView mPhotoDraweeView;
    ImageButton backEventViewPhoto;
    String document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_photo_viewer);
        document    = "http://"+getIntent().getExtras().get("document").toString();
        Log.e("gambar",document);
        mPhotoDraweeView = findViewById(R.id.photo_drawee_view);
        mPhotoDraweeView.setPhotoUri(Uri.parse(document));
        mPhotoDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoDraweeView.setPhotoUri(Uri.parse(document));
                Toast.makeText(PhotoViewerActivity.this, "Image Load", Toast.LENGTH_SHORT).show();
            }
        });
        mPhotoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                mPhotoDraweeView.setPhotoUri(Uri.parse(document));
            }
        });
        backEventViewPhoto = findViewById(R.id.backEventViewPhoto);
        backEventViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
