package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SMAWListActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private String[] item, item2, item3;
    private TextView pesan;
    private String spek;
    private String tipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smawlist);

        final ProgressBar barbar=findViewById(R.id.progressBar);
        barbar.setVisibility(View.VISIBLE);
        pesan=findViewById(R.id.textView3);

        Bundle bundle=getIntent().getExtras();
        String pessan=bundle.getString("pesan");
        tipe=bundle.getString("tipe");

        pesan.setText(pessan);
        if(tipe.equals("welder")){
            //Get your reference to the node with all the entries
            ref = FirebaseDatabase.getInstance().getReference().child("Welders");

            // Query for all entries with a certain child with value equal to something
            // Query allPostFromAuthor = ref.orderByChild("uid").equalTo("KfUrg1c0UNSiFBKMUvfBqI52Nm23");
            if (pessan.equals("SMAW")){
                spek="spesifikasi1";
            }
            if (pessan.equals("FCAW")){
                spek="spesifikasi2";
            }
            if (pessan.equals("GTAW")){
                spek="spesifikasi3";
            }
            if (pessan.equals("SMAW/GTAW")){
                spek="spesifikasi5";
            }
            if (pessan.equals("SMAW/FCAW")){
                spek="spesifikasi4";
            }
            if (pessan.equals("OAW")){
                spek="spesifikasi6";
            }

            Query allPostFromAuthor = ref.orderByChild(spek).equalTo(pessan);
            // Add listener for Firebase response on said query
            allPostFromAuthor.addValueEventListener( new ValueEventListener(){
                ArrayList<String> hasil=new ArrayList<>();
                ArrayList<String> hasil2=new ArrayList<>();
                ArrayList<String> hasil3=new ArrayList<>();
                String putty;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    hasil.clear();
                    hasil2.clear();
                    hasil3.clear();
                    for(DataSnapshot post : dataSnapshot.getChildren() ){
                        // Iterate through all posts with the same author
                        hasil.add(post.child("namalengkap").getValue().toString());
                        hasil2.add(post.child("posisi").getValue().toString());
                        hasil3.add(post.getKey());
                    }
                    if(hasil.size()>0){
                        ListView lisst=findViewById(R.id.list);

                        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(), hasil, hasil2, hasil3, tipe);
                        customAdapter.notifyDataSetChanged();
                        barbar.setVisibility(View.INVISIBLE);
                        lisst.setAdapter(customAdapter);
                    }
                    else{
                        LinearLayout layut=findViewById(R.id.linearLayout);
                        layut.setVisibility(View.INVISIBLE);
                        barbar.setVisibility(View.INVISIBLE);
                        TextView kosong =findViewById(R.id.textView25);
                        kosong.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        if (tipe.equals("proyek")){
            ref = FirebaseDatabase.getInstance().getReference().child("Proyek");

            Query allPostFromAuthor = ref.orderByChild("jenisproyek").equalTo(pessan);
            // Add listener for Firebase response on said query
            allPostFromAuthor.addValueEventListener( new ValueEventListener(){
                ArrayList<String> hasil=new ArrayList<>();
                ArrayList<String> hasil2=new ArrayList<>();
                ArrayList<String> hasil3=new ArrayList<>();
                String putty;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    hasil.clear();
                    hasil2.clear();
                    hasil3.clear();
                    for(DataSnapshot post : dataSnapshot.getChildren() ){
                        // Iterate through all posts with the same author
                        hasil.add(post.child("namaproyek").getValue().toString());
                        hasil2.add(post.child("tipe").getValue().toString());
                        hasil3.add(post.getKey());
                    }
                    if(hasil.size()>0){

                        ListView lisst=findViewById(R.id.list);

                        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(), hasil, hasil2, hasil3, tipe);
                        customAdapter.notifyDataSetChanged();
                        barbar.setVisibility(View.INVISIBLE);
                        lisst.setAdapter(customAdapter);
                    }
                    else{
                        LinearLayout layut=findViewById(R.id.linearLayout);
                        layut.setVisibility(View.INVISIBLE);
                        barbar.setVisibility(View.INVISIBLE);
                        TextView kosong =findViewById(R.id.textView25);
                        kosong.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        if(tipe.equals("pengguna")){
            pesan.setText(tipe);
            ref = FirebaseDatabase.getInstance().getReference().child("Users");

            // Add listener for Firebase response on said query
            ref.addValueEventListener( new ValueEventListener(){
                ArrayList<String> hasil=new ArrayList<>();
                ArrayList<String> hasil2=new ArrayList<>();
                ArrayList<String> hasil3=new ArrayList<>();
                String putty;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    hasil.clear();
                    hasil2.clear();
                    hasil3.clear();
                    for(DataSnapshot post : dataSnapshot.getChildren() ){
                        // Iterate through all posts with the same author
                        hasil.add(post.child("username").getValue().toString());
                        hasil2.add(post.child("notelp").getValue().toString());
                        hasil3.add(post.getKey());
                    }
                    if(hasil.size()>0){

                        ListView lisst=findViewById(R.id.list);

                        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(), hasil, hasil2, hasil3, tipe);
                        customAdapter.notifyDataSetInvalidated();
                        customAdapter.notifyDataSetChanged();
                        barbar.setVisibility(View.INVISIBLE);
                        lisst.setAdapter(customAdapter);
                    }
                    else{
                        LinearLayout layut=findViewById(R.id.linearLayout);
                        layut.setVisibility(View.INVISIBLE);
                        barbar.setVisibility(View.INVISIBLE);
                        TextView kosong =findViewById(R.id.textView25);
                        kosong.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }



    }
}
