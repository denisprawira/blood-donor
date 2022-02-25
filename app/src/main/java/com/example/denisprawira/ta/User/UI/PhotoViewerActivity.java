package com.example.denisprawira.ta.User.UI;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.R;
import com.facebook.drawee.backends.pipeline.Fresco;

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
        document    = getIntent().getExtras().get("document").toString();
        mPhotoDraweeView = findViewById(R.id.photo_drawee_view);
        mPhotoDraweeView.setPhotoUri(Uri.parse(document));
        backEventViewPhoto = findViewById(R.id.backEventViewPhoto);
        backEventViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
