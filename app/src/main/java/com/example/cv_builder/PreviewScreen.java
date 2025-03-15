package com.example.cv_builder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PreviewScreen extends AppCompatActivity {
    private ImageView imgPreview;
    private TextView summary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_screen);

        imgPreview = findViewById(R.id.imgPreview);
        summary = findViewById(R.id.summary);

        // Get the passed image URI
        Intent profileIntent = getIntent();
        if (profileIntent != null && profileIntent.hasExtra("imageUri")) {
            String imageUriString = profileIntent.getStringExtra("imageUri");
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                imgPreview.setImageURI(imageUri); // Display image
            }
        }

        // Get Summary from Intent
        Intent summaryIntent = getIntent();
        if (summaryIntent.hasExtra("summary")) {
            String summaryText = summaryIntent.getStringExtra("summary");
            summary.setText(summaryText);
        }

        //Personal Details
        Intent detailsIntent = getIntent();
        String firstName = detailsIntent.getStringExtra("firstName");
        String lastName = detailsIntent.getStringExtra("lastName");
        String email = detailsIntent.getStringExtra("email");
        String phoneNumber = detailsIntent.getStringExtra("phoneNumber");


    }
}
