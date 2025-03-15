package com.example.cv_builder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePictureScreen extends AppCompatActivity {
    private ImageView imgProfile;
    private Button btnSelectImage, btnSave, btnCancel;
    private ActivityResultLauncher<Intent> launcher;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_screen);

        init(); // Initialize UI components

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imgProfile.setImageURI(imageUri);
                    }
                }
        );

        //Open gallery when user clicks btn
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcher.launch(intent);
        });

        //Save button to pass the image to preview screen
        btnSave.setOnClickListener(v -> {
            if (imageUri != null) {
                Intent save = new Intent(ProfilePictureScreen.this, MainActivity.class);
                save.putExtra("imageUri", imageUri.toString());
                save.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(save);
                finish();
            }
        });

        //Cancel button
        btnCancel.setOnClickListener(v -> {
            Intent cancel = new Intent(ProfilePictureScreen.this, MainActivity.class);
            cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(cancel);
            finish();
        });
    }

    private void init() {
        imgProfile = findViewById(R.id.imgProfile);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }
}
