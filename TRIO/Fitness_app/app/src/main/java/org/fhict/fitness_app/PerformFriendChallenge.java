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

public class PerformFriendChallenge extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;
    private ImageView imageexercise;
    private Button finishchallenge;
    private TextView friend_chall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_friend_challenge);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();


        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        imageexercise = (ImageView) findViewById(R.id.img_exercise_friend);
        finishchallenge = (Button) findViewById(R.id.bttn_finish_chall_friend);
        friend_chall = (TextView) findViewById(R.id.tv_friend_chall);

        finishchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donefriendchallenge();
            }
        });

        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String load_chall_friend = documentSnapshot.getString("challenge");
                friend_chall.setText(load_chall_friend);

                if(load_chall_friend.contains("push"))
                {
                    imageexercise.setImageResource(R.drawable.pushups);
                }
               else if(load_chall_friend.contains("crunch"))
                {
                    imageexercise.setImageResource(R.drawable.crunches);
                }
                else if(load_chall_friend.contains("plank"))
                {
                    imageexercise.setImageResource(R.drawable.planks);
                }

            }
        });

    }

    public void donefriendchallenge(){
        mDocref.update("challenge" , null);
        mDocref.update("challenger", null);
        Toast.makeText(this, "Challenge Done!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
