package com.example.welder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.DashPathEffect;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LihatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dabes= FirebaseDatabase.getInstance().getReference().child("Welders").child(uid);

        dabes
    }
}
