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

public class GTAWActivity extends AppCompatActivity {
    private String fin1, fin2, fin3;
    private EditText mulai, selesai ;
    private Calendar myCalendar ;
    private boolean kliked;
    private TextView angka1, angka2, angka3;
    private int countsf1=0, countsf2=0, countsf3=0;
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
        setContentView(R.layout.activity_gtaw);

        final String proyek1="Posisi 1G, 2G, 3G, 4G (pelat)\nPosisi 1G, 2G, 5G, 6G (pipa)";
        final String proyek2="Industri+Posisi 1G, 2G, 3G, 4G (pelat)\nPosisi 1G, 2G, 5G, 6G (pipa)";
        final String proyek3="Marine+Posisi 1G, 2G, 3G, 4G (pelat)\nPosisi 1G, 2G, 5G, 6G (pipa)";
        final String proyek4="0";
        final String proyek5="0";

        Bundle bundle=getIntent().getExtras();
        fin1=bundle.getString("final1");
        fin2=bundle.getString("final2");
        fin3=bundle.getString("final3");

        angka1=findViewById(R.id.textView46);
        angka2=findViewById(R.id.textView77);
        angka3=findViewById(R.id.textView80);

        ConstraintLayout constraintt3=findViewById(R.id.perentgtaw);
        ConstraintSet ctrr = new ConstraintSet();
        FrameLayout frem= findViewById(R.id.frameLayout7);
        FrameLayout frem2= findViewById(R.id.frameLayout17);
        FrameLayout frem3= findViewById(R.id.frameLayout16);
        TextView tulisan=findViewById(R.id.textView68);

        mulai = findViewById(R.id.editText14);
        selesai = findViewById(R.id.editText15);
        myCalendar = Calendar.getInstance();

        database = FirebaseDatabase.getInstance().getReference().child("Proyek");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        hargaField=findViewById(R.id.textView48);
        submitt=findViewById(R.id.button21);
        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah1=angka1.getText().toString();
                String jumlah2=angka2.getText().toString();
                String jumlah3=angka3.getText().toString();
                String jumlah4="0";
                String jumlah5="0";
                String hargaa=hargaField.getText().toString();
                String wid="0";
                String tmul=mulai.getText().toString();
                String tsel=selesai.getText().toString();
                String tipe="GTAW";
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
                submitProyek(new Proyek(namaproyek, tipe, proyek1,proyek2, proyek3, proyek4, proyek5, jumlah1,
                        jumlah2, jumlah3, jumlah4, jumlah5,tmul, tsel, hargaa, uid, wid));
            }
        });

        ImageButton plus1=findViewById(R.id.imageButton16);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf1++;
                String aa=Integer.toString(countsf1);
                angka1.setText(aa);
                update();
            }
        });

        ImageButton plus2=findViewById(R.id.imageButton32);
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf2++;
                String aa=Integer.toString(countsf2);
                angka2.setText(aa);
                update();
            }
        });

        ImageButton plus3=findViewById(R.id.imageButton34);
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf3++;
                String aa=Integer.toString(countsf3);
                angka3.setText(aa);
                update();
            }
        });

        ImageButton min1=findViewById(R.id.imageButton15);
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

        ImageButton min2=findViewById(R.id.imageButton31);
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

        ImageButton min3=findViewById(R.id.imageButton33);
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

        if(fin3.equals("Kapal Non Ferro")|| fin2.equals("Onshore/Offshore")|| fin2.equals("Stainless Steel")|| fin2.equals("Non Ferro")){
            frem2.setVisibility(View.GONE);
            frem3.setVisibility(View.GONE);
        }
        if (fin2.equals("Carbon Steel")){
            ctrr.clone(constraintt3);
            ctrr.connect(R.id.textView44, ConstraintSet.BOTTOM, R.id.frameLayout17, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt3);

            frem.setVisibility(View.GONE);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(GTAWActivity.this, date, myCalendar
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(GTAWActivity.this, date, myCalendar
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
                    int hp1=0, hp2=0, hp3=0;
                    if (fin3.equals("Kapal Non Ferro")){
                        hp1=240000;
                    }
                    if (fin2.equals("Onshore/Offshore")){
                        hp1=600000;
                    }
                    if(fin2.equals("Carbon Steel")){
                        hp2=280000;
                        hp3=400000;
                    }
                    if (fin2.equals("Stainless Steel")){
                        hp1=320000;
                    }
                    if(fin2.equals("Non Ferro")){
                        hp1=750000;
                    }
                    long harga=(hp1*countsf1+hp2*countsf2+hp3*countsf3)*beda;
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
        database.push().setValue(proyek).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(findViewById(R.id.button20), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
