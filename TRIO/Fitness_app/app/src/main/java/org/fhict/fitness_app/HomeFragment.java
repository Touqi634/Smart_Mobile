package org.fhict.fitness_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class HomeFragment extends Fragment {

    @Nullable
    private ImageView imgdrink;
    private ImageView imgtreadmill;
    private ImageView imgsleep;
    private ImageView imgfood;
    private ImageView imgactivity;
    private ImageView imgweight;
    private ImageView imgprofile;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //getActivity().findViewById()
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imgdrink = (ImageView) view.findViewById(R.id.img_drink);
        imgtreadmill = (ImageView) view.findViewById(R.id.img_treadmill);
        imgsleep = (ImageView) view.findViewById(R.id.img_sleep);
        imgfood = (ImageView) view.findViewById(R.id.img_food);
        imgactivity = (ImageView) view.findViewById(R.id.img_activity);
        imgweight = (ImageView) view.findViewById(R.id.img_weight);

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


        return view;
    }

    public void opendrinkactivity(){
        Intent intent = new Intent(getActivity(), WhatDidYouDrinkActivity.class);
        startActivity(intent);
    }




    public void opensleepactivity(){
        Intent intent;
        intent = new Intent(getActivity(), HowMuchDidYouSleepActivity.class);
        startActivity(intent);
    }

    public void openfoodactivity(){
        Intent intent = new Intent(getActivity(), WhatDidYouEatActivity.class);
        startActivity(intent);
    }



    public void openweightactivity(){
        Intent intent = new Intent(getActivity(), WeightActivity.class);
        startActivity(intent);
    }
}
