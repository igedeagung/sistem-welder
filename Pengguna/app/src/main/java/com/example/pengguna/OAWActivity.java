package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OAWActivity extends AppCompatActivity {
    private TextView angka1;
    private EditText mulai, selesai ;
    private Calendar myCalendar ;
    private boolean kliked;
    private TextView hargaField;
    private Button submitt;
    private Button balik;
    private DatabaseReference database;
    private String uid;
    private int countsf1=0;
    private long beda;
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
        setContentView(R.layout.activity_oaw);

        final String proyek1="OAW";
        final String proyek2="0";
        final String proyek3="0";
        final String proyek4="0";
        final String proyek5="0";

        angka1=findViewById(R.id.textView92);

        mulai = findViewById(R.id.editText20);
        selesai = findViewById(R.id.editText21);
        myCalendar = Calendar.getInstance();

        database = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        hargaField=findViewById(R.id.textView94);
        balik=findViewById(R.id.button34);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitt=findViewById(R.id.button28);
        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah1=angka1.getText().toString();
                String jumlah2="0";
                String jumlah3="0";
                String jumlah4="0";
                String jumlah5="0";
                String jumlah1f=jumlah1;
                String jumlah2f=jumlah2;
                String jumlah3f=jumlah3;
                String jumlah4f=jumlah4;
                String jumlah5f=jumlah5;
                String hargaa=hargaField.getText().toString();
                String wid="0";
                String tmul=mulai.getText().toString();
                String tsel=selesai.getText().toString();
                String tipe="OAW";
                EditText alama=findViewById(R.id.editText32);
                String alamat=alama.getText().toString();

                if(TextUtils.isEmpty(alamat)){
                    alama.setError("Alamat harus diisi");
                    alama.requestFocus();
                    return;
                }
                String namaproyek="Las Umum";
                String jenisproyek="Las Umum";
                String status="0";
                String nilai="0";
                String bdh=Long.toString(beda);
                Proyek proyeku=new Proyek(nilai, bdh, status, alamat, jenisproyek, namaproyek, tipe, proyek1,proyek2, proyek3, proyek4, proyek5,
                        jumlah1f, jumlah2f, jumlah3f, jumlah4f, jumlah5f, jumlah1,
                        jumlah2, jumlah3, jumlah4, jumlah5,tmul, tsel, hargaa, uid, wid);
                Intent pindah= new Intent(OAWActivity.this, KonfPesanActivity.class);
                pindah.putExtra("proyek",proyeku);
                startActivity(pindah);;
            }
        });

        ImageButton plus1=findViewById(R.id.imageButton40);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf1++;
                String aa=Integer.toString(countsf1);
                angka1.setText(aa);
                update();
            }
        });

        ImageButton min1=findViewById(R.id.imageButton39);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(OAWActivity.this, date, myCalendar
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(OAWActivity.this, date, myCalendar
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
                beda= (date2.getTime()-date.getTime())/86400000;
                if(beda<0){
                    selesai.setText("Tanggal Selesai");
                    mulai.setText("Tanggal Mulai");
                    hargaField.setText("0");
                }
                else {
                    int hp1=150000;

                    if(flay==0){
                        jumlh=0;
                    }
                    else{
                        jumlh=100000;
                    }
                    long harga=(hp1*countsf1+jumlh)*beda;
                    NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                    String aa=nf3.format(harga);
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
}
