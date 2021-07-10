package org.fhict.fitness_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainMenuActivity extends AppCompatActivity {
    /*private ImageView imgdrink;
    private ImageView imgtreadmill;
    private ImageView imgsleep;
    private ImageView imgfood;
    private ImageView imgactivity;
    private ImageView imgweight;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /*imgdrink = (ImageView) findViewById(R.id.img_drink);
        imgtreadmill = (ImageView) findViewById(R.id.img_treadmill);
        imgsleep = (ImageView) findViewById(R.id.img_sleep);
        imgfood = (ImageView) findViewById(R.id.img_food);
        imgactivity = (ImageView) findViewById(R.id.img_activity);
        imgweight = (ImageView) findViewById(R.id.img_weight);

        imgdrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { opendrinkactivity();
            }
        });


        imgsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensleepactivity();
            }
        });

        imgfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfoodactivity();;
            }
        });


        imgweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openweightactivity();
            }
        });
*/

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_friends:
                            selectedFragment = new FriendsFragment();
                            break;case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
   /* public void opendrinkactivity(){
        Intent intent = new Intent(this, WhatDidYouDrinkActivity.class);
        startActivity(intent);
    }


    public void opensleepactivity(){
        Intent intent = new Intent(this, HowMuchDidYouSleepActivity.class);
        startActivity(intent);
    }

    public void openfoodactivity(){
        Intent intent = new Intent(this, WhatDidYouEatActivity.class);
        startActivity(intent);
    }



    public void openweightactivity(){
        Intent intent = new Intent(this, WeightActivity.class);
        startActivity(intent);
    }*/
}
