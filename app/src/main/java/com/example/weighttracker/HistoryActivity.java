package com.example.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private Button mainScreenButton;
    private Button statsScreenButton;
    private Button historyScreenButton;
    private LinearLayout historiaContainer;
    private ArrayList<String> weightArray = new ArrayList<>();
    private ArrayList<String> dateArray = new ArrayList<>();
    String fileName = "wagi.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length == 2){
                    dateArray.add(parts[0]);
                    weightArray.add(parts[1]);
                }
            }
            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        historiaContainer = findViewById(R.id.historiaContainer);
        refreshHistory();

        mainScreenButton = findViewById(R.id.mainScreenButton);
        statsScreenButton = findViewById(R.id.statsScreenButton);
        historyScreenButton = findViewById(R.id.historyScreenButton);

        mainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        statsScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void refreshHistory() {
        historiaContainer.removeAllViews();
        if (weightArray != null && dateArray != null) {
            for (int i = 0; i < weightArray.size(); i++) {

                // tworzenie jednego wiersza
                LinearLayout wiersz = new LinearLayout(this);
                wiersz.setOrientation(LinearLayout.HORIZONTAL);
                wiersz.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // tworzenie textview z data
                TextView dateDisplay = new TextView(this);
                dateDisplay.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                dateDisplay.setGravity(Gravity.CENTER);
                dateDisplay.setText(dateArray.get(i));
                wiersz.addView(dateDisplay);

                // tworzenie textview z waga
                TextView weightDisplay = new TextView(this);
                weightDisplay.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                weightDisplay.setGravity(Gravity.CENTER);
                weightDisplay.setText(weightArray.get(i) + "kg");
                wiersz.addView(weightDisplay);

                // tworzenie textview z roznica wag
                TextView roznicaDisplay = new TextView(this);
                roznicaDisplay.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                roznicaDisplay.setGravity(Gravity.CENTER);
                if (i == 0) {
                    roznicaDisplay.setText("-"); // brak poprzedniego dnia
                } else {
                    double poprzednia = Double.parseDouble(weightArray.get(i - 1));
                    double aktualna = Double.parseDouble(weightArray.get(i));
                    double roznica = aktualna - poprzednia;
                    if (roznica < 0){
                        roznicaDisplay.setText(roznica + "kg");
                    }
                    else if (roznica > 0){
                        roznicaDisplay.setText("+" + roznica + "kg");
                    }
                    else {
                        roznicaDisplay.setText("0");
                    }
                }
                wiersz.addView(roznicaDisplay);

                // tworzenie buttona do usuwania
                Button deleteButton = new Button(this);
                deleteButton.setText("Usuń");
                deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                wiersz.addView(deleteButton);

                final int index = i; // potrzebne, żeby listener wiedział, który wiersz
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        historiaContainer.removeView(wiersz);
                        dateArray.remove(index);
                        weightArray.remove(index);

                        try {
                            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                            for (int j = 0; j < dateArray.size(); j++) {
                                String linia = dateArray.get(j) + "," + weightArray.get(j) + "\n";
                                fos.write(linia.getBytes());
                            }
                            fos.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        refreshHistory();
                    }
                });

                // dodanie wiersza do tabelki
                historiaContainer.addView(wiersz);
            }
        }
    }


}