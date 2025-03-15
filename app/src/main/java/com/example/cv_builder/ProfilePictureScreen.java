package com.example.cv_builder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePictureScreen extends AppCompatActivity {

    private ImageView imgProfile;
    private Button btnSelectImage, btnSave, btnCancel;
    private ActivityResultLauncher<Intent> launcher;
    private Uri imageUri; // Stores selected image URI
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_screen);

        init(); // Initialize UI components
        loadSavedImage(); // Load existing image if saved

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imgProfile.setImageURI(imageUri);
                    }
                }
        );

        btnSelectImage.setOnClickListener(v -> selectImage());
        btnSave.setOnClickListener(v -> saveImage());
        btnCancel.setOnClickListener(v -> {
            finish(); // Cancel without saving
        });
    }

    private void init() {
        imgProfile = findViewById(R.id.imgProfile);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    private void saveImage() {
        if (imageUri != null) {
            // Save the image URI in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profileImageUri", imageUri.toString());
            editor.apply();

            Toast.makeText(this, "Profile picture saved!", Toast.LENGTH_SHORT).show();
            finish(); // Close the screen and go back
        } else {
            Toast.makeText(this, "Please select an image first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSavedImage() {
        String savedImageUri = sharedPreferences.getString("profileImageUri", null);
        if (savedImageUri != null) {
            imageUri = Uri.parse(savedImageUri);
            imgProfile.setImageURI(imageUri);
        }
    }
}
