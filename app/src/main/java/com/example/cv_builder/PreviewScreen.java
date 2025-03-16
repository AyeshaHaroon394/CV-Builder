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

import java.util.Set;

public class PreviewScreen extends AppCompatActivity {
    private TextView tvName, tvEmail, tvPhone, tvSummary, tvHighSchool, tvUniversity, tvCertifications;
    private TextView tvCompanies, tvYears;
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
        ivProfilePic = findViewById(R.id.ivProfilePic);
        tvHighSchool = findViewById(R.id.tvHighSchool);
        tvUniversity = findViewById(R.id.tvUniversity);
        tvCompanies = findViewById(R.id.tvCompanies);
        tvYears = findViewById(R.id.tvyears);
        btnBack = findViewById(R.id.btnBack);
    }

    private void loadData() {
        SharedPreferences profilePrefs = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);

        String firstName = profilePrefs.getString("firstName", "N/A");
        String lastName = profilePrefs.getString("lastName", "N/A");
        String email = profilePrefs.getString("email", "N/A");
        String phone = profilePrefs.getString("phoneNumber", "N/A");
        String summary = profilePrefs.getString("summaryText", "No summary provided");

        // Fetch education details
        String highSchool = profilePrefs.getString("high_school", "N/A");
        String highSchoolDegree = profilePrefs.getString("high_school_degree", "N/A");
        String highSchoolYear = profilePrefs.getString("high_school_year", "N/A");

        String university = profilePrefs.getString("university", "N/A");
        String universityDegree = profilePrefs.getString("degree", "N/A");
        String universityYear = profilePrefs.getString("year", "N/A");

        tvName.setText(firstName + " " + lastName);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvSummary.setText(summary);
        tvHighSchool.setText("High School: " + highSchool + " Degree: " + highSchoolDegree + " Year: " + highSchoolYear);
        tvUniversity.setText("University: " + university + " Degree: " + universityDegree + " Year: " + universityYear);

        // Load Experience Data
        Set<String> companySet = profilePrefs.getStringSet("companies", null);
        Set<String> yearSet = profilePrefs.getStringSet("durations", null);

        if (companySet != null && yearSet != null) {
            StringBuilder companyText = new StringBuilder("Companies:\n");
            StringBuilder yearText = new StringBuilder("Years:\n");

            String[] companies = companySet.toArray(new String[0]);
            String[] years = yearSet.toArray(new String[0]);

            for (int i = 0; i < companies.length; i++) {
                companyText.append(companies[i]).append("\n");
                if (i < years.length) {
                    yearText.append(years[i]).append("\n");
                } else {
                    yearText.append("N/A\n");
                }
            }

            tvCompanies.setText(companyText.toString());
            tvYears.setText(yearText.toString());
        } else {
            tvCompanies.setText("Companies: No data available");
            tvYears.setText("Years: No data available");
        }

        // Load Certifications
        Set<String> certSet = profilePrefs.getStringSet("certifications", null);
        if (certSet != null && !certSet.isEmpty()) {
            StringBuilder certText = new StringBuilder("Certifications:\n");
            for (String cert : certSet) {
                certText.append(cert.trim()).append("\n");
            }
            tvCertifications.setText(certText.toString());
        } else {
            tvCertifications.setText("Certifications: No certifications added");
        }

        // Load Profile Picture
        String imageUriString = profilePrefs.getString("key_profile_image_uri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            ivProfilePic.setImageURI(imageUri);
        }
    }
}
