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

public class ReferencesScreen extends AppCompatActivity {
    private RadioGroup rgReferences;
    private RadioButton rbYes, rbNo;
    private EditText etReferences;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references_screen);

        rgReferences = findViewById(R.id.rgReferences);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        etReferences = findViewById(R.id.etReferences);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Toggle EditText visibility based on selection
        rgReferences.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbYes) {
                etReferences.setVisibility(android.view.View.VISIBLE);
            } else {
                etReferences.setVisibility(android.view.View.GONE);
            }
        });

        btnSave.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (rbYes.isChecked()) {
                String refs = etReferences.getText().toString().trim();
                Set<String> refSet = new HashSet<>(Arrays.asList(refs.split(",")));
                editor.putStringSet("references", refSet);
            } else {
                editor.remove("references");
            }

            editor.apply();
            startActivity(new Intent(ReferencesScreen.this, MainActivity.class));
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            startActivity(new Intent(ReferencesScreen.this, MainActivity.class));
            finish();
        });
    }
}
