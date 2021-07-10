package org.fhict.fitness_app;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        View rootView = this.getWindow().getDecorView().getRootView();
        ImageView profileimg = rootView.findViewById(R.id.profileimg);
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openCamera();
            }
        });

    }
    public void openCamera(){
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }
}
