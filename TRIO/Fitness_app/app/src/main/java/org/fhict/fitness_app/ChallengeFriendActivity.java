package org.fhict.fitness_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChallengeFriendActivity extends AppCompatActivity {

    private EditText challengeemail;
    private Spinner amountspinner;
    private Spinner challengespinner;
    private Button savechallenge;
    private FirebaseAuth firebaseAuth;
   // private String challengeset;
    //private String challengeamount;
    private DocumentReference mDocref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_friend);

        challengeemail = (EditText) findViewById(R.id.challenge_email);

        //mDocref = FirebaseFirestore.getInstance().document("sampleData/test");

        savechallenge = (Button) findViewById(R.id.bttn_challenge_send);

        savechallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savechallenge();
            }
        });


        Spinner challengespinner = findViewById(R.id.challenges_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.challenges, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        challengespinner.setAdapter(adapter1);

        Spinner amountspinner = findViewById(R.id.amount_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.amounts, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountspinner.setAdapter(adapter2);
         //challengeset = challengespinner.getSelectedItem().toString();
         //challengeamount = amountspinner.getSelectedItem().toString();





    }



    public void savechallenge()
    {
        challengeemail = (EditText) findViewById(R.id.challenge_email);

       final String challengee = challengeemail.getText().toString();

        if(TextUtils.isEmpty(challengee))
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            Spinner challengespinner = findViewById(R.id.challenges_spinner);
            Spinner amountspinner = findViewById(R.id.amount_spinner);

            String challengeset = challengespinner.getSelectedItem().toString();
            String challengeamount = amountspinner.getSelectedItem().toString();


            mDocref = FirebaseFirestore.getInstance().document("Users/".concat(challengee));

            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            final String useremail = user.getEmail();

//            Map<String, String> datatosave = new HashMap<String, String>();
//            datatosave.put("challenge", challengeamount.concat(" " + challengeset));
            mDocref.update(
                    "challenge", challengeamount.concat(" " + challengeset));

            mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

            mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String name  = documentSnapshot.getString("name");
                    mDocref = FirebaseFirestore.getInstance().document("Users/".concat(challengee));
                    mDocref.update(
                            "challenger", name);
                }
            });

            Toast.makeText(this, "Challenge sent!", Toast.LENGTH_SHORT).show();
        }



    }
}
