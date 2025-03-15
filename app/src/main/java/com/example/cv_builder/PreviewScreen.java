package com.example.cv_builder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PreviewScreen extends AppCompatActivity {
    private TextView tvTitle, tvName, tvEmail, tvPhone, tvSummary;
    private ImageView ivProfilePic;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_screen);

        init();
        loadData();

        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(PreviewScreen.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void init() {
        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvSummary = findViewById(R.id.tvSummary);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnBack = findViewById(R.id.btnBack);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);

        String firstName = sharedPreferences.getString("firstName", "N/A");
        String lastName = sharedPreferences.getString("lastName", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String phone = sharedPreferences.getString("phoneNumber", "N/A");
        String summary = sharedPreferences.getString("summaryText", "No summary provided");

        tvName.setText(firstName + " " + lastName);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvSummary.setText(summary);

        // Load Profile Picture (If stored)
        String imageUriString = sharedPreferences.getString("profileImageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            ivProfilePic.setImageURI(imageUri);
        }
    }
}
