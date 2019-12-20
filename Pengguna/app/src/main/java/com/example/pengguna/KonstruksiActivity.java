package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KonstruksiActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konstruksi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        String pesan=bundle.getString("test");
        TextView vieww =findViewById(R.id.textView8);
        vieww.setText(pesan);
    }
    public void pindahkapal(View view){
        Intent tokapal= new Intent(KonstruksiActivity.this, KapalActivity.class);
        startActivity(tokapal);
    }
    public void pindahonshore(View view){
        Intent towelder= new Intent(KonstruksiActivity.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Onshore/Offshore");
        startActivity(towelder);
    }
}
