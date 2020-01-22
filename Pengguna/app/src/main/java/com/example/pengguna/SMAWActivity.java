package com.example.pengguna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SMAWActivity extends AppCompatActivity {
    private String fin1, fin2, fin3;
    private int countsf1=0, countsf2=0, countsf3=0, countsf4=0, countsf5=0;
    private TextView angka1;
    private TextView angka2;
    private TextView angka3;
    private TextView angka4;
    private TextView angka5;
    private EditText mulai, selesai ;
    private Calendar myCalendar ;
    private boolean kliked;
    private TextView hargaField;
    private Button submitt;
    private Button balik;
    private DatabaseReference database;
    private String uid;
    private long beda;
    private EditText alama;

    private String pakaimesin;
    private int flay=0;
    private int jumlh=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_smaw);

        final String proyek1="Posisi 1G 2G (pelat/profil)";
        final String proyek2="Posisi 1G 2G 3G 4G (pelat/profil)";
        final String proyek3="Posisi 1G, 2G, 3G, 4G (pelat)\nPosisi 1G, 2G, 5G, 6G (pipa)";
        final String proyek4="Industri+Posisi 1G 2G 3G 4G (pelat/profil)";
        final String proyek5="Marine+Posisi 1G 2G 3G 4G (pelat/profil)";

        Bundle bundle=getIntent().getExtras();
        fin1=bundle.getString("final1");
        fin2=bundle.getString("final2");
        fin3=bundle.getString("final3");

        angka1=findViewById(R.id.textView26);
        angka2=findViewById(R.id.textView28);
        angka3=findViewById(R.id.textView30);
        angka4=findViewById(R.id.textView74);
        angka5=findViewById(R.id.textView71);

        ConstraintLayout constraintt2=findViewById(R.id.parentsmaw);
        ConstraintSet ctrr = new ConstraintSet();
        FrameLayout frem= findViewById(R.id.sfl1);
        FrameLayout frem2= findViewById(R.id.sfl2);
        FrameLayout frem3= findViewById(R.id.sfl3);
        FrameLayout frem4= findViewById(R.id.frameLayout15);
        FrameLayout frem5= findViewById(R.id.frameLayout14);
        TextView tulisan=findViewById(R.id.textView64);

        mulai = findViewById(R.id.editText10);
        selesai = findViewById(R.id.editText11);
        myCalendar = Calendar.getInstance();

        database = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        hargaField=findViewById(R.id.textView34);

        alama=findViewById(R.id.editText29);
        alama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SMAWActivity.this, MapsActivity.class);
                startActivityForResult(i, 70);
            }
        });
        balik=findViewById(R.id.button29);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitt=findViewById(R.id.button19);
        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah1=angka1.getText().toString();
                String jumlah2=angka2.getText().toString();
                String jumlah3=angka3.getText().toString();
                String jumlah4=angka4.getText().toString();
                String jumlah5=angka5.getText().toString();
                String jumlah1f=jumlah1;
                String jumlah2f=jumlah2;
                String jumlah3f=jumlah3;
                String jumlah4f=jumlah4;
                String jumlah5f=jumlah5;
                String hargaa=hargaField.getText().toString();
                String wid="0";

                String alamat=alama.getText().toString();

                if(TextUtils.isEmpty(alamat)){
                    alama.setError("Alamat harus diisi");
                    alama.requestFocus();
                    return;
                }
                String tmul=mulai.getText().toString();
                String tsel=selesai.getText().toString();
                String tipe="SMAW";
                String namaproyek;
                if(!fin2.equals("yha")){
                    if (!fin3.equals("yha")){
                        namaproyek=fin3;
                    }
                    else{
                        namaproyek=fin2;
                    }
                }
                else{
                    namaproyek=fin1;
                }
                String jenisproyek=fin1;
                String status="0";
                String nilai="0";
                String bdh=Long.toString(beda);
                Proyek proyeku=new Proyek(nilai, bdh, status, alamat, jenisproyek, namaproyek, tipe, proyek1,proyek2, proyek3, proyek4, proyek5,
                        jumlah1f, jumlah2f, jumlah3f, jumlah4f, jumlah5f, jumlah1,
                        jumlah2, jumlah3, jumlah4, jumlah5,tmul, tsel, hargaa, uid, wid);
                Intent pindah= new Intent(SMAWActivity.this, KonfPesanActivity.class);
                pindah.putExtra("proyek",proyeku);
                startActivity(pindah);;
            }
        });

        ImageButton plus1=findViewById(R.id.imageButton2);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf1++;
                String aa=Integer.toString(countsf1);
                angka1.setText(aa);
                update();
            }
        });

        ImageButton plus2=findViewById(R.id.imageButton4);
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf2++;
                String aa=Integer.toString(countsf2);
                angka2.setText(aa);
                update();
            }
        });

        ImageButton plus3=findViewById(R.id.imageButton6);
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf3++;
                String aa=Integer.toString(countsf3);
                angka3.setText(aa);
                update();
            }
        });

        ImageButton plus4=findViewById(R.id.imageButton30);
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf4++;
                String aa=Integer.toString(countsf4);
                angka4.setText(aa);
                update();
            }
        });

        ImageButton plus5=findViewById(R.id.imageButton28);
        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf5++;
                String aa=Integer.toString(countsf5);
                angka5.setText(aa);
                update();
            }
        });

        ImageButton min1=findViewById(R.id.imageButton);
        min1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countsf1>0){
                    countsf1--;
                }
                String aa=Integer.toString(countsf1);
                angka1.setText(aa);
                update();
            }
        });

        ImageButton min2=findViewById(R.id.imageButton3);
        min2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countsf2>0){
                    countsf2--;
                }
                String aa=Integer.toString(countsf2);
                angka2.setText(aa);
                update();
            }
        });

        ImageButton min3=findViewById(R.id.imageButton5);
        min3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countsf3>0){
                    countsf3--;
                }
                String aa=Integer.toString(countsf3);
                angka3.setText(aa);
                update();
            }
        });

        ImageButton min4=findViewById(R.id.imageButton29);
        min4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countsf4>0){
                    countsf4--;
                }
                String aa=Integer.toString(countsf4);
                angka4.setText(aa);
                update();
            }
        });

        ImageButton min5=findViewById(R.id.imageButton27);
        min5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countsf5>0){
                    countsf5--;
                }
                String aa=Integer.toString(countsf5);
                angka5.setText(aa);
                update();
            }
        });

        if(fin1.equals("Steel Structure")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.sfl2, ConstraintSet.BOTTOM, R.id.editText10, ConstraintSet.TOP, 150);
            ctrr.applyTo(constraintt2);

            frem3.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }

        if(fin3.equals("Kapal Ferro")||fin2.equals("Onshore/Offshore")){
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }

        if(fin2.equals("Carbon Steel")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.frameLayout15, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            frem.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
            frem3.setVisibility(View.GONE);
        }

        if(fin2.equals("Stainless Steel")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.sfl2, ConstraintSet.TOP, 0);
            ctrr.connect(R.id.sfl2, ConstraintSet.BOTTOM, R.id.editText10, ConstraintSet.TOP, 150);
            ctrr.applyTo(constraintt2);

            frem.setVisibility(View.GONE);
            frem3.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }

        if(fin2.equals("Pressure Tank")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView64, ConstraintSet.BOTTOM, R.id.sfl3, ConstraintSet.TOP, 50);
            ctrr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView64, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            tulisan.setText("Tanpa menggunakan backing");
            frem.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }

        if(fin2.equals("Storage Tank")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView64, ConstraintSet.BOTTOM, R.id.sfl2, ConstraintSet.TOP, 50);
            ctrr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView64, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            tulisan.setText("Material Carbon Steel");
            frem.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if(kliked){
                    selesai.setText(sdf.format(myCalendar.getTime()));
                    update();
                }
                else {
                    mulai.setText(sdf.format(myCalendar.getTime()));
                    update();
                }

            }
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        selesai.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);

                kliked=true;
                DatePickerDialog datePickerDialog = new DatePickerDialog(SMAWActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        mulai.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                kliked=false;


                DatePickerDialog datePickerDialog = new DatePickerDialog(SMAWActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });
    }

    public void update(){
        String mulaii=mulai.getText().toString();
        String selesaii= selesai.getText().toString();

        Date date=new Date(), date2=new Date();
        //bila tanggal mulai dan tanggal selesai sudah disetting
        if(!mulaii.equals("Tanggal Mulai") && !selesaii.equals("Tanggal Selesai")){
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(mulaii);
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(selesaii);
                beda= (date2.getTime()-date.getTime())/86400000; //menghitung berapa lama proyek
                if(beda<0){
                    selesai.setText("Tanggal Selesai");
                    mulai.setText("Tanggal Mulai");
                    hargaField.setText("0");
                }
                else {

                    //mendapatkan database welder
                    FirebaseDatabase.getInstance().getReference().child("Welders").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //menghitung total welder
                            final double size=(double)dataSnapshot.getChildrenCount();
                            //mendapatkan database welder yang belum mendapatkan proyek
                            FirebaseDatabase.getInstance().getReference().child("Welders").orderByChild("pid").equalTo("0").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //menghitung total welder yang belum mendapatkan proyek
                                    double size2=(double)dataSnapshot.getChildrenCount();
                                    //menghitung rasionya
                                    double rasio=size2/size;
                                    double hp1=0, hp2=0, hp3=0, hp4=0, hp5=0;
                                    //bila rasio < 0.2 akan ada kenaikan harga
                                    if(rasio<0.2){
                                        //menghitung rasio penambahan
                                        //contoh: bila rasio 0.15, maka rasio kenaikan harga:
                                        //1-(0.15/0.2)=1-0.75=0.25
                                        double persen=1-rasio/0.2;
                                        if(fin1.equals("Steel Structure")){ //bila proyek Steel Structure
                                            hp1=(15000+15000*persen)*8; //harga posisi 1G, 2G
                                            hp2=(15000+15000*persen)*8; //harga posisi 1G, 2G, 3G, 4G
                                            if(hp1>160000){
                                                hp1=160000;
                                            }
                                            if(hp2>160000){
                                                hp2=160000;
                                            }
                                        }
                                        if(fin3.equals("Kapal Ferro")){ //bila proyek Kapal Ferro
                                            hp1=(20000+20000*persen)*8; //harga posisi 1G, 2G
                                            hp3=(20000+20000*persen)*8;; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            hp2=(20000+20000*persen)*8;; //harga posisi 1G, 2G, 3G, 4G
                                            //bila harga > 25.000*8=200.000
                                            if(hp1>200000){
                                                hp1=200000;//maksimal 200.000
                                            }
                                            //bila harga > 25.000*8=200.000
                                            if(hp2>200000){
                                                hp2=200000;//maksimal 200.000
                                            }
                                            //bila harga > 25.000*8=200.000
                                            if(hp3>200000){
                                                hp3=200000;//maksimal 200.000
                                            }
                                        }
                                        if (fin2.equals("Onshore/Offshore")){ //bila proyek Onshore/Offshore
                                            hp1=(40000+40000*persen)*12;; //harga posisi 1G, 2G
                                            hp3=(40000+40000*persen)*12; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            hp2=(40000+40000*persen)*12; //harga posisi 1G, 2G, 3G, 4G

                                            if(hp1>540000){
                                                hp1=540000;
                                            }
                                            if(hp2>540000){
                                                hp2=540000;
                                            }
                                            if(hp3>540000){
                                                hp3=540000;
                                            }
                                        }
                                        if(fin2.equals("Carbon Steel")){ //bila proyek Carbon Steel
                                            hp4=(30000+30000*persen)*8; //harga Proyek Industri
                                            hp5=(40000+40000*persen)*8; // harga proyek marine

                                            if(hp4>280000){
                                                hp4=280000;
                                            }
                                            if(hp5>360000){
                                                hp5=360000;
                                            }
                                        }
                                        if(fin2.equals("Stainless Steel")){ //bila proyek Stainless Steel

                                            hp2=(35000+35000*persen)*8; //harga posisi 1G, 2G, 3G, 4G
                                            if(hp2>320000){
                                                hp2=320000;
                                            }
                                        }
                                        if(fin2.equals("Storage Tank")){ //bila proyek Storage Tank
                                            hp3=(20000+20000*persen)*8; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            hp2=(20000+20000*persen)*8; //harga posisi 1G, 2G, 3G, 4G
                                            if(hp3>200000){
                                                hp3=200000;
                                            }
                                            if(hp2>200000){
                                                hp2=200000;
                                            }
                                        }
                                        if(fin2.equals("Pressure Tank")){
                                            hp3=(35000+35000*persen)*8; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            if(hp3>320000){
                                                hp3=320000;
                                            }
                                        }

                                    }
                                    else{
                                        if(fin1.equals("Steel Structure")){ //bila proyek Steel Structure
                                            hp1=120000; //harga posisi 1G, 2G
                                            hp2=120000; //harga posisi 1G, 2G, 3G, 4G
                                        }
                                        if(fin3.equals("Kapal Ferro")){ //bila proyek Kapal Ferro
                                            hp1=160000; //harga posisi 1G, 2G
                                            hp3=160000; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            hp2=160000; //harga posisi 1G, 2G, 3G, 4G
                                        }
                                        if (fin2.equals("Onshore/Offshore")){ //bila proyek Onshore/Offshore
                                            hp1=480000; //harga posisi 1G, 2G
                                            hp3=480000; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            hp2=480000; //harga posisi 1G, 2G, 3G, 4G
                                        }
                                        if(fin2.equals("Carbon Steel")){ //bila proyek Carbon Steel
                                            hp4=240000; //harga Proyek Industri
                                            hp5=320000; // harga proyek marine
                                        }
                                        if(fin2.equals("Stainless Steel")){ //bila proyek Stainless Steel
                                            hp2=280000; //harga posisi 1G, 2G, 3G, 4G
                                        }
                                        if(fin2.equals("Storage Tank")){ //bila proyek Storage Tank
                                            hp3=160000; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                            hp2=160000; //harga posisi 1G, 2G, 3G, 4G
                                        }
                                        if(fin2.equals("Pressure Tank")){
                                            hp3=280000; //harga posisi 1G, 2G, 3G, 4G, 5G, 6G
                                        }
                                    }

                                    // harga welder= (jumlah welder * harga welder) * berapa lama
                                    double harga=(hp1*countsf1+hp2*countsf2+hp3*countsf3+hp4*countsf4+hp5*countsf5+jumlh)*beda;
                                    int hf=(int)harga/1000*1000;
                                    NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                                    String aa=nf3.format(hf);
                                    //harga ditampilkan
                                    hargaField.setText(aa);
                                    String cek=hargaField.getText().toString();
                                    if(cek.equals("0")){
                                        submitt.setEnabled(false);
                                    }
                                    else
                                    {
                                        submitt.setEnabled(true);
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

                }

            } catch (ParseException e) {              // Insert this block.
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case (70):{
                if(resultCode== Activity.RESULT_OK){
                    String newal=data.getStringExtra("alamat");
                    alama.setText(newal);
                }
                break;
            }
        }
    }
}
