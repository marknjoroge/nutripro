package com.virtualeatery.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualeatery.R;
import com.virtualeatery.models.db.DBHandler;


public class NutritionalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritional_information);

        TextView searchedFood = findViewById(R.id.foodSearched);
        Button addToConsumedBtn = findViewById(R.id.addToConsumedBtn);
        DBHandler dbHandler = new DBHandler(NutritionalInformationActivity.this);

        TextView nut0 = findViewById(R.id.foodValue0);
        TextView nut1 = findViewById(R.id.foodValue1);
        TextView nut2 = findViewById(R.id.foodValue2);
        TextView nut3 = findViewById(R.id.foodValue3);

        Intent intent = getIntent();

        String calorieCnt = intent.getStringExtra("calories");
        String carbsCnt = intent.getStringExtra("carbohydrates");
        String proteinCnt = intent.getStringExtra("proteins");
        String fatCnt = intent.getStringExtra("fats");

        String query = intent.getStringExtra("query");

        searchedFood.setText(query);

        nut0.setText(new StringBuilder().append(getString(R.string.calories)).append(": ").append(calorieCnt).append(" Cal"));
        nut1.setText(new StringBuilder().append(getString(R.string.carbohydrates)).append(": ").append(carbsCnt).append(" g"));
        nut2.setText(new StringBuilder().append(getString(R.string.proteins)).append(": ").append(proteinCnt).append(" g"));
        nut3.setText(new StringBuilder().append(getString(R.string.fats)).append(": ").append(fatCnt).append(" g"));

        addToConsumedBtn.setOnClickListener(view -> {
            dbHandler.addNewMealInfo(query);

            Toast.makeText(NutritionalInformationActivity.this, query + " added successfully", Toast.LENGTH_SHORT).show();
        });
    }
}