package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Intent intent = getIntent();
        String text = ((Intent) intent).getStringExtra(MainActivity.EXTRA_TEXT);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Welcome " + text);
    }
}
