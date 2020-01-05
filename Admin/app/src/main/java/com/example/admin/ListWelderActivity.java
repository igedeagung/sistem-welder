package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListWelderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_welder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button klas=findViewById(R.id.button);
        klas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kelas=new Intent(ListWelderActivity.this, ClassWelderActivity.class);
                startActivity(kelas);
            }
        });
    }
}
