package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class SMGTAWActivity extends AppCompatActivity {
    private String fin1, fin2, fin3;
    private EditText mulai, selesai ;
    private Calendar myCalendar ;
    private boolean kliked;
    private int countsf1=0, countsf2=0, countsf3=0,countsf4=0,countsf5=0 ;
    private TextView angka1;
    private TextView angka2;
    private TextView angka3;
    private TextView angka4;
    private TextView angka5;
    private TextView hargaField;
    private Button submitt;
    private Button balik;
    private DatabaseReference database;
    private String uid;
    private long beda;
    private ProgressDialog diialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_smgtaw);

        final String proyek1="Posisi 1G, 2G, 3G, 4G (GTAW)\nPosisi 1G, 2G, 5G, 6G (SMAW)";
        final String proyek2="Posisi 1G, 2G, 3G, 4G (SMAW)";
        final String proyek3="- Posisi 1G, 2G, 3G, 4G (GTAW)\n- Posisi 1G, 2G, 5G, 6G \n  (Root, Hot : GTAW)\n  (Filler, Capping :SMAW)";
        final String proyek5="Industri+- Posisi 1G, 2G, 3G, 4G (GTAW)\n- Posisi 1G, 2G, 5G, 6G \n  (Root, Hot : GTAW)\n  (Filler, Capping :SMAW)";
        final String proyek4="Marine+- Posisi 1G, 2G, 3G, 4G (GTAW)\n- Posisi 1G, 2G, 5G, 6G \n  (Root, Hot : GTAW)\n  (Filler, Capping :SMAW)";

        Bundle bundle=getIntent().getExtras();
        fin1=bundle.getString("final1");
        fin2=bundle.getString("final2");
        fin3=bundle.getString("final3");

        angka1=findViewById(R.id.textView51);
        angka2=findViewById(R.id.textView56);
        angka3=findViewById(R.id.textView53);
        angka4=findViewById(R.id.textView83);
        angka5=findViewById(R.id.textView86);

        ConstraintLayout constraintt2=findViewById(R.id.parentsmgtaw);
        ConstraintSet ctrr = new ConstraintSet();
        FrameLayout frem= findViewById(R.id.frameLayout8);
        FrameLayout frem2= findViewById(R.id.frameLayout10);
        FrameLayout frem3= findViewById(R.id.frameLayout9);
        FrameLayout frem4= findViewById(R.id.frameLayout19);
        FrameLayout frem5= findViewById(R.id.frameLayout20);
        TextView tulisan=findViewById(R.id.textView69);

        mulai = findViewById(R.id.editText17);
        selesai = findViewById(R.id.editText18);
        myCalendar = Calendar.getInstance();

        database = FirebaseDatabase.getInstance().getReference().child("Proyek");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        hargaField=findViewById(R.id.textView55);
        balik=findViewById(R.id.button31);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitt=findViewById(R.id.button22);
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
                EditText alama=findViewById(R.id.editText31);
                String alamat=alama.getText().toString();
                diialog= new ProgressDialog(SMGTAWActivity.this);
                diialog.setMessage("Tunggu Sebentar");
                diialog.setCancelable(false);
                diialog.show();
                if(TextUtils.isEmpty(alamat)){
                    diialog.dismiss();
                    alama.setError("Alamat harus diisi");
                    alama.requestFocus();
                    return;
                }
                String tmul=mulai.getText().toString();
                String tsel=selesai.getText().toString();
                String tipe="SMAW/GTAW";
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
                submitProyek(new Proyek(nilai, bdh, status, alamat, jenisproyek, namaproyek, tipe, proyek1,proyek2, proyek3, proyek4, proyek5,
                        jumlah1f, jumlah2f, jumlah3f, jumlah4f, jumlah5f, jumlah1,
                        jumlah2, jumlah3, jumlah4, jumlah5,tmul, tsel, hargaa, uid, wid));
            }
        });

        ImageButton plus1=findViewById(R.id.imageButton18);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf1++;
                String aa=Integer.toString(countsf1);
                angka1.setText(aa);
                update();
            }
        });

        ImageButton plus2=findViewById(R.id.imageButton22);
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf2++;
                String aa=Integer.toString(countsf2);
                angka2.setText(aa);
                update();
            }
        });

        ImageButton plus3=findViewById(R.id.imageButton20);
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf3++;
                String aa=Integer.toString(countsf3);
                angka3.setText(aa);
                update();
            }
        });

        ImageButton plus4=findViewById(R.id.imageButton36);
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf4++;
                String aa=Integer.toString(countsf4);
                angka4.setText(aa);
                update();
            }
        });

        ImageButton plus5=findViewById(R.id.imageButton38);
        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countsf5++;
                String aa=Integer.toString(countsf5);
                angka5.setText(aa);
                update();
            }
        });

        ImageButton min1=findViewById(R.id.imageButton17);
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

        ImageButton min2=findViewById(R.id.imageButton21);
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

        ImageButton min3=findViewById(R.id.imageButton19);
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

        ImageButton min4=findViewById(R.id.imageButton35);
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

        ImageButton min5=findViewById(R.id.imageButton37);
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

        if(fin3.equals("Kapal Ferro" )|| fin2.equals("Onshore/Offshore") ){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView49, ConstraintSet.BOTTOM, R.id.frameLayout8, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            frem3.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }
        if(fin2.equals("Stainless Steel") || fin2.equals("Non Ferro")|| fin2.equals("Pressure Tank")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView49, ConstraintSet.BOTTOM, R.id.frameLayout9, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            frem.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
            frem4.setVisibility(View.GONE);
            frem5.setVisibility(View.GONE);
        }
        if (fin2.equals("Carbon Steel")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView49, ConstraintSet.BOTTOM, R.id.frameLayout20, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            frem.setVisibility(View.GONE);
            frem2.setVisibility(View.GONE);
            frem3.setVisibility(View.GONE);
        }
        if(fin2.equals("Storage Tank")){
            ctrr.clone(constraintt2);
            ctrr.connect(R.id.textView69, ConstraintSet.BOTTOM, R.id.frameLayout10, ConstraintSet.TOP, 50);
            ctrr.connect(R.id.textView49, ConstraintSet.BOTTOM, R.id.textView69, ConstraintSet.TOP, 0);
            ctrr.applyTo(constraintt2);

            tulisan.setText("Material Stainless Steel");
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(SMGTAWActivity.this, date, myCalendar
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(SMGTAWActivity.this, date, myCalendar
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
                    int hp1=0, hp2=0, hp3=0, hp4=0, hp5=0;
                    if(fin3.equals("Kapal Ferro" )){
                        hp1=160000;
                    }
                    if (fin2.equals("Onshore/Offshore")){
                        hp1=600000;
                    }
                    if (fin2.equals("Carbon Steel")){
                        hp4=280000;
                        hp5=400000;
                    }
                    if (fin2.equals("Stainless Steel")){
                        hp3=280000;
                    }
                    if(fin2.equals("Non Ferro")){
                        hp3=750000;
                    }
                    if(fin2.equals("Storage Tank")){
                        hp2=160000;
                        hp3=160000;
                    }
                    if(fin2.equals("Pressure Tank")){
                        hp3=400000;
                    }
                    long harga=(hp1*countsf1+hp2*countsf2+hp3*countsf3+hp4*countsf4+hp5*countsf5)*beda;
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
                diialog.dismiss();
                Intent pinda=new Intent(SMGTAWActivity.this, MainActivity.class);
                pinda.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pinda);
                Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan", Toast.LENGTH_SHORT ).show();
            }
        });
    }
}
