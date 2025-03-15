package com.example.cv_builder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

        btnSave.setOnClickListener(v -> saveDetails());

        btnCancel.setOnClickListener(v -> {
            Intent homeIntent = new Intent(PersonalDetailsScreen.this, MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
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

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
            return;
        }

        //validation check to make sure 11 digits
        if (!phoneNumber.matches("\\d{11}")) {
            Toast.makeText(this, "Invalid phone number! Enter 10 digits.", Toast.LENGTH_SHORT).show();
            return;
        }

        //sending data to PreviewScreen
        Intent previewIntent = new Intent(PersonalDetailsScreen.this, PreviewScreen.class);
        previewIntent.putExtra("firstName", firstName);
        previewIntent.putExtra("lastName", lastName);
        previewIntent.putExtra("email", email);
        previewIntent.putExtra("phoneNumber", phoneNumber);
        startActivity(previewIntent);

        //go back to Home Screen
        Intent homeIntent = new Intent(PersonalDetailsScreen.this, MainActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish();
    }
}
