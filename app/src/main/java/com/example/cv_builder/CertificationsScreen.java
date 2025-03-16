package com.example.cv_builder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CertificationsScreen extends AppCompatActivity {
    private RadioGroup rgCertifications;
    private RadioButton rbYes, rbNo;
    private EditText etCertifications;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certifications_screen);

        rgCertifications = findViewById(R.id.rgCertifications);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        etCertifications = findViewById(R.id.etCertifications);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Toggle EditText visibility based on selection
        rgCertifications.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbYes) {
                etCertifications.setVisibility(android.view.View.VISIBLE);
            } else {
                etCertifications.setVisibility(android.view.View.GONE);
            }
        });

        btnSave.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (rbYes.isChecked()) {
                String certs = etCertifications.getText().toString().trim();
                Set<String> certSet = new HashSet<>(Arrays.asList(certs.split(",")));
                editor.putStringSet("certifications", certSet);
            } else {
                editor.remove("certifications");
            }

            editor.apply();
            startActivity(new Intent(CertificationsScreen.this, MainActivity.class));
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            startActivity(new Intent(CertificationsScreen.this, MainActivity.class));
            finish();
        });
    }
}
