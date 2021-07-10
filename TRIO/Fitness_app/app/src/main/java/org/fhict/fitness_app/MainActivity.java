package org.fhict.fitness_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView textViewSignup;
    private Button buttonlogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();

        textViewSignup = (TextView) findViewById(R.id.textviewSignuph);

        editTextEmail = (EditText) findViewById(R.id.et_email);

        editTextPassword = (EditText) findViewById(R.id.et_password);

        buttonlogin = (Button) findViewById(R.id.bttn_login);

        progressDialog = new ProgressDialog(this);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity2();
            }
        });
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //checking if email and password are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if the email and password are not empty
        //displaying progress dialog
        progressDialog.setMessage("Logging in please wait....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the activity
                            finish();
                            openmainmenu();
                        }
                    }
                });
    }

    public void openmainmenu(){
        Intent intent = new Intent (this, MainMenuActivity.class);
        startActivity(intent);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        Toast.makeText(this, "Logged in as:  ".concat(useremail), Toast.LENGTH_SHORT).show();


    }

    public void openactivity2(){

        Intent intent = new Intent(this,RegisterActivity.class );
        startActivity(intent);

}}
