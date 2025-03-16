package com.example.cv_builder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class PreviewScreen extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone, tvSummary, tvHighSchool, tvUniversity, tvCertifications, tvReferences;
    private TextView tvCompanies, tvYears;
    private ImageView ivProfilePic;
    private Button btnBack, btnShare;
    private SharedPreferences profilePrefs;

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

        btnShare.setOnClickListener(v -> shareCV());
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
        tvYears = findViewById(R.id.tvYears);
        tvCertifications = findViewById(R.id.tvCertifications);
        tvReferences = findViewById(R.id.tvReferences);
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShareCV);

        profilePrefs = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
    }

    private void loadData() {
        loadPersonalDetails();
        loadEducationDetails();
        loadExperienceDetails();
        loadCertifications();
        loadReferences();
        loadProfilePicture();
    }

    private void loadPersonalDetails() {
        String firstName = profilePrefs.getString("firstName", "N/A");
        String lastName = profilePrefs.getString("lastName", "N/A");
        String email = profilePrefs.getString("email", "N/A");
        String phone = profilePrefs.getString("phoneNumber", "N/A");
        String summary = profilePrefs.getString("summaryText", "No summary provided");

        tvName.setText(firstName + " " + lastName);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvSummary.setText(summary);
    }

    private void loadEducationDetails() {
        String highSchool = profilePrefs.getString("high_school", "N/A");
        String highSchoolDegree = profilePrefs.getString("high_school_degree", "N/A");
        String highSchoolYear = profilePrefs.getString("high_school_year", "N/A");

        String university = profilePrefs.getString("university", "N/A");
        String universityDegree = profilePrefs.getString("degree", "N/A");
        String universityYear = profilePrefs.getString("year", "N/A");

        tvHighSchool.setText("High School: " + highSchool + " | Degree: " + highSchoolDegree + " | Year: " + highSchoolYear);
        tvUniversity.setText("University: " + university + " | Degree: " + universityDegree + " | Year: " + universityYear);
    }

    private void loadExperienceDetails() {
        Set<String> companySet = profilePrefs.getStringSet("companies", null);
        Set<String> yearSet = profilePrefs.getStringSet("durations", null);

        if (companySet != null && yearSet != null) {
            StringBuilder companyText = new StringBuilder("Companies:\n");
            StringBuilder yearText = new StringBuilder("Years:\n");

            String[] companies = companySet.toArray(new String[0]);
            String[] years = yearSet.toArray(new String[0]);

            for (int i = 0; i < companies.length; i++) {
                companyText.append(companies[i]).append("\n");
                yearText.append(i < years.length ? years[i] : "N/A").append("\n");
            }

            tvCompanies.setText(companyText.toString());
            tvYears.setText(yearText.toString());
        } else {
            tvCompanies.setText("Companies: No data available");
            tvYears.setText("Years: No data available");
        }
    }

    private void loadCertifications() {
        Set<String> certSet = profilePrefs.getStringSet("certifications", null);
        tvCertifications.setText(certSet != null && !certSet.isEmpty()
                ? "Certifications:\n" + String.join("\n", certSet)
                : "Certifications: No certifications added");
    }

    private void loadReferences() {
        Set<String> refSet = profilePrefs.getStringSet("references", null);
        tvReferences.setText(refSet != null && !refSet.isEmpty()
                ? "References:\n" + String.join("\n", refSet)
                : "References: No references added");
    }

    private void loadProfilePicture() {
        String imageUriString = profilePrefs.getString("profileImageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            ivProfilePic.setImageURI(imageUri);
        }
    }

    private void shareCV() {
        String cvContent = generateCVContent();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, cvContent);
        startActivity(Intent.createChooser(shareIntent, "Share CV via"));
    }

    private String generateCVContent() {
        return "Name: " + tvName.getText().toString() + "\n" +
                "Email: " + tvEmail.getText().toString() + "\n" +
                "Phone: " + tvPhone.getText().toString() + "\n" +
                "Summary: " + tvSummary.getText().toString() + "\n\n" +
                "Education:\n" + tvHighSchool.getText().toString() + "\n" +
                tvUniversity.getText().toString() + "\n\n" +
                "Experience:\n" + tvCompanies.getText().toString() + "\n\n" +
                "Certifications:\n" + tvCertifications.getText().toString() + "\n\n" +
                "References:\n" + tvReferences.getText().toString();
    }
}
