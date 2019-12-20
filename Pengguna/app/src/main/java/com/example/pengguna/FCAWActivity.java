package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FCAWActivity extends AppCompatActivity {
    private String fin1, fin2, fin3;
    private EditText mulai, selesai ;
    private Calendar myCalendar ;
    private boolean kliked;
    private int countsf1=0, countsf2=0, countsf3=0, countsf4=0;
    private TextView angka1;
    private TextView angka2;
    private TextView angka3;
    private TextView angka4;
    private TextView hargaField;
    private Button submitt;
    private DatabaseReference database;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_fcaw);

        final String proyek1="Posisi 1G 2G (pelat/profil)";
        final String proyek2="Posisi 1G 2G 3G 4G (pelat/profil)";
        final String proyek3="Posisi 1G, 2G, 3G, 4G (pelat)\nPosisi 1G, 2G, 5G, 6G (pipa)";
        final String proyek4="Posisi 1G, 2G, 3G, 4G (pelat)\nPosisi 1G, 2G, 5G, 6G, 6GR (pipa)";
        final String proyek5="0";

        Bundle bundle=getIntent().getExtras();
        fin1=bundle.getString("final1");
        fin2=bundle.getString("final2");
        fin3=bundle.getString("final3");

        angka1=findViewById(R.id.textView35);
        angka2=findViewById(R.id.textView37);
        angka3=findViewById(R.id.textView39);
        angka4=findViewById(R.id.textView41);

        ConstraintLayout constraintt2=findViewById(R.id.parentfcaw);
        ConstraintSet ctrr = new ConstraintSet();
        FrameLayout frem= findViewById(R.id.ffl1);
        FrameLayout frem2= findViewById(R.id.frameLayout2);
        FrameLayout frem3= findViewById(R.id.frameLayout3);
        FrameLayout frem4= findViewById(R.id.frameLayout4);
        TextView tulisan=findViewById(R.id.textView66);

        mulai = findViewById(R.id.editText12);
        selesai = findViewById(R.id.editText13);
        myCalendar = Calendar.getInstance();

        database = FirebaseDatabase.getInstance().getReference().child("Proyek");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        hargaField=findViewById(R.id.textView43);
        submitt=findViewById(R.id.button20);

        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah1=angka1.getText().toString();
                String jumlah2=angka2.getText().toString();
                String jumlah3=angka3.getText().toString();
                String jumlah4=angka4.getText().toString();
                String jumlah5="0";
                String hargaa=hargaField.getText().toString();
                String wid="0";
                String tmul=mulai.getText().toString();
                String tsel=selesai.getText().toString();
                String tipe="FCAW";

                submitProyek(new Proyek(tipe, proyek1,proyek2, proyek3, proyek4, proyek5, jumlah1,
                        jumlah2, jumlah3, jumlah4, jumlah5,tmul, tsel, hargaa, uid, wid));
            }
        });

        ImageButton plus1=findViewById(R.id.imageButton8);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf1++;
                String aa=Integer.toString(countsf1);
                angka1.setText(aa);
                update();
            }
        });

        ImageButton plus2=findViewById(R.id.imageButton10);
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf2++;
                String aa=Integer.toString(countsf2);
                angka2.setText(aa);
                update();
            }
        });

        ImageButton plus3=findViewById(R.id.imageButton12);
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf3++;
                String aa=Integer.toString(countsf3);
                angka3.setText(aa);
                update();
            }
        });

        ImageButton plus4=findViewById(R.id.imageButton14);
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf4++;
                String aa=Integer.toString(countsf4);
                angka4.setText(aa);
                update();
            }
        });

        ImageButton min1=findViewById(R.id.imageButton7);
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

        ImageButton min2=findViewById(R.id.imageButton9);
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

        ImageButton min3=findViewById(R.id.imageButton11);
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

        ImageButton min4=findViewById(R.id.imageButton13);
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

        if(fin1.equals("Steel Structure") || fin3.equals("Kapal Ferro")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.frameLayout2, ConstraintSet.BOTTOM, R.id.editText12, ConstraintSet.TOP, 50);
            ctrr.applyTo(constraintt2);

            frem3.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
        }

        if(fin2.equals("Onshore/Offshore")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView31, ConstraintSet.BOTTOM, R.id.frameLayout3, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            frem.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
        }

        if(fin2.equals("Pressure Tank")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView66, ConstraintSet.BOTTOM, R.id.frameLayout3, ConstraintSet.TOP, 50);
            ctrr.connect(R.id.textView31, ConstraintSet.BOTTOM, R.id.textView66, ConstraintSet.TOP, 0);
            ctrr.connect(R.id.frameLayout3, ConstraintSet.BOTTOM, R.id.editText12, ConstraintSet.TOP, 50);
            ctrr.applyTo(constraintt2);

            tulisan.setText("Tanpa menggunakan backing");
            frem.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(FCAWActivity.this, date, myCalendar
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(FCAWActivity.this, date, myCalendar
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
        if(!mulaii.equals("Tanggal Mulai") && !selesaii.equals("Tanggal Selesai")){
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(mulaii);
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(selesaii);
                long beda= (date2.getTime()-date.getTime())/86400000;
                if(beda<0){
                    selesai.setText("Tanggal Selesai");
                    mulai.setText("Tanggal Mulai");
                    hargaField.setText("0");
                }
                else {
                    int hp1=0, hp2=0, hp3=0, hp4=0;
                    if(fin1.equals("Steel Structure")){
                        hp1=120000;
                        hp2=120000;
                    }
                    if(fin3.equals("Kapal Ferro")){
                        hp1=160000;
                        hp2=160000;
                    }
                    if(fin2.equals("Onshore/Offshore")){
                        hp3=600000;
                        hp4=600000;
                    }
                    if(fin2.equals("Pressure Tank")){
                        hp3=240000;
                    }

                    long harga=(hp1*countsf1+hp2*countsf2+hp3*countsf3+hp4*countsf4)*beda;
                    String aa=Long.toString(harga);
                    hargaField.setText(aa);
                }
                String cek=hargaField.getText().toString();
                if(cek.equals("0")){
                    submitt.setEnabled(false);
                }
                else
                {
                    submitt.setEnabled(true);
                }
            } catch (ParseException e) {              // Insert this block.
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private void submitProyek(Proyek proyek) {
        if(!fin2.equals("yha")){
            if (!fin3.equals("yha")){
                database.child(fin1).child(fin2).child(fin3).push().setValue(proyek).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.button20), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
            else{
                if (fin2.equals("Onshore/Offshore")){
                    database.child(fin1).child("Onshore Offshore").push().setValue(proyek).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(findViewById(R.id.button20), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    database.child(fin1).child(fin2).push().setValue(proyek).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(findViewById(R.id.button20), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }
        else{
            database.child(fin1).push().setValue(proyek).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Snackbar.make(findViewById(R.id.button20), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}

