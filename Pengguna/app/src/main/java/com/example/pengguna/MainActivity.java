package com.example.pengguna;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar barbar=findViewById(R.id.progressBar2);
        barbar.setVisibility(View.VISIBLE);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser= mAuth.getCurrentUser();

        if(currentUser==null){
            barbar.setVisibility(View.INVISIBLE);
            Intent loginIntent= new Intent(MainActivity.this, IntroActivity.class);
            startActivity(loginIntent);
            finish();
        }
        else{

            uid=currentUser.getUid();
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(uid)){
                        barbar.setVisibility(View.INVISIBLE);
                        mAuth.signOut();
                        Intent loginIntent= new Intent(MainActivity.this, IntroActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                    else{
                        barbar.setVisibility(View.INVISIBLE);
                        Button button =findViewById(R.id.button26);
                        button.setVisibility(View.VISIBLE);
                        Button button2 =findViewById(R.id.jenisproyek);
                        button2.setVisibility(View.VISIBLE);
                        Button button3 =findViewById(R.id.button39);
                        button3.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void pindah(View v){
        Intent pindahjeni=new Intent(MainActivity.this, JenisProyek.class);
        startActivity(pindahjeni);
    }

    public void pindah2(View v){
        Intent pindahjenis=new Intent(MainActivity.this, LihatProyekActivity.class);
        startActivity(pindahjenis);
    }

    public void pindah3(View v){
        Intent pindahjeni=new Intent(MainActivity.this, HelperActivity.class);
        startActivity(pindahjeni);
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
            mAuth.signOut();
            Intent loginIntent= new Intent(MainActivity.this, IntroActivity.class);
            startActivity(loginIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
