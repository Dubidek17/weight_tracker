package com.example.weighttracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button mainScreenButton;
    private Button statsScreenButton;
    private Button historyScreenButton;
    private EditText editTextWeight;
    private EditText editTextDate;
    private Button submitButton;
    private String weight;
    private String date;
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

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String dateString = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                editTextDate.setText(dateString);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = editTextWeight.getText().toString();
                date = editTextDate.getText().toString();

                if (!weight.isEmpty() && !date.isEmpty()) {
                    String line = date + "," + weight + "\n";
                    String fileName = "wagi.csv";
                    try {
                        FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
                        fos.write(line.getBytes());
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        succeedText.setText("Błąd zapisu!");
                    }

                    succeedText.setText("Udało się");
                    editTextWeight.setText("");
                    editTextDate.setText("");
                } else {
                    succeedText.setText("wpisz dane!");
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
                startActivity(intent);
            }
        });
    }
}