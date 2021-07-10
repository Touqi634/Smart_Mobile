package org.fhict.fitness_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;
    private EditText currentWeight;
    private TextView weightToLose;
    public Button saveWeight;
    private String goalWeight;
    private double weightLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        weightToLose = findViewById(R.id.weightLose);
        currentWeight = findViewById(R.id.weightCurrent);
        saveWeight = findViewById(R.id.saveWeight);
        String weightCalc = currentWeight.getText().toString();


        saveWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWeight();
            }
        });

    }

    private void changeWeight() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));
        final String weightCurr= currentWeight.getText().toString();
        String weightLose= weightToLose.getText().toString();
        final double weightDbl = Double.parseDouble(weightCurr);
        mDocref.update("weight",weightCurr);
        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                goalWeight = documentSnapshot.getString("goalWeight");
                double d = Double.valueOf( (String) goalWeight );
                weightLeft=weightDbl-d;
                String caloreload = Double.toString(weightLeft);
                if(weightLeft==0){
                    weightToLose.setText("You achieved your goal weight");
                }
                else{
                    weightToLose.setText("Weight to lose: ".concat(caloreload).concat(" kg"));
                }

            }
        });

    }
}
