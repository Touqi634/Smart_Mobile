package org.fhict.fitness_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HistoryFragment extends Fragment {

    @Nullable
    private ImageView profileimg;
    private EditText NAME;
    private EditText GENDER;
    private EditText HEIGHT;
    private EditText AGE;
    private EditText BODY_FAT;
    private EditText WEIGHT;
    private EditText GOAL_WEIGHT;
    public Button finalizeGoals;
    public View view;
    private FirebaseAuth firebaseAuth;
    private DocumentReference mDocref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        profileimg = view.findViewById(R.id.profileimg);

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));

        NAME = view.findViewById(R.id.Name);
        GENDER = view.findViewById(R.id.Gender);
        AGE = view.findViewById(R.id.Age);
        HEIGHT = view.findViewById(R.id.Height);
        WEIGHT = view.findViewById(R.id.Weight);
        GOAL_WEIGHT = view.findViewById(R.id.GoalWeight);
        BODY_FAT = view.findViewById(R.id.BodyFat);
        finalizeGoals = view.findViewById(R.id.FinalizeProfile);
        finalizeGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeGoals();
            }
        });
        mDocref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String hintName = documentSnapshot.getString("name");
                String hintAge = documentSnapshot.getString("age");
                String hintHeight = documentSnapshot.getString("height");
                String hintGender = documentSnapshot.getString("gender");
                String hintWeight = documentSnapshot.getString("weight");
                String hintGoalWeight = documentSnapshot.getString("goalWeight");
                String hintBodyFat = documentSnapshot.getString("bodyFat");
                NAME.setText(hintName);
                GENDER.setText(hintGender);
                AGE.setText(hintAge);
                HEIGHT.setText(hintHeight);
                WEIGHT.setText(hintWeight);
                GOAL_WEIGHT.setText(hintGoalWeight);
                BODY_FAT.setText(hintBodyFat);
            }
        });
        return view;
    }

    public void openCamera(){
        Intent intent = new Intent(getActivity(), Camera.class);
        startActivity(intent);
    }

    public void finalizeGoals(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String useremail = user.getEmail();
        mDocref = FirebaseFirestore.getInstance().document("Users/".concat(useremail));
        String name= NAME.getText().toString();
        String gender= GENDER.getText().toString();
        String age= AGE.getText().toString();
        String height= HEIGHT.getText().toString();
        String weight= WEIGHT.getText().toString();
        String goalWeight= GOAL_WEIGHT.getText().toString();
        String bodyFat= BODY_FAT.getText().toString();
        double heightDbl = Double.parseDouble(height);
        double weightDbl = Double.parseDouble(weight);
        int ageInt = Integer.parseInt(age);
        double calories = ((heightDbl * 6.25) + (weightDbl * 9.99) - (ageInt * 4.92) + 5)*1.2;
        mDocref.update("name",name);
        mDocref.update("gender",gender);
        mDocref.update("age",age);
        mDocref.update("height",height);
        mDocref.update("weight",weight);
        mDocref.update("goalWeight",goalWeight);
        mDocref.update("bodyFat",bodyFat);
        mDocref.update("calories", calories);
        Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
    }
}
