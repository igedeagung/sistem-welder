package com.example.admin;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth= FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();

        if (user==null){
            Intent intro=new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intro);
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonku=findViewById(R.id.button14);
        buttonku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welderku=new Intent(MainActivity.this, ListWelderActivity.class);
                startActivity(welderku);
            }
        });

        Button buttonnya=findViewById(R.id.button15);
        buttonnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welderku=new Intent(MainActivity.this, HomeListProyekActivity.class);
                startActivity(welderku);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mauth.signOut();
            Intent logn=new Intent(MainActivity.this, IntroActivity.class);
            startActivity(logn);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
