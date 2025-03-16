package com.example.cv_builder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cv_builder.MainActivity;
import com.example.cv_builder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExperienceScreen extends AppCompatActivity {

    private EditText etCompanyNames, etDuration;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_screen);

        // Initialize Views
        etCompanyNames = findViewById(R.id.etCompanyNames);
        etDuration = findViewById(R.id.etDuration);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Save Button Click Listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExperienceData();
                navigateToHome();
            }
        });

        // Cancel Button Click Listener (does not save, just navigates back)
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });
    }

    // Function to Save Experience Data as a List
    private void saveExperienceData() {
        String companyNamesInput = etCompanyNames.getText().toString().trim();
        String durationsInput = etDuration.getText().toString().trim();

        if (companyNamesInput.isEmpty() || durationsInput.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Split input into lists
        ArrayList<String> companyNamesList = new ArrayList<>(Arrays.asList(companyNamesInput.split("\\s*,\\s*")));
        ArrayList<String> durationsList = new ArrayList<>(Arrays.asList(durationsInput.split("\\s*,\\s*")));

        // Ensure both lists have the same number of elements
        if (companyNamesList.size() != durationsList.size()) {
            Toast.makeText(this, "Mismatch: Each company must have a corresponding duration", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store in SharedPreferences using Set<String>
        SharedPreferences preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("companies", new HashSet<>(companyNamesList));
        editor.putStringSet("durations", new HashSet<>(durationsList));
        editor.apply();

        Toast.makeText(this, "Experience saved!", Toast.LENGTH_SHORT).show();
    }

    // Navigate Back to Home Page and Pass List Data
    private void navigateToHome() {
        SharedPreferences preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        Set<String> companiesSet = preferences.getStringSet("companies", new HashSet<>());
        Set<String> durationsSet = preferences.getStringSet("durations", new HashSet<>());

        ArrayList<String> companiesList = new ArrayList<>(companiesSet);
        ArrayList<String> durationsList = new ArrayList<>(durationsSet);

        // Send the lists to HomeActivity
        Intent intent = new Intent(ExperienceScreen.this, MainActivity.class);
        intent.putStringArrayListExtra("companyList", companiesList);
        intent.putStringArrayListExtra("durationList", durationsList);
        startActivity(intent);
        finish();
    }
}
