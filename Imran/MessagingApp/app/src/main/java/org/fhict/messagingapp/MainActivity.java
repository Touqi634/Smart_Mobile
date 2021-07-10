package org.fhict.messagingapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int SMS_PERSMISSION_CODE = 1;
    private String nostring = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edittext1);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString() == "") {
                    Toast.makeText(MainActivity.this, "Please provide a message", Toast.LENGTH_SHORT).show();
                } else {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        SmsManager smsmanager = SmsManager.getDefault();
                        smsmanager.sendTextMessage("+31643099527", null, editText.getText().toString(), null, null);
                        Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                    } else {
                        requestsmspermission();
                    }


                }
            }
        });





    }

    private void requestsmspermission(){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SMS_PERSMISSION_CODE);
        }
    }


