package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryScreen extends AppCompatActivity {

    private EditText summary;
    private Button btnSave, btnCancel;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ProfileData"; // Ensure consistency

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);

        init();
        loadSavedSummary(); // Load previously saved summary

        btnSave.setOnClickListener(v -> validateSummary());
        btnCancel.setOnClickListener(v -> finish()); // Exit without saving
    }

    private void init() {
        summary = findViewById(R.id.idsummary);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    private void loadSavedSummary() {
        summary.setText(sharedPreferences.getString("summaryText", ""));
    }

    private void validateSummary() {
        String summaryText = summary.getText().toString().trim();

        if (summaryText.isEmpty()) {
            Toast.makeText(this, "Summary cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (summaryText.length() < 10) {
            Toast.makeText(this, "Summary must be at least 10 characters!", Toast.LENGTH_SHORT).show();
            return;
        } else if (summaryText.length() > 100) {
            Toast.makeText(this, "Summary cannot exceed 100 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the summary in SharedPreferences
        sharedPreferences.edit().putString("summaryText", summaryText).apply();

        Toast.makeText(this, "Summary saved successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Exit to previous screen
    }
}
