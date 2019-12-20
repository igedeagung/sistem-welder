package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class KapalActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kapal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void pindahferro(View view){
        Intent towelder= new Intent(KapalActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Kapal Ferro");
        startActivity(towelder);
    }
    public void pindahnonferro(View view){
        Intent towelder= new Intent(KapalActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Kapal Non Ferro");
        startActivity(towelder);
    }
}
