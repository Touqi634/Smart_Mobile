package org.fhict.fitness_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerformRandomChallenge extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;
    private ImageView imageexercise;
    private Button finishchallenge;
    private TextView rand_chall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_random_challenge);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();


        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        imageexercise = (ImageView) findViewById(R.id.img_exercise);
        finishchallenge = (Button) findViewById(R.id.bttn_finish_chall_ran);
        rand_chall = (TextView) findViewById(R.id.tv_rand_chall);

        finishchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donerandchallenge();
            }
        });

        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String load_rand = documentSnapshot.getString("random challenge");
                rand_chall.setText(load_rand);

                if(load_rand.contains("walk"))
                {
                    imageexercise.setImageResource(R.drawable.walk);
                }
                else if( load_rand.contains("setups"))
                {
                    imageexercise.setImageResource(R.drawable.setups);
                }
                else if(load_rand.contains("starjumps"))
                {
                    imageexercise.setImageResource(R.drawable.starjumps);
                }

            }
        });




    }

    public void donerandchallenge(){
        mDocref.update("random challenge" , null);
        Toast.makeText(this, "Challenge Done!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


}
