package com.example.cv_builder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PreviewScreen extends AppCompatActivity {
    private ImageView imgPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_screen);

        imgPreview = findViewById(R.id.imgPreview);

        // Get the passed image URI
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("imageUri")) {
            String imageUriString = intent.getStringExtra("imageUri");
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                imgPreview.setImageURI(imageUri); // Display image
            }
        }
    }
}
