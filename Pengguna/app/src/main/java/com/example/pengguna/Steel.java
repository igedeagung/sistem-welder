package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Steel extends AppCompatActivity {
    public static final String MESSAGE2="hmm";
//    private Button welderscf;
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        String pesan=bundle.getString("test");
        TextView vieww =findViewById(R.id.textView3);
        vieww.setText(pesan);
    }

    public void steelwelder(View view){
        Intent towelder= new Intent(Steel.this, ListSFCActivity.class);
        towelder.putExtra("test2", "Steel Structure");
        startActivity(towelder);
    }
}
