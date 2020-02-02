package com.example.welder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int flag=1;
    private FirebaseAuth mauth;
    private ProgressDialog progressDialog;
    private int flag1, flag2;
    private ProgressBar pgb;
    private Thread thread=null;
    private int i=0;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pgb = findViewById(R.id.progressBar);
        pgb.setVisibility(View.VISIBLE);

        mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();

        if (user==null){
            Intent intro=new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intro);
            finish();
        }
        else{
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            thread = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!thread.isInterrupted()) {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                                    mFusedLocation.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null){
                                                Button buton=findViewById(R.id.button4);
                                                FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("latlong").setValue(location.getLatitude()+","+location.getLongitude());
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };

            thread.start();

            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Welders").child(uid);
            Button buton=findViewById(R.id.button9);
            buton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah=new Intent(MainActivity.this, RiwayatActivity.class);

                    startActivity(pindah);
                }
            });
            ref.child("status").addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int keys;
                    keys=dataSnapshot.getValue(int.class);
                    if(keys==0){
                        pgb.setVisibility(View.INVISIBLE);
                        Intent pindah= new Intent(MainActivity.this, VerifActivity.class);
                        startActivity(pindah);
                        finish();
                    }
                    else{
                        ref.child("acc").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int keys;
                                keys=dataSnapshot.getValue(int.class);
                                if(keys==0){
                                    TextView veww= findViewById(R.id.textView11);
                                    veww.setVisibility(View.VISIBLE);
                                    pgb.setVisibility(View.INVISIBLE);
                                }
                                else{

                                    TextView veww= findViewById(R.id.textView11);
                                    veww.setVisibility(View.GONE);
                                    pgb.setVisibility(View.INVISIBLE);

                                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference dabes= FirebaseDatabase.getInstance().getReference().child("Welders").child(uid);

                                    dabes.child("pid").addValueEventListener(new ValueEventListener() {
                                        String key;
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            key=dataSnapshot.getValue().toString();
                                            if (key.equals("0")){
                                                Button buton=findViewById(R.id.button9);
                                                buton.setVisibility(View.VISIBLE);
                                                TextView veww= findViewById(R.id.textView11);
                                                veww.setVisibility(View.GONE);
                                                TextView piew=findViewById(R.id.textView30);
                                                piew.setVisibility(View.VISIBLE);
                                                Button lihat=findViewById(R.id.button4);
                                                lihat.setVisibility(View.GONE);
                                            }
                                            else{
                                                Button buton=findViewById(R.id.button9);
                                                buton.setVisibility(View.VISIBLE);
                                                TextView veww= findViewById(R.id.textView11);
                                                veww.setVisibility(View.GONE);
                                                TextView piew=findViewById(R.id.textView30);
                                                piew.setVisibility(View.GONE);
                                                Button lihat=findViewById(R.id.button4);
                                                lihat.setVisibility(View.VISIBLE);
                                                lihat.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent pindah=new Intent(MainActivity.this, LihatActivity.class);
                                                        startActivity(pindah);
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

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
            Intent loginintent= new Intent(MainActivity.this, IntroActivity.class);
            startActivity(loginintent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

