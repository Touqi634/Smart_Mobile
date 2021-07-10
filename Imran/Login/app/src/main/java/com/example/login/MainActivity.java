package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity2();
            }
        });
    }
    public void openactivity2(){
        EditText editText = (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();
        Intent intent = new Intent(this,activity2.class );
        intent.putExtra(EXTRA_TEXT, text);

        startActivity(intent);
    }
}
