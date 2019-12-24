package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetilWelderctivity extends AppCompatActivity {
    private DatabaseReference ref;
    private TextView nmlkp;
    private TextView spek;
    private TextView pos;
    private TextView alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_welderctivity);

        Bundle bundle=getIntent().getExtras();
        String pessan=bundle.getString("email");
        TextView view=findViewById(R.id.textView8);
        view.setText(pessan);

        nmlkp=findViewById(R.id.textView8);
        spek=findViewById(R.id.textView14);
        pos=findViewById(R.id.textView16);
        alamat=findViewById(R.id.textView20);

        ref = FirebaseDatabase.getInstance().getReference().child("Welders");
        Query allPostFromAuthor = ref.orderByChild("email").equalTo(pessan);
        // Add listener for Firebase response on said query
        allPostFromAuthor.addValueEventListener( new ValueEventListener() {
            String hasil;
            String hasil2;
            String hasil3;
            String hasil4;
            String hasil5;
            String hasil6;
            String hasil7;
            String hasil8;
            String hasil9;
            String hasil10;


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    // Iterate through all posts with the same author
                    hasil=post.child("namalengkap").getValue().toString();
                    hasil2=post.child("spesifikasi1").getValue().toString();
                    hasil3=post.child("spesifikasi2").getValue().toString();
                    hasil4=post.child("spesifikasi3").getValue().toString();
                    hasil5=post.child("spesifikasi4").getValue().toString();
                    hasil6=post.child("spesifikasi5").getValue().toString();
                    hasil7=post.child("spesifikasi6").getValue().toString();
                    hasil8=post.child("posisi").getValue().toString();
                    hasil9=post.child("alamatlengkap").getValue().toString();
                    hasil10=post.child("sertifikasi").getValue().toString();
                }
                String lengkap="";
                if (!hasil2.equals("0")){
                    if(lengkap.equals("")){
                        lengkap=lengkap+hasil2;
                    }
                    else{
                        lengkap=lengkap+", "+hasil2;
                    }
                }
                if (!hasil3.equals("0")){
                    if(lengkap.equals("")){
                        lengkap=lengkap+hasil3;
                    }
                    else{
                        lengkap=lengkap+", "+hasil3;
                    }
                }
                if (!hasil4.equals("0")){
                    if(lengkap.equals("")){
                        lengkap=lengkap+hasil4;
                    }
                    else{
                        lengkap=lengkap+", "+hasil4;
                    }
                }
                if (!hasil5.equals("0")){
                    if(lengkap.equals("")){
                        lengkap=lengkap+hasil5;
                    }
                    else{
                        lengkap=lengkap+", "+hasil5;
                    }
                }
                if (!hasil6.equals("0")){
                    if(lengkap.equals("")){
                        lengkap=lengkap+hasil6;
                    }
                    else{
                        lengkap=lengkap+", "+hasil6;
                    }
                }
                if (!hasil7.equals("0")){
                    if(lengkap.equals("")){
                        lengkap=lengkap+hasil7;
                    }
                    else{
                        lengkap=lengkap+", "+hasil7;
                    }
                }
                nmlkp.setText(hasil);
                spek.setText(lengkap);
                pos.setText(hasil8);
                alamat.setText(hasil9);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
