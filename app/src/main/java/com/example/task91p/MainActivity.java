package com.example.task91p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addPlace, showAll;

    ArrayList<String> savedLatitudes;
    ArrayList<String> savedLongitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPlace = findViewById(R.id.buttonAdd);
        showAll = findViewById(R.id.buttonShow);

        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPlace.class);
                startActivity(intent);
            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedLatitudes = (ArrayList<String>) getIntent().getSerializableExtra("LatArrays");
                savedLongitudes = (ArrayList<String>) getIntent().getSerializableExtra("LongArrays");

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("LatArray", savedLatitudes);
                intent.putExtra("LongArray", savedLatitudes);

                startActivity(intent);
            }
        });
    }
}