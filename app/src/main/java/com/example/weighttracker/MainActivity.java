package com.example.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mainScreenButton;
    private Button statsScreenButton;
    private Button historyScreenButton;
    private EditText editTextWeight;
    private EditText editTextDate;
    private Button submitButton;
    private String weight;
    private String date;
    private ArrayList<String> weightArray = new ArrayList<>();
    private ArrayList<String> dateArray = new ArrayList<>();
    private TextView succeedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreenButton = findViewById(R.id.mainScreenButton);
        statsScreenButton = findViewById(R.id.statsScreenButton);
        historyScreenButton = findViewById(R.id.historyScreenButton);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextDate = findViewById(R.id.editTextDate);
        submitButton = findViewById(R.id.submitButton);

        succeedText = findViewById(R.id.succeedText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = editTextWeight.getText().toString();
                date = editTextDate.getText().toString();

                if(!weight.isEmpty() && !date.isEmpty()){
                    weightArray.add(weight);
                    dateArray.add(date);
                    succeedText.setText("Udało się");
                }
            }
        });

        statsScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        historyScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putStringArrayListExtra("wagi_array", weightArray);
                intent.putStringArrayListExtra("daty_array", dateArray);
                startActivity(intent);
            }
        });
    }
}