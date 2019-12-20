package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ManufakturActivity extends AppCompatActivity {
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufaktur);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        String pesan=bundle.getString("test");
        TextView vieww =findViewById(R.id.textView11);
        vieww.setText(pesan);
    }

    public void storagewelder(View view){
        Intent towelder= new Intent(ManufakturActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Storage Tank");
        startActivity(towelder);
    }

    public void pressurewelder(View view){
        Intent towelder= new Intent(ManufakturActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Pressure Tank");
        startActivity(towelder);
    }
}
