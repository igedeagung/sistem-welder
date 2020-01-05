package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JenisProyekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis_proyek);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button stel=findViewById(R.id.button18);
        stel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(JenisProyekActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "Steel Structure");
                pindahsmaw.putExtra("tipe", "proyek");
                startActivity(pindahsmaw);
            }
        });

        Button marine=findViewById(R.id.button19);
        marine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(JenisProyekActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "Konstruksi Maritim");
                pindahsmaw.putExtra("tipe", "proyek");
                startActivity(pindahsmaw);
            }
        });

        Button pipe=findViewById(R.id.button20);
        pipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(JenisProyekActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "Perpipaan");
                pindahsmaw.putExtra("tipe", "proyek");
                startActivity(pindahsmaw);
            }
        });

        Button industri=findViewById(R.id.button21);
        industri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(JenisProyekActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "Industry Manufacture");
                pindahsmaw.putExtra("tipe", "proyek");
                startActivity(pindahsmaw);
            }
        });

        Button umum=findViewById(R.id.button22);
        umum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw=new Intent(JenisProyekActivity.this, SMAWListActivity.class);
                pindahsmaw.putExtra("pesan", "Las Umum");
                pindahsmaw.putExtra("tipe", "proyek");
                startActivity(pindahsmaw);
            }
        });
    }
}
