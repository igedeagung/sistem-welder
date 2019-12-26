package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private ImageView imagee;
    private Button buttonacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_welderctivity);

        Bundle bundle=getIntent().getExtras();
        String pessan=bundle.getString("email");
        TextView view=findViewById(R.id.textView8);
        view.setText(pessan);

        nmlkp=findViewById(R.id.textView8);
        id=findViewById(R.id.textView10);
        spek=findViewById(R.id.textView14);
        proses=findViewById(R.id.textView12);
        pos=findViewById(R.id.textView16);
        tgl=findViewById(R.id.textView18);
        alamat=findViewById(R.id.textView20);
        imagee=findViewById(R.id.imageView);

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
            String hasil11;
            String hasil12;
            String hasil13;
            String key;
            String sertiff;

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
                    hasil11=post.child("id").getValue().toString();
                    hasil12=post.child("tanggalmasuk").getValue().toString();
                    hasil13=post.child("acc").getValue().toString();
                    key=post.getKey();
                    sertiff=post.child("sertifikasi").getValue().toString();

                }
                String ext="";
                ext=sertiff.substring(sertiff.lastIndexOf(".")+1);

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
                if(hasil8.contains("6G")){
                    if(hasil8.contains("4G")){
                        spek.setText("Pelat + Pipa");
                    }
                    else{
                        spek.setText("Pipa");
                    }
                }
                else{
                    spek.setText("Pelat/Profil");
                }

                buttonacc=findViewById(R.id.button7);
                if(hasil13.equals("1")){
                    buttonacc.setVisibility(View.GONE);
                }
                buttonacc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        ref.child(key).child("tanggalmasuk").setValue(date);
                        ref.child(key).child("id").setValue("1");
                        ref.child(key).child("acc").setValue(1);
                    }
                });

                FirebaseStorage stg=FirebaseStorage.getInstance();
                StorageReference stgrf=stg.getReferenceFromUrl("gs://sistem-7f12e.appspot.com/sertifikasi_welder/").child(sertiff);
                try {
                    final File file=File.createTempFile("image", ext);
                    stgrf.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                            imagee.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetilWelderctivity.this, "hahashakdx", Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (IOException e){
                    e.printStackTrace();
                }

                tgl.setText(hasil12);
                nmlkp.setText(hasil);
                proses.setText(lengkap);
                pos.setText(hasil8);
                alamat.setText(hasil9);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}
