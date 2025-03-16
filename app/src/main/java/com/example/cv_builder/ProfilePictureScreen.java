package com.example.cv_builder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ProfilePictureScreen extends AppCompatActivity {

    private ImageView imgProfile;
    private Button btnSelectImage, btnSave, btnCancel;
    private ActivityResultLauncher<Intent> launcher;
    private Uri imageUri; // Stores selected image URI
    private SharedPreferences sharedPreferences;
    private static final int PERMISSION_REQUEST_CODE = 1;

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

        btnSelectImage.setOnClickListener(v -> requestPermissionAndSelectImage());
        btnSave.setOnClickListener(v -> saveImage());
        btnCancel.setOnClickListener(v -> finish()); // Cancel without saving
    }

    private void init() {
        imgProfile = findViewById(R.id.imgProfile);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
    }

    private void requestPermissionAndSelectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            }
        } else { // Android 12 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
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

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage(); // Permission granted, open gallery
            } else {
                Toast.makeText(this, "Permission denied! Select specific photos manually.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
