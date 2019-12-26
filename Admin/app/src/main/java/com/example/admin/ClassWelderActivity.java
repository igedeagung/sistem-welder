package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClassWelderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_welder);

        Button smaw=findViewById(R.id.button8);
        smaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(ClassWelderActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "SMAW");
                pindahsmaw.putExtra("tipe", "welder");
                startActivity(pindahsmaw);
            }
        });

        Button fcaw=findViewById(R.id.button9);
        fcaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(ClassWelderActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "FCAW");
                pindahsmaw.putExtra("tipe", "welder");
                startActivity(pindahsmaw);
            }
        });

        Button gtaw=findViewById(R.id.button10);
        gtaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(ClassWelderActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "GTAW");
                pindahsmaw.putExtra("tipe", "welder");
                startActivity(pindahsmaw);
            }
        });

        Button smgtaw=findViewById(R.id.button11);
        smgtaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(ClassWelderActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "SMAW/GTAW");
                pindahsmaw.putExtra("tipe", "welder");
                startActivity(pindahsmaw);
            }
        });

        Button smfcaw=findViewById(R.id.button12);
        smfcaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(ClassWelderActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "SMAW/FCAW");
                pindahsmaw.putExtra("tipe", "welder");
                startActivity(pindahsmaw);
            }
        });

        Button oaw=findViewById(R.id.button13);
        oaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(ClassWelderActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "OAW");
                pindahsmaw.putExtra("tipe", "welder");
                startActivity(pindahsmaw);
            }
        });
    }
}
