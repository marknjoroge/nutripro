package com.virtualeatery.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.virtualeatery.R;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    FoodFragment foodFragment = new FoodFragment();
    MapsFragment mapsFragment = new MapsFragment();
    SearchFragment searchFragment = new SearchFragment();
    String phoneNumber = "11222333444";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton callBtn = findViewById(R.id.callBtn);

        callBtn.setOnClickListener(v -> {

            String uri = "tel:" + phoneNumber.trim() ;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.map);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, foodFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int RESTAURANT = R.id.restaurant;
        final int SEARCH_FOOD = R.id.search_food;
        final int LOCATION = R.id.location;

        switch (item.getItemId()) {
            case RESTAURANT:
                Log.d(this.getLocalClassName(), "Restaurant");
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, foodFragment).commit();
                return true;

            case SEARCH_FOOD:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, searchFragment).commit();
                return true;

            case LOCATION:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, mapsFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}