package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPenggunaActivity extends AppCompatActivity {
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pengguna);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mauth= FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();

        String uid=user.getUid();

        FirebaseDatabase.getInstance().getReference().child("Admin").child(uid).child("jenis").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                if(key.equals("Manajer Lapangan")||key.equals("Manajer Administrasi")){
                    Button btn=findViewById(R.id.button6);
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("email");

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(pessan);

        ref.child("namadepan").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    TextView piew=findViewById(R.id.textView79);
                    piew.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("email").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    TextView piew=findViewById(R.id.textView81);
                    piew.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("username").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    TextView piew=findViewById(R.id.textView83);
                    piew.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("notelp").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    TextView piew=findViewById(R.id.textView85);
                    piew.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button buttonupd=findViewById(R.id.button5);
        buttonupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah=new Intent(ListPenggunaActivity.this, EditPenggunaActivity.class);
                pindah.putExtra("key", pessan);
                startActivity(pindah);
            }
        });

        Button buttondel=findViewById(R.id.button6);
        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Users");
                FirebaseDatabase.getInstance().getReference().child("Proyek").orderByChild("uid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
                    ArrayList<String> hui=new ArrayList<>();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot podt:dataSnapshot.getChildren()){
                            hui.add(podt.getKey());
                        }
                        if(hui.size()>0) {
                            for (int i = 0; i < hui.size(); i ++) {
                                FirebaseDatabase.getInstance().getReference().child("Welders").orderByChild("pid").equalTo(hui.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                                    ArrayList<String> huis=new ArrayList<>();
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot podts:dataSnapshot.getChildren()){
                                            huis.add(podts.getKey());
                                        }
                                        if(huis.size()>0) {
                                            for (int i = 0; i < huis.size(); i ++) {
                                                FirebaseDatabase.getInstance().getReference().child("Welders").child(huis.get(i)).child("pid").setValue("0");
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek");
                                res.child(hui.get(i)).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                res.child(pessan).removeValue();
                finish();
            }
        });
    }
}
