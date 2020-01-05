package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JenisProyek extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis_proyek);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void steell(View view){
        Intent pindahsteel=new Intent(JenisProyek.this, Steel.class);
        pindahsteel.putExtra("test", "Steel Structure");
        startActivity(pindahsteel);
    }
    public void construct(View view){
        Intent pindahsteel=new Intent(JenisProyek.this, KonstruksiActivity.class);
        pindahsteel.putExtra("test", "Konstruksi Maritim");
        startActivity(pindahsteel);
    }
    public void pipaan(View view){
        Intent pindahsteel=new Intent(JenisProyek.this, PipaActivity.class);
        pindahsteel.putExtra("test", "Perpipaan");
        startActivity(pindahsteel);
    }
    public void manufaktur(View view){
        Intent pindahsteel=new Intent(JenisProyek.this, ManufakturActivity.class);
        pindahsteel.putExtra("test", "Industry Manufacture");
        startActivity(pindahsteel);
    }
    public void umum(View view){
        Intent pindahsteel=new Intent(JenisProyek.this, LasUmumActivity.class);
        startActivity(pindahsteel);
    }
}
