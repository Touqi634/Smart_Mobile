package org.fhict.fitness_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HowMuchDidYouSleepActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;
    private TimePicker from;
    private TimePicker to;
    private TextView messageForSleep;
    public Button saveSleep;
    private int calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_much_did_you_sleep);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        messageForSleep = findViewById(R.id.messageForSleep);
        saveSleep = findViewById(R.id.saveSleep);
        saveSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSleep();
            }
        });
    }

    private void setSleep() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));
        Integer fromSleepHour = from.getHour();
        Integer fromSleepMinute = from.getMinute();
        Integer toSleepHour = to.getHour();
        Integer toSleepMinute = to.getMinute();
        mDocref.update("fromSleepHour",fromSleepHour);
        mDocref.update("fromSleepMinute",fromSleepMinute);
        mDocref.update("toSleepHour",toSleepHour);
        mDocref.update("toSleepMinute",toSleepMinute);
        if (fromSleepHour>toSleepHour && fromSleepMinute>toSleepMinute){
            calculate=24-fromSleepHour+toSleepHour+1;
        }
        else if(fromSleepHour>toSleepHour && fromSleepMinute<=toSleepMinute){
            calculate=24-fromSleepHour+toSleepHour;
        }
        else if(fromSleepHour<toSleepHour && fromSleepMinute>toSleepMinute){
            calculate=toSleepHour-fromSleepHour+1;
        }
        else {
            calculate=toSleepHour-fromSleepHour;
        }
        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(calculate<7) {
                    messageForSleep.setText("You did not sleep enough last night! Aim to get at least 7 hours of sleep");
                }
                else{
                    messageForSleep.setText("You got more than 7 hours of sleep. Good job!");
                }
            }
        });
    }
}
