package com.example.cv_builder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class PreviewScreen extends AppCompatActivity {

    // Declare views
    private TextView tvName, tvEmail, tvPhone, tvSummary, tvHighSchool, tvUniversity, tvCertifications, tvReferences;
    private TextView tvCompanies, tvYears;
    private ImageView ivProfilePic;
    private Button btnBack, btnShare;

    // SharedPreferences instance
    private SharedPreferences profilePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_screen);

        init();  // Initialize views and SharedPreferences
        loadData();  // Load data into views

        // Back button functionality
        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(PreviewScreen.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        });

        btnShare.setOnClickListener(v ->{
            Uri fileUri = createAndSaveCV(); // Generate and save the CV
            if (fileUri != null) {
                shareCV(fileUri); // Share the CV
            }
        });

    }

    // Initialize views and SharedPreferences
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

        profilePrefs = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);  // Initialize SharedPreferences
    }

    // Load all data into the views
    private void loadData() {
        loadPersonalDetails();
        loadEducationDetails();
        loadExperienceDetails();
        loadCertifications();
        loadReferences();
        loadProfilePicture();
    }

    // Function to load personal details
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

    // Function to load education details
    private void loadEducationDetails() {
        String highSchool = profilePrefs.getString("high_school", "N/A");
        String highSchoolDegree = profilePrefs.getString("high_school_degree", "N/A");
        String highSchoolYear = profilePrefs.getString("high_school_year", "N/A");

        String university = profilePrefs.getString("university", "N/A");
        String universityDegree = profilePrefs.getString("degree", "N/A");
        String universityYear = profilePrefs.getString("year", "N/A");

        tvHighSchool.setText("High School: " + highSchool + " Degree: " + highSchoolDegree + " Year: " + highSchoolYear);
        tvUniversity.setText("University: " + university + " Degree: " + universityDegree + " Year: " + universityYear);
    }

    // Function to load experience details
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
    }

    // Function to load certifications
    private void loadCertifications() {
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
    }

    // Function to load references
    private void loadReferences() {
        Set<String> refSet = profilePrefs.getStringSet("references", null);
        if (refSet != null && !refSet.isEmpty()) {
            StringBuilder refText = new StringBuilder("References:\n");
            for (String ref : refSet) {
                refText.append(ref.trim()).append("\n");
            }
            tvReferences.setText(refText.toString());
        } else {
            tvReferences.setText("References: No references added");
        }
    }

    // Function to load profile picture
    private void loadProfilePicture() {
        String imageUriString = profilePrefs.getString("profileImageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            ivProfilePic.setImageURI(imageUri);
        }
    }

    private Uri createAndSaveCV() {
        String fileName = "CV_" + System.currentTimeMillis() + ".pdf";
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

        if (uri != null) {
            try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                if (outputStream != null) {
                    String content = generateCVContent(); // Generate text content of the CV
                    outputStream.write(content.getBytes());
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return uri;
    }

    private String generateCVContent() {
        return "Name: " + tvName.getText().toString() + "\n" +
                "Email: " + tvEmail.getText().toString() + "\n" +
                "Phone: " + tvPhone.getText().toString() + "\n" +
                "Summary: " + tvSummary.getText().toString() + "\n" +
                "Education: " + tvHighSchool.getText().toString() + "\n" +
                "University: " + tvUniversity.getText().toString() + "\n" +
                "Experience: " + tvCompanies.getText().toString() + "\n" +
                "Certifications: " + tvCertifications.getText().toString() + "\n" +
                "References: " + tvReferences.getText().toString();
    }

    private void shareCV(Uri fileUri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CV");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Here is my CV.");
        startActivity(Intent.createChooser(shareIntent, "Share CV via"));
    }

}
