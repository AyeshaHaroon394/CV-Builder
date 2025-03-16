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
    private TextView tvName, tvEmail, tvPhone, tvSummary, tvHighSchool, tvUniversity;
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
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvSummary = findViewById(R.id.tvSummary);
        ivProfilePic = findViewById(R.id.ivProfilePic);  // IMAGE WAPIS
        tvHighSchool = findViewById(R.id.tvHighSchool);
        tvUniversity = findViewById(R.id.tvUniversity);
        btnBack = findViewById(R.id.btnBack);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);

        String firstName = sharedPreferences.getString("key_first_name", "N/A");
        String lastName = sharedPreferences.getString("key_last_name", "N/A");
        String email = sharedPreferences.getString("key_email", "N/A");
        String phone = sharedPreferences.getString("key_phone", "N/A");
        String summary = sharedPreferences.getString("key_summary", "No summary provided");

        // High School Details
        String highSchool = sharedPreferences.getString("key_high_school", "N/A");
        String highSchoolDegree = sharedPreferences.getString("key_high_school_degree", "N/A");
        String highSchoolYear = sharedPreferences.getString("key_high_school_year", "N/A");

        // University Details
        String university = sharedPreferences.getString("key_university", "N/A");
        String universityDegree = sharedPreferences.getString("key_university_degree", "N/A");
        String universityYear = sharedPreferences.getString("key_university_year", "N/A");

        tvName.setText(firstName + " " + lastName);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvSummary.setText(summary);

        tvHighSchool.setText("🏫 High School: " + highSchool + "\n🎓 Degree: " + highSchoolDegree + "\n📅 Year: " + highSchoolYear);
        tvUniversity.setText("🏛 University: " + university + "\n🎓 Degree: " + universityDegree + "\n📅 Year: " + universityYear);

        // Load Profile Picture
        String imageUriString = sharedPreferences.getString("key_profile_image_uri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            ivProfilePic.setImageURI(imageUri);
        }
    }
}
