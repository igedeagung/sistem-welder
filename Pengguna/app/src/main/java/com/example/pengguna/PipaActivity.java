package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PipaActivity extends AppCompatActivity {
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        String pesan=bundle.getString("test");
        TextView vieww =findViewById(R.id.textView10);
        vieww.setText(pesan);
    }
    public void carbonwelder(View view){
        Intent towelder= new Intent(PipaActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Carbon Steel");
        startActivity(towelder);
    }
    public void stainlesswelder(View view){
        Intent towelder= new Intent(PipaActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Stainless Steel");
        startActivity(towelder);
    }
    public void nonferrowelder(View view){
        Intent towelder= new Intent(PipaActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Non Ferro");
        startActivity(towelder);
    }
}
