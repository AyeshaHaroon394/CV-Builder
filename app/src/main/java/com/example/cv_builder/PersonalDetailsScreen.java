package com.example.cv_builder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalDetailsScreen extends AppCompatActivity {
    private EditText etFirstName, etLastName, etEmail, etPhoneNumber;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_screen);

        init();
        loadSavedData(); // Load saved data when screen opens

        btnSave.setOnClickListener(v -> saveDetails());

        btnCancel.setOnClickListener(v -> {
            // Return to MainActivity without saving
            Intent cancel = new Intent(PersonalDetailsScreen.this, MainActivity.class);
            cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(cancel);
            finish();
        });
    }

    private void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void saveDetails() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        // Validation checks
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phoneNumber.matches("\\d{11}")) { // Ensure phone number is 11 digits
            Toast.makeText(this, "Invalid phone number! Enter 11 digits.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store the data in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("email", email);
        editor.putString("phoneNumber", phoneNumber);
        editor.apply();

        Toast.makeText(this, "Details saved successfully!", Toast.LENGTH_SHORT).show();

        // Navigate back to Home Screen (MainActivity)
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish();
    }

    private void loadSavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        etFirstName.setText(sharedPreferences.getString("firstName", ""));
        etLastName.setText(sharedPreferences.getString("lastName", ""));
        etEmail.setText(sharedPreferences.getString("email", ""));
        etPhoneNumber.setText(sharedPreferences.getString("phoneNumber", ""));
    }
}
