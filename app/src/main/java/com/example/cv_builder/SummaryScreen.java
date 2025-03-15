package com.example.cv_builder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cv_builder.R;

public class SummaryScreen extends AppCompatActivity {

    private EditText summary;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);
        init();

        //Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSummary();
            }
        });

        //cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(SummaryScreen.this, MainActivity.class);
                cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cancel);
                finish();
            }
        });
    }

    private void init()
    {
        summary = findViewById(R.id.idsummary);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }
    private void validateSummary() {
        String summaryText = summary.getText().toString().trim();

        if (summaryText.isEmpty()) {
            Toast.makeText(this, "Summary cannot be empty!", Toast.LENGTH_SHORT).show();
        } else if (summaryText.length() < 10) {
            Toast.makeText(this, "Summary must be at least 10 characters!", Toast.LENGTH_SHORT).show();
        } else if (summaryText.length() > 100) {
            Toast.makeText(this, "Summary cannot exceed 100 characters!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Summary saved successfully!", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent(SummaryScreen.this, PreviewScreen.class);
            resultIntent.putExtra("summary", summaryText);
            startActivity(resultIntent);
            finish();
        }
    }
}
