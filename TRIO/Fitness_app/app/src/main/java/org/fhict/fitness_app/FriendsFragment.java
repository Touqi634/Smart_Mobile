package org.fhict.fitness_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class FriendsFragment extends Fragment {

    @Nullable
    private TextView currentchallenge;
    private TextView fromname;
    private Button buttonchallengefriend;
    private Button bttnfetch;
    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;
    private TextView rand_chall;
    private Boolean israndchall;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        final String useremail = user.getEmail();
//
//        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        buttonchallengefriend = (Button) view.findViewById(R.id.challenge_friend);
        currentchallenge = (TextView) view.findViewById(R.id.curr_challenge);
        fromname = (TextView) view.findViewById(R.id.challenge_from);
        bttnfetch = (Button) view.findViewById(R.id.bttn_fetch);
        rand_chall = (TextView)view.findViewById(R.id.random_challenge);

        currentchallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfriendchallege();
            }
        });

        rand_chall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openrandchallege();
            }
        });


        final int random = new Random().nextInt(3);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();

        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String random_challenge  = documentSnapshot.getString("random challenge");
                String challenge  = documentSnapshot.getString("challenge");
                String challenger  = documentSnapshot.getString("challenger");
                currentchallenge.setText(challenge);
                fromname.setText(challenger);
                rand_chall.setText(random_challenge);

                if(random_challenge == null)

                {
                    if(random == 0)
                    {
                        rand_chall.setText("walk for 30 mins");
                    }
                    else if( random == 1)
                    {
                        rand_chall.setText("20 setups");
                    }
                    else if( random == 2)
                    {
                        rand_chall.setText("30 starjumps");
                    }
                    mDocref.update("random challenge", rand_chall.getText());
                }
            }
        });





        bttnfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchdata();
            }
        });

        buttonchallengefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchallengefriendactivity();
            }
        });

        return view;
    }

    public void openfriendchallege()
    {
        Intent intent = new Intent(getActivity(), PerformFriendChallenge.class);
        startActivity(intent);
    }

    public void openrandchallege()
    {
        Intent intent = new Intent(getActivity(), PerformRandomChallenge.class);
        startActivity(intent);
    }

    public void fetchdata()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();

        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String challenge  = documentSnapshot.getString("challenge");
                String challenger  = documentSnapshot.getString("challenger");
                currentchallenge.setText(challenge);
                fromname.setText(challenger);

                String random_challenge  = documentSnapshot.getString("random challenge");
                rand_chall.setText(random_challenge);

                final int random = new Random().nextInt(3);

                if(random_challenge == null)

                {
                    if(random == 0)
                    {
                        rand_chall.setText("walk for 30 mins");
                    }
                    else if( random == 1)
                    {
                        rand_chall.setText("20 setups");
                    }
                    else if( random == 2)
                    {
                        rand_chall.setText("30 starjumps");
                    }
                    mDocref.update("random challenge", rand_chall.getText());
                }

                if(challenger != null)
                {
                    fromname.setText("from ".concat(challenger));
                }

            }
        });


    }

    public void openchallengefriendactivity(){
        Intent intent = new Intent(getActivity(), ChallengeFriendActivity.class);
        startActivity(intent);
    }
}
