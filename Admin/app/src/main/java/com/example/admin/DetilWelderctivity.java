package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetilWelderctivity extends AppCompatActivity {
    private DatabaseReference ref;
    private TextView nmlkp;
    private TextView id;
    private TextView proses;
    private TextView spek;
    private TextView pos;
    private TextView tgl;
    private TextView alamat;
    private TextView reting;
    private ImageView imagee;
    private Button buttonacc;
    private FirebaseAuth mauth;
    private String ratting;
    private double loh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_welderctivity);

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
                    Button btn=findViewById(R.id.button33);
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("email");

        buttonacc=findViewById(R.id.button7);
        nmlkp=findViewById(R.id.textView8);
        id=findViewById(R.id.textView10);
        spek=findViewById(R.id.textView14);
        proses=findViewById(R.id.textView12);
        pos=findViewById(R.id.textView16);
        tgl=findViewById(R.id.textView18);
        alamat=findViewById(R.id.textView20);
        reting=findViewById(R.id.textView26);
        imagee=findViewById(R.id.imageView);


        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("Welders").child(pessan);
        FirebaseDatabase.getInstance().getReference().child("Transaksi").orderByChild("wid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> key=new ArrayList<>();
            ArrayList<String> key1=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key.clear();
                key1.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    key.add(post.child("pid").getValue().toString());
                }
                if(key.size()>0){
                    for(int i=0; i<key.size(); i++){
                        final DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek").child(key.get(i));
                        res.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                            String kes;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                kes=dataSnapshot.getValue().toString();
                                if(kes.equals("1")){
                                    res.child("sudahnilai").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            key1.add(dataSnapshot.getValue().toString());
                                            loh=0;
                                            for(int i=0; i<key1.size(); i++){
                                                loh+=Integer.parseInt(key1.get(i));
                                            }
                                            if(key1.size()>0){
                                                loh=loh/(key1.size());
                                                reting.setText(String.format("%.1f", loh));
                                            }
                                            else{
                                                reting.setText("0.0");
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
//                        reting.setText(key1.get(0));
                    }
                }
                else{
                    reting.setText("0.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("acc").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                if(key.equals("1")){
                    buttonacc.setVisibility(View.GONE);
                }
                buttonacc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(pessan).child("tanggalmasuk").setValue(date);
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(pessan).child("id").setValue("1");
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(pessan).child("acc").setValue(1);
                        buttonacc.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2.child("namalengkap").addValueEventListener(new ValueEventListener() {
            String keys;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    keys=dataSnapshot.getValue().toString();
                    nmlkp.setText(keys);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("id").addValueEventListener(new ValueEventListener() {
            String keys;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    keys=dataSnapshot.getValue().toString();
                    id.setText(keys);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("email").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    key=dataSnapshot.getValue().toString();

                    FirebaseDatabase.getInstance().getReference().child("Welders").orderByChild("email").equalTo(key).addValueEventListener(new ValueEventListener() {
                        String spekk1, spekk2, spekk3, spekk4, spekk5, spekk6;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                for (DataSnapshot post : dataSnapshot.getChildren()) {
                                    spekk1=post.child("spesifikasi1").getValue().toString();
                                    spekk2=post.child("spesifikasi2").getValue().toString();
                                    spekk3=post.child("spesifikasi3").getValue().toString();
                                    spekk4=post.child("spesifikasi4").getValue().toString();
                                    spekk5=post.child("spesifikasi5").getValue().toString();
                                    spekk6=post.child("spesifikasi6").getValue().toString();
                                }
                                String lengkaps="";
                                if (!spekk1.equals("0")){
                                    if(lengkaps.equals("")){
                                        lengkaps=lengkaps+spekk1;
                                    }
                                    else{
                                        lengkaps=lengkaps+", "+spekk1;
                                    }
                                }
                                if (!spekk2.equals("0")){
                                    if(lengkaps.equals("")){
                                        lengkaps=lengkaps+spekk2;
                                    }
                                    else{
                                        lengkaps=lengkaps+", "+spekk2;
                                    }
                                }
                                if (!spekk3.equals("0")){
                                    if(lengkaps.equals("")){
                                        lengkaps=lengkaps+spekk3;
                                    }
                                    else{
                                        lengkaps=lengkaps+", "+spekk3;
                                    }
                                }
                                if (!spekk4.equals("0")){
                                    if(lengkaps.equals("")){
                                        lengkaps=lengkaps+spekk4;
                                    }
                                    else{
                                        lengkaps=lengkaps+", "+spekk4;
                                    }
                                }
                                if (!spekk5.equals("0")){
                                    if(lengkaps.equals("")){
                                        lengkaps=lengkaps+spekk5;
                                    }
                                    else{
                                        lengkaps=lengkaps+", "+spekk5;
                                    }
                                }
                                if (!spekk6.equals("0")){
                                    if(lengkaps.equals("")){
                                        lengkaps=lengkaps+spekk6;
                                    }
                                    else{
                                        lengkaps=lengkaps+", "+spekk6;
                                    }
                                }
                                proses.setText(lengkaps);
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

        ref2.child("posisi").addValueEventListener(new ValueEventListener() {
            String keyss;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    keyss=dataSnapshot.getValue().toString();
                    if(keyss.contains("6G")){
                        if(keyss.contains("4G")){
                            spek.setText("Pelat + Pipa");
                        }
                        else{
                            spek.setText("Pipa");
                        }
                    }
                    else{
                        spek.setText("Pelat/Profil");
                    }
                    pos.setText(keyss);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("tanggalmasuk").addValueEventListener(new ValueEventListener() {
            String heyy;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    heyy=dataSnapshot.getValue().toString();
                    tgl.setText(heyy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2.child("alamatlengkap").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    alamat.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasi").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    final String[] hasil=key.split(", ");
                    FirebaseStorage stg=FirebaseStorage.getInstance();
                    for(int i=0; i<hasil.length; i++){
                        final int juml=i;
                        StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder/").child(hasil[i]);
                        try {
                            final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                            stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                    ImageView imagi=new ImageView(DetilWelderctivity.this);
                                    imagi.setImageBitmap(bitmap);
                                    imagi.setId(juml);

                                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                                    mylayout.addView(imagi);

                                    ConstraintSet set=new ConstraintSet();
                                    set.clone(mylayout);
                                    set.connect(R.id.textView27, ConstraintSet.BOTTOM, R.id.textView28, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                    set.constrainHeight(imagi.getId(), 400);
                                    set.constrainWidth(imagi.getId(), 300);

                                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView27, ConstraintSet.BOTTOM, 5+(400*(juml)));

                                    set.applyTo(mylayout);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasimarine").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=1000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView28, ConstraintSet.BOTTOM, R.id.textView30, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView28, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+100;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_marine/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView28, ConstraintSet.BOTTOM, R.id.textView30, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView28, ConstraintSet.BOTTOM, 5+(400*(juml-100)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=1000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView28, ConstraintSet.BOTTOM, R.id.textView30, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView28, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasilr").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=4000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView30, ConstraintSet.BOTTOM, R.id.textView31, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView30, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+300;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_lr/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView30, ConstraintSet.BOTTOM, R.id.textView31, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView30, ConstraintSet.BOTTOM, 5+(400*(juml-300)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=4000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView30, ConstraintSet.BOTTOM, R.id.textView31, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView30, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2.child("sertifikasibv").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=5000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView31, ConstraintSet.BOTTOM, R.id.textView32, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView31, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+400;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_bv/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView31, ConstraintSet.BOTTOM, R.id.textView32, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView31, ConstraintSet.BOTTOM, 5+(400*(juml-400)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }

                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=5000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView31, ConstraintSet.BOTTOM, R.id.textView32, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView31, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasimigas").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=6000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView32, ConstraintSet.BOTTOM, R.id.textView33, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView32, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+500;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_migas/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView32, ConstraintSet.BOTTOM, R.id.textView33, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView32, ConstraintSet.BOTTOM, 5+(400*(juml-500)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=6000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView32, ConstraintSet.BOTTOM, R.id.textView33, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView32, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2.child("sertifikasioffshore").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=7000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView33, ConstraintSet.BOTTOM, R.id.textView34, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView33, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+600;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_offshore/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView33, ConstraintSet.BOTTOM, R.id.textView34, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView33, ConstraintSet.BOTTOM, 5+(400*(juml-600)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=7000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView33, ConstraintSet.BOTTOM, R.id.textView34, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView33, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasikapal").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=8000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView34, ConstraintSet.BOTTOM, R.id.textView35, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView34, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+700;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_kapal/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView34, ConstraintSet.BOTTOM, R.id.textView35, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView34, ConstraintSet.BOTTOM, 5+(400*(juml-700)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=8000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView34, ConstraintSet.BOTTOM, R.id.textView35, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView34, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasisteel").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=9000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView35, ConstraintSet.BOTTOM, R.id.textView36, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView35, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+800;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_steel/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView35, ConstraintSet.BOTTOM, R.id.textView36, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView35, ConstraintSet.BOTTOM, 5+(400*(juml-800)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=9000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView35, ConstraintSet.BOTTOM, R.id.textView36, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView35, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2.child("sertifikasipipa").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=10000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView36, ConstraintSet.BOTTOM, R.id.textView37, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView36, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+900;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_pipa/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView36, ConstraintSet.BOTTOM, R.id.textView37, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView36, ConstraintSet.BOTTOM, 5+(400*(juml-900)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=10000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView36, ConstraintSet.BOTTOM, R.id.textView37, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView36, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasiindustri").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=11000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView37, ConstraintSet.BOTTOM, R.id.textView29, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView37, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+1001;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_industri/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView37, ConstraintSet.BOTTOM, R.id.textView29, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView37, ConstraintSet.BOTTOM, 5+(400*(juml-1001)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=11000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView37, ConstraintSet.BOTTOM, R.id.textView29, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView37, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2.child("sertifikasitoefl").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    if(key.equals("0")){
                        TextView imagi=new TextView(DetilWelderctivity.this);
                        imagi.setText("Tidak Ada");
                        int id=2000;
                        imagi.setId(id);

                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                        mylayout.addView(imagi);

                        ConstraintSet set=new ConstraintSet();
                        set.clone(mylayout);
                        set.connect(R.id.textView29, ConstraintSet.BOTTOM, R.id.textView23, ConstraintSet.TOP, 50);

                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView29, ConstraintSet.BOTTOM, 5);

                        set.applyTo(mylayout);
                    }
                    else{
                        final String[] hasil=key.split(", ");
                        FirebaseStorage stg=FirebaseStorage.getInstance();
                        for(int i=0; i<hasil.length; i++){
                            final int juml=i+200;
                            StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder_toefl/").child(hasil[i]);
                            try {
                                final File file=File.createTempFile("image", hasil[i].substring(hasil[i].lastIndexOf(".")+1));
                                stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                        ImageView imagi=new ImageView(DetilWelderctivity.this);
                                        imagi.setImageBitmap(bitmap);
                                        imagi.setId(juml);

                                        ConstraintLayout mylayout= findViewById(R.id.parentt);
                                        mylayout.addView(imagi);

                                        ConstraintSet set=new ConstraintSet();
                                        set.clone(mylayout);
                                        set.connect(R.id.textView29, ConstraintSet.BOTTOM, R.id.textView23, ConstraintSet.TOP, 30+(400*(hasil.length)));

                                        set.constrainHeight(imagi.getId(), 400);
                                        set.constrainWidth(imagi.getId(), 300);

                                        set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                                        set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView29, ConstraintSet.BOTTOM, 5+(400*(juml-200)));

                                        set.applyTo(mylayout);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=2000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView29, ConstraintSet.BOTTOM, R.id.textView23, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView29, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.child("sertifikasiijasah").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    final String[] hasil=key.split(", ");
                    FirebaseStorage stg=FirebaseStorage.getInstance();

                        StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/ijasah_welder/").child(key);
                        try {
                            final File file=File.createTempFile("image", key.substring(key.lastIndexOf(".")+1));
                            stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                                    imagee.setImageBitmap(bitmap);
                                    imagee.setVisibility(View.VISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetilWelderctivity.this, "Sertifikasi tidak valid", Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                }
                else{
                    TextView imagi=new TextView(DetilWelderctivity.this);
                    imagi.setText("Tidak Ada");
                    int id=3000;
                    imagi.setId(id);

                    ConstraintLayout mylayout= findViewById(R.id.parentt);
                    mylayout.addView(imagi);

                    ConstraintSet set=new ConstraintSet();
                    set.clone(mylayout);
                    set.connect(R.id.textView23, ConstraintSet.BOTTOM, R.id.imageView, ConstraintSet.TOP, 50);

                    set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                    set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.textView23, ConstraintSet.BOTTOM, 5);

                    set.applyTo(mylayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button del=findViewById(R.id.button33);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Welders").child(pessan).child("sertifikasi").addListenerForSingleValueEvent(new ValueEventListener() {
                    String aku;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        aku=dataSnapshot.getValue().toString();
                        FirebaseStorage.getInstance().getReference().child("sertifikasi_welder/"+aku).delete();
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(pessan).child("profil").addListenerForSingleValueEvent(new ValueEventListener() {
                            String uka;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                uka=dataSnapshot.getValue().toString();
                                FirebaseStorage.getInstance().getReference().child("profil_welder/"+uka).delete();
                                FirebaseDatabase.getInstance().getReference().child("Transaksi").orderByChild("wid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
                                    List<String> hh=new ArrayList<>();
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot post : dataSnapshot.getChildren() ){
                                            hh.add(post.getKey());
                                        }
                                        for(int i=0; i<hh.size(); i++){
                                            FirebaseDatabase.getInstance().getReference().child("Transaksi").child(hh.get(i)).removeValue();
                                        }
                                        DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Welders");
                                        res.child(pessan).removeValue();
                                        finish();
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

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        Button edit=findViewById(R.id.button32);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah=new Intent(DetilWelderctivity.this, EditWelderActivity.class);
                pindah.putExtra("email", pessan);
                startActivity(pindah);
            }
        });

    }
}
