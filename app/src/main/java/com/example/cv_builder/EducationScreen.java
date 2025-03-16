package com.example.cv_builder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EducationScreen extends AppCompatActivity {

    private EditText etUniversity, etDegree, etYear, etHighSchool, etHighSchoolDegree, etHighSchoolYear;
    private Button btnSave, btnCancel;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "ProfileData";  // Updated shared preference name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_screen);

        // Initialize UI components
        etUniversity = findViewById(R.id.etUniversity);
        etDegree = findViewById(R.id.etDegree);
        etYear = findViewById(R.id.etYear);
        etHighSchool = findViewById(R.id.etHighSchool);
        etHighSchoolDegree = findViewById(R.id.etHighSchoolDegree);
        etHighSchoolYear = findViewById(R.id.etHighSchoolYear);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);  // Added cancel button

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Save button logic
        btnSave.setOnClickListener(v -> saveEducationData());

        // Cancel button logic (Navigates back without saving)
        btnCancel.setOnClickListener(v -> navigateBack());
    }

    private void saveEducationData() {
        String university = etUniversity.getText().toString().trim();
        String degree = etDegree.getText().toString().trim();
        String year = etYear.getText().toString().trim();
        String highSchool = etHighSchool.getText().toString().trim();
        String highSchoolDegree = etHighSchoolDegree.getText().toString().trim();
        String highSchoolYear = etHighSchoolYear.getText().toString().trim();

        if (university.isEmpty() || degree.isEmpty() || year.isEmpty() ||
                highSchool.isEmpty() || highSchoolDegree.isEmpty() || highSchoolYear.isEmpty()) {
            Toast.makeText(EducationScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("university", university);
        editor.putString("degree", degree);
        editor.putString("year", year);
        editor.putString("high_school", highSchool);
        editor.putString("high_school_degree", highSchoolDegree);
        editor.putString("high_school_year", highSchoolYear);
        editor.apply();

        Toast.makeText(EducationScreen.this, "Education Details Saved", Toast.LENGTH_SHORT).show();

        navigateBack();  // Navigate back after saving
    }

    private void navigateBack() {
        Intent intent = new Intent(EducationScreen.this, MainActivity.class);
        startActivity(intent);
        finish();  // Close the current activity
    }
}
