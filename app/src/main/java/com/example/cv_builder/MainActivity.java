package com.example.cv_builder;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnProfilePicture,
            btnPersonalDetails,
            btnSummary,
            btnEducation,
            btnExperience,
            btnCertifications,
            btnReferences,
            btnPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //Initializing
        btnProfilePicture = findViewById(R.id.btnProfilePicture);
        btnPersonalDetails = findViewById(R.id.btnPersonalDetails);
        btnSummary = findViewById(R.id.btnSummary);
        btnEducation = findViewById(R.id.btnEducation);
        btnExperience = findViewById(R.id.btnExperience);
        btnCertifications = findViewById(R.id.btnCertifications);
        btnReferences = findViewById(R.id.btnReferences);
        btnPreview = findViewById(R.id.btnPreview);

        //Setting click listeners
        btnProfilePicture.setOnClickListener(v -> navigateTo("ProfilePictureScreen"));
        btnPersonalDetails.setOnClickListener(v -> navigateTo("PersonalDetailsScreen"));
        btnSummary.setOnClickListener(v -> navigateTo("SummaryScreen"));
        btnEducation.setOnClickListener(v -> navigateTo("EducationScreen"));
        btnExperience.setOnClickListener(v -> navigateTo("ExperienceScreen"));
        btnCertifications.setOnClickListener(v -> navigateTo("CertificationsScreen"));
        btnReferences.setOnClickListener(v -> navigateTo("ReferencesScreen"));
        btnPreview.setOnClickListener(v -> navigateTo("PreviewScreen"));
    }

    private void navigateTo(String className) {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), getPackageName() + "." + className);
        startActivity(intent);
    }

}