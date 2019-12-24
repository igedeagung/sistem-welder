package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LasUmumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_las_umum);

        Button pindahoaw= findViewById(R.id.button27);
        pindahoaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oaww= new Intent(LasUmumActivity.this, OAWActivity.class);
                startActivity(oaww);
            }
        });
    }
}
