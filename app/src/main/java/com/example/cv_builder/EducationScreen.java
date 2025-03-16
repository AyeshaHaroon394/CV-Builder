package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EducationScreen extends AppCompatActivity {

    private EditText etUniversity, etDegree, etYear, etHighSchool, etHighSchoolDegree, etHighSchoolYear;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "ProfileData";
    private static final String KEY_UNIVERSITY = "university";
    private static final String KEY_DEGREE = "degree";
    private static final String KEY_YEAR = "year";
    private static final String KEY_HIGH_SCHOOL = "high_school";
    private static final String KEY_HIGH_SCHOOL_DEGREE = "high_school_degree";
    private static final String KEY_HIGH_SCHOOL_YEAR = "high_school_year";

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

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Save button logic
        btnSave.setOnClickListener(v -> saveEducationData());
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
        editor.putString(KEY_UNIVERSITY, university);
        editor.putString(KEY_DEGREE, degree);
        editor.putString(KEY_YEAR, year);
        editor.putString(KEY_HIGH_SCHOOL, highSchool);
        editor.putString(KEY_HIGH_SCHOOL_DEGREE, highSchoolDegree);
        editor.putString(KEY_HIGH_SCHOOL_YEAR, highSchoolYear);
        editor.apply();

        Toast.makeText(EducationScreen.this, "Education Details Saved", Toast.LENGTH_SHORT).show();
    }
}
