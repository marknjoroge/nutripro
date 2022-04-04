package com.virtualeatery.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.virtualeatery.R;
import com.virtualeatery.models.db.DBHandler;
import com.virtualeatery.models.db.MealsTakenModel;

import java.util.ArrayList;
import java.util.Arrays;

public class AllMealsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_meals);

        displayContent();
    }

    private void displayContent() {
        ArrayList<MealsTakenModel> mealsTakenModelArrayList;
        DBHandler dbHandler = new DBHandler(AllMealsActivity.this);

        mealsTakenModelArrayList = dbHandler.getEatingInfo("");

        Log.v("temp", Arrays.deepToString(mealsTakenModelArrayList.toArray()));

        EatenMealsAdapter eatenMealsAdapter = new EatenMealsAdapter(mealsTakenModelArrayList, AllMealsActivity.this);
        RecyclerView propertyRV = findViewById(R.id.mealsRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllMealsActivity.this, RecyclerView.VERTICAL, false);
        propertyRV.setLayoutManager(linearLayoutManager);

        propertyRV.setAdapter(eatenMealsAdapter);
    }
}