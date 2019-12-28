package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class DetilProyekActivity extends AppCompatActivity {
    private TextView owener;
    private TextView nama;
    private TextView jenis;
    private TextView lingkup;
    private TextView mulai;
    private TextView selesai;
    private TextView alamat;
    private TextView t[]=new TextView[10];;
    private ConstraintLayout constraint;
    private ConstraintSet ctr;
    private int j=0;
    private int count=0;
    private String spek;
    private String pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_proyek);

//        TextView vieww=findViewById(R.id.textView28);
        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("email");

        owener=findViewById(R.id.textView29);
        nama=findViewById(R.id.textView31);
        jenis=findViewById(R.id.textView33);
        lingkup=findViewById(R.id.textView35);
        mulai=findViewById(R.id.textView37);
        selesai=findViewById(R.id.textView39);
        constraint=findViewById(R.id.constraintt);
        ctr=new ConstraintSet();

        Button edit=findViewById(R.id.button28);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gg=new Intent(DetilProyekActivity.this, EditProyekActivity.class);
                gg.putExtra("email", pessan);
                startActivity(gg);;
            }
        });

        Button delete=findViewById(R.id.button29);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek");
                res.child(pessan).removeValue();
            }
        });
//        vieww.setText(pessan);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan);
        ref.child("uid").addValueEventListener(new ValueEventListener() {
            String uid;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid=dataSnapshot.getValue().toString();
                DatabaseReference hey= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                hey.child("namadepan").addValueEventListener(new ValueEventListener() {
                    String ow;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ow=dataSnapshot.getValue().toString();
                        owener.setText(ow);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("namaproyek").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                nama.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jenisproyek").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                jenis.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tipe").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                lingkup.setText(key);
                getspek(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tanggalmulai").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                mulai.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tanggalselesai").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                selesai.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah1").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view=findViewById(R.id.textView67);
                TextView view2=findViewById(R.id.textView61);
                TextView view3=findViewById(R.id.textView72);

                if(key.equals("0")){
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
                else{
                    view.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah2").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view=findViewById(R.id.textView68);
                TextView view2=findViewById(R.id.textView62);
                TextView view3=findViewById(R.id.textView73);

                if(key.equals("0")){
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
                else{
                    view.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah3").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view=findViewById(R.id.textView69);
                TextView view2=findViewById(R.id.textView63);
                TextView view3=findViewById(R.id.textView74);

                if(key.equals("0")){
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
                else{
                    view.setText(key);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah4").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view=findViewById(R.id.textView70);
                TextView view2=findViewById(R.id.textView64);
                TextView view3=findViewById(R.id.textView75);

                if(key.equals("0")){
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
                else{
                    view.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah5").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view=findViewById(R.id.textView71);
                TextView view2=findViewById(R.id.textView65);
                TextView view3=findViewById(R.id.textView76);

                if(key.equals("0")){
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }
                else{
                    view.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek1").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ow=dataSnapshot.getValue().toString();
                getpos(ow);
                ow=ow.replace("+", "\n");
                TextView vieww=findViewById(R.id.textView61);
                vieww.setText(ow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek2").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ow=dataSnapshot.getValue().toString();
                getpos(ow);
                ow=ow.replace("+", "\n");
                TextView vieww=findViewById(R.id.textView62);
                vieww.setText(ow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek3").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ow=dataSnapshot.getValue().toString();
                getpos(ow);
                ow=ow.replace("+", "\n");
                TextView vieww=findViewById(R.id.textView63);
                vieww.setText(ow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek4").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ow=dataSnapshot.getValue().toString();
                getpos(ow);
                ow=ow.replace("+", "\n");
                TextView vieww=findViewById(R.id.textView64);
                vieww.setText(ow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek5").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ow=dataSnapshot.getValue().toString();
                getpos(ow);
                ow=ow.replace("+", "\n");
                TextView vieww=findViewById(R.id.textView65);
                vieww.setText(ow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        final TextView pindah=findViewById(R.id.textView72);
        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah1");
                startActivity(pindahh);
            }
        });

        final TextView pindah2=findViewById(R.id.textView73);
        pindah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah2");
                startActivity(pindahh);
            }
        });

        ref.child("jumlah1f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view3=findViewById(R.id.textView72);

                if(key.equals("0")){
                    view3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah2f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view3=findViewById(R.id.textView73);

                if(key.equals("0")){
                    view3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah3f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view3=findViewById(R.id.textView74);

                if(key.equals("0")){
                    view3.setVisibility(View.GONE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah4f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view3=findViewById(R.id.textView75);

                if(key.equals("0")){
                    view3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah5").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                TextView view3=findViewById(R.id.textView76);

                if(key.equals("0")){
                    view3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final TextView pindah3=findViewById(R.id.textView74);
        pindah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah3");
                startActivity(pindahh);
            }
        });

        final TextView pindah4=findViewById(R.id.textView75);
        pindah4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah4");
                startActivity(pindahh);
            }
        });

        final TextView pindah5=findViewById(R.id.textView76);
        pindah5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah5");
                startActivity(pindahh);
            }
        });

    }

    public void getspek(String setring){
        spek=setring;
    }
    public void getpos(String setring){
        pos=setring;
    }
}
