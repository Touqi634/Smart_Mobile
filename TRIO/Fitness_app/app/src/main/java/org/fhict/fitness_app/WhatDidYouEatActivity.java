package org.fhict.fitness_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import lecho.lib.hellocharts.view.PieChartView;

public class WhatDidYouEatActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;
    private EditText etfood;
    private EditText etcalories;
    private TextView totcalories;
    private CheckBox cbbreakfast;
    private CheckBox cblunch;
    private CheckBox cbdinner;
    private Button calcal;
    private Button savecalorie;
    private int caloriessave;
    private int recCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_did_you_eat);

        etfood = (EditText) findViewById(R.id.et_food);
        etcalories = (EditText) findViewById(R.id.et_cal);
        cbbreakfast = (CheckBox) findViewById(R.id.cb_breakfast);
        cblunch = (CheckBox) findViewById(R.id.cb_lunch);
        cbdinner = (CheckBox) findViewById(R.id.cb_dinner);
        totcalories = (TextView) findViewById(R.id.total_cal);
        calcal = (Button) findViewById(R.id.bttn_calc_cal);
        savecalorie = (Button) findViewById(R.id.bttn_save_cal);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();

        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                caloriessave = documentSnapshot.getLong("calories").intValue();
                recCalories = caloriessave - 300;
                String caloreload = Integer.toString(recCalories);
                totcalories.setText("Recommended calories: ".concat(caloreload));
            }
        });

        savecalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savecal();
            }
        });


        calcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc_cal();
            }
        });


    }

    private void calc_cal() {

        etfood = (EditText) findViewById(R.id.et_food);
        etcalories = (EditText) findViewById(R.id.et_cal);
        cbbreakfast = (CheckBox) findViewById(R.id.cb_breakfast);
        cblunch = (CheckBox) findViewById(R.id.cb_lunch);
        cbdinner = (CheckBox) findViewById(R.id.cb_dinner);
        String calories = etcalories.getText().toString();
        Integer foodCalories = Integer.parseInt(calories);
        String food = etfood.getText().toString();


        if (TextUtils.isEmpty(food) || TextUtils.isEmpty(calories)) {
            Toast.makeText(this, "Please complete the data", Toast.LENGTH_SHORT).show();
        } else {

            int multiplier = 0;

            int cal = Integer.parseInt(calories);

            if (cbbreakfast.isChecked()) {
                multiplier++;
            }

            if (cblunch.isChecked()) {
                multiplier++;
            }

            if (cbdinner.isChecked()) {
                multiplier++;
            }

            cal *= multiplier;
            recCalories -= cal;
            String calprint = Integer.toString(recCalories);

            totcalories.setText("Recommended calories: ".concat(calprint));
        }

    }


    private void savecal() {

        if (caloriessave == 0) {
            Toast.makeText(this, "Please calculate calories to save", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Calories updated", Toast.LENGTH_SHORT).show();

            mDocref.update("calories", recCalories+300);
        }
    }
}