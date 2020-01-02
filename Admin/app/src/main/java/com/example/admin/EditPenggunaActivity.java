package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPenggunaActivity extends AppCompatActivity {
    private EditText piew;
    private EditText piew2;
    private EditText piew3;
    private EditText piew4;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pengguna);

        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("key");

        ref= FirebaseDatabase.getInstance().getReference().child("Users").child(pessan);

        ref.child("namadepan").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                piew=findViewById(R.id.textView79);
                piew.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                piew2=findViewById(R.id.textView81);
                piew2.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                piew3=findViewById(R.id.textView83);
                piew3.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("notelp").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                piew4=findViewById(R.id.textView85);
                piew4.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button buttonupd=findViewById(R.id.button5);
        buttonupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama=piew.getText().toString();
                String email=piew2.getText().toString();
                String username=piew3.getText().toString();
                String notelp=piew4.getText().toString();

                if(TextUtils.isEmpty(nama)) {
                    piew.setError("Nama tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    piew2.setError("Email tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(username)) {
                    piew3.setError("Username tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(notelp)) {
                    piew4.setError("Nomor Telepon tidak boleh kosong");
                    return;
                }

                ref.child("namadepan").setValue(nama);
                ref.child("email").setValue(email);
                ref.child("username").setValue(username);
                ref.child("notelp").setValue(notelp);
                finish();
            }
        });

        Button buttondel=findViewById(R.id.button6);
        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    }

