package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CariWelderActivity extends AppCompatActivity {

    private String spekk;
    private String[] item, item2, item4;
    String item3, item5;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_welder);

        Bundle bundle=getIntent().getExtras();
        String pessan=bundle.getString("spek");
        String pessan2=bundle.getString("pos");
        final String pessan3=bundle.getString("key");
        final String julah=bundle.getString("jumlahnya");

        if(pessan2.contains("6GR")){
            flag=3;
        }
        else if(pessan2.contains("6G")){
            flag=2;
        }
        else{
            flag=1;
        }

        if(pessan.equals("SMAW")){
            spekk="spesifikasi1";
        }
        if(pessan.equals("FCAW")){
            spekk="spesifikasi2";
        }
        if(pessan.equals("GTAW")){
            spekk="spesifikasi3";
        }
        if(pessan.equals("SMAW/FCAW")){
            spekk="spesifikasi4";
        }
        if(pessan.equals("SMAW/GTAW")){
            spekk="spesifikasi5";
        }
        if(pessan.equals("OAW")){
            spekk="spesifikasi6";
        }
        DatabaseReference res= FirebaseDatabase.getInstance().getReference().child("Welders");

        res.orderByChild(spekk).equalTo(pessan).addValueEventListener(new ValueEventListener() {
            String acc;
            String sibuk;
            String pos;
            List<String> hasil=new ArrayList<>();
            List<String> hasil2=new ArrayList<>();
            List<String> hasil3=new ArrayList<>();
            ProgressBar barbar=findViewById(R.id.progressBar3);

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren() ){
                    // Iterate through all posts with the same author
                    acc=post.child("acc").getValue().toString();
                    sibuk=post.child("pid").getValue().toString();
                    pos=post.child("posisi").getValue().toString();
                    int flagg;
                    if(pos.contains("6GR")){
                        flagg=3;
                    }
                    else if(pos.contains("6G")){
                        flagg=2;
                    }
                    else{
                        flagg=1;
                    }
                    if(acc.equals("1")&&sibuk.equals("0")&&flagg==flag){
                        hasil.add(post.child("namalengkap").getValue().toString());
                        hasil2.add(post.child("alamatlengkap").getValue().toString());
                        hasil3.add(post.getKey());
                    }
                }
                if(hasil.size()>0){
                    int i ;
                    item=new String[hasil.size()];
                    item2=new String[hasil2.size()];
                    item3=pessan3;
                    item4=new String[hasil3.size()];
                    item5=julah;

                    for(i=0; i<hasil.size(); i++){
                        item[i]=hasil.get(i);
                        item2[i]=hasil2.get(i);
                        item4[i]=hasil3.get(i);
                    }
                    ListView lisst=findViewById(R.id.lilis);

                    CustomAdapter2 customAdapter=new CustomAdapter2(getApplicationContext(), item, item2, item3, item4, item5);
                    barbar.setVisibility(View.INVISIBLE);
                    lisst.setAdapter(customAdapter);
                }
                else{
                    barbar.setVisibility(View.INVISIBLE);
                    TextView kosong =findViewById(R.id.textView43);
                    kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        TextView tex=findViewById(R.id.textView42);
//        tex.setText(pessan);
    }
}
