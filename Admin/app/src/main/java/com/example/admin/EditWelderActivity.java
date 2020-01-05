package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class EditWelderActivity extends AppCompatActivity {
    private EditText spes;
    private EditText pos;
    private EditText sertif;
    private EditText nmlengkap;
    private EditText idwelder;
    private EditText almtlkp;
    private EditText tglmsk;
    private Button btnsubmit;
    private Button btnback;
    private String[] listItems;
    private String[] listItems2;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private Uri filePath;
    private Uri filePath2;
    private ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private String nmlkp;
    private String alamlkp;
    private String tanggalms;
    private String spesifik;
    private String posisi;
    private String aidi;
    private String sertifikasi;
    private String spek1="0";
    private String spek2="0";
    private String spek3="0";
    private String spek4="0";
    private String spek5="0";
    private String spek6="0";
    private String pessan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_welder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        pessan=bundle.getString("email");
        spes = (EditText) findViewById(R.id.editText6);
        pos = (EditText) findViewById(R.id.editText7);
        nmlengkap = (EditText) findViewById(R.id.editText2);
        idwelder = (EditText) findViewById(R.id.editText3);
        almtlkp = (EditText) findViewById(R.id.editText4);

        btnsubmit=findViewById(R.id.button);
        btnback=findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Welders");

        listItems = getResources().getStringArray(R.array.pros);
        listItems2 = getResources().getStringArray(R.array.ting);
        checkedItems = new boolean[listItems.length];

        database.child(pessan).child("namalengkap").addListenerForSingleValueEvent(new ValueEventListener() {
            String keys;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys=dataSnapshot.getValue().toString();
                nmlengkap.setText(keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child(pessan).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
            String keys;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys=dataSnapshot.getValue().toString();
                idwelder.setText(keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child(pessan).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                FirebaseDatabase.getInstance().getReference().child("Welders").orderByChild("email").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    String spekk1, spekk2, spekk3, spekk4, spekk5, spekk6;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            spekk1=post.child("spesifikasi1").getValue().toString();
                            spekk2=post.child("spesifikasi2").getValue().toString();
                            spekk3=post.child("spesifikasi3").getValue().toString();
                            spekk4=post.child("spesifikasi4").getValue().toString();
                            spekk5=post.child("spesifikasi5").getValue().toString();
                            spekk6=post.child("spesifikasi6").getValue().toString();

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
                            spes.setText(lengkaps);
                        }
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

        database.child(pessan).child("alamatlengkap").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                almtlkp.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child(pessan).child("posisi").addListenerForSingleValueEvent(new ValueEventListener() {
            String keyss;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyss=dataSnapshot.getValue().toString();
                pos.setText(keyss);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditWelderActivity.this);
                mBuilder.setTitle("Spesifikasi Proses Welding");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            if (listItems[mUserItems.get(i)].equals("SMAW")){
                                spek1="SMAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("FCAW")){
                                spek2="FCAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("GTAW")){
                                spek3="GTAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("SMAW/FCAW")){
                                spek4="SMAW/FCAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("SMAW/GTAW")){
                                spek5="SMAW/GTAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("OAW")){
                                spek6="OAW";
                            }
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        spes.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            spes.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditWelderActivity.this);
                mBuilder.setTitle("Posisi Pengelasan");
                mBuilder.setSingleChoiceItems(listItems2, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pos.setText(listItems2[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmlkp=nmlengkap.getText().toString();
                alamlkp=almtlkp.getText().toString();
                aidi=idwelder.getText().toString();
                spesifik=spes.getText().toString();
                posisi=pos.getText().toString();
                if (TextUtils.isEmpty(nmlkp)){
                    nmlengkap.setError("Nama Lengkap harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(alamlkp)){
                    almtlkp.setError("Alamat Lengkap harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(spesifik)){
                    spes.setError("Spesifikasi harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(posisi)){
                    pos.setError("Posisi harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(aidi)){
                    idwelder.setError("ID Welder harus diisi");
                    return;
                }
                String[] hasil=spesifik.split(", ");

                for(int i=0; i< hasil.length; i++){
                    if (hasil[i].equals("SMAW")){
                        spek1="SMAW";
                    }
                    if (hasil[i].equals("FCAW")){
                        spek2="FCAW";
                    }
                    if (hasil[i].equals("GTAW")){
                        spek3="GTAW";
                    }
                    if (hasil[i].equals("SMAW/FCAW")){
                        spek4="SMAW/FCAW";
                    }
                    if (hasil[i].equals("SMAW/GTAW")){
                        spek5="SMAW/GTAW";
                    }
                    if (hasil[i].equals("OAW")){
                        spek6="OAW";
                    }
                }
                final DatabaseReference id_db = database.child(pessan);
                id_db.child("namalengkap").setValue(nmlkp);
                id_db.child("alamatlengkap").setValue(alamlkp);
                id_db.child("spesifikasi1").setValue(spek1);
                id_db.child("spesifikasi2").setValue(spek2);
                id_db.child("spesifikasi3").setValue(spek3);
                id_db.child("spesifikasi4").setValue(spek4);
                id_db.child("spesifikasi5").setValue(spek5);
                id_db.child("spesifikasi6").setValue(spek6);
                id_db.child("posisi").setValue(posisi);
                id_db.child("id").setValue(aidi);
                Toast.makeText(EditWelderActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();

            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
