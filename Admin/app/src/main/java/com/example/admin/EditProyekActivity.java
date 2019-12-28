package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProyekActivity extends AppCompatActivity {
    private String[] listItems2, listitem3;
    private EditText mulai, selesai;
    private boolean kliked;
    private Calendar myCalendar ;
    private String jeniss;
    private String namapr;
    private String jenis;
    private String mulaii;
    private String selesaii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proyek);

        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("email");

        mulai=findViewById(R.id.editText12);
        selesai=findViewById(R.id.editText13);
        myCalendar = Calendar.getInstance();

        listItems2 = getResources().getStringArray(R.array.nmprk);
        final EditText namap=findViewById(R.id.editText10);
        namap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditProyekActivity.this);
                mBuilder.setTitle("Nama Proyek");
                mBuilder.setSingleChoiceItems(listItems2, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        namap.setText(listItems2[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        listitem3 = getResources().getStringArray(R.array.pros);
        final EditText pross=findViewById(R.id.editText11);
        pross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditProyekActivity.this);
                mBuilder.setTitle("Proses Pengelasan");
                mBuilder.setSingleChoiceItems(listitem3, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pross.setText(listitem3[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if(kliked){
                    selesai.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    mulai.setText(sdf.format(myCalendar.getTime()));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProyekActivity.this, date, myCalendar
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProyekActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        Button sel=findViewById(R.id.button30);
        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText owner=findViewById(R.id.editText6);
                String owwner=owner.getText().toString();
                jeniss="";
                namapr=namap.getText().toString();
                jenis=pross.getText().toString();
                mulaii=mulai.getText().toString();
                selesaii= selesai.getText().toString();

                if(namapr.equals("Steel Structure")){
                    jeniss="Steel Structure";
                }
                if(namapr.equals("Kapal Ferro") ||namapr.equals("Kapal Non Ferro")||namapr.equals("Onshore/Offshore")){
                    jeniss="Konstruksi Maritim";
                }
                if(namapr.equals("Carbon Steel") || namapr.equals("Stainless Steel") || namapr.equals("Non Ferro")){
                    jeniss="Perpipaan";
                }
                if(namapr.equals("Storage Tank") || namapr.equals("Pressure Tank")){
                    jeniss="Industri Manufacture";
                }
                if(namapr.equals("Las Umum")){
                    jeniss="Las Umum";
                }

                if(TextUtils.isEmpty(owwner)) {
                    owner.setError("Nama Depan tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(namapr)) {
                    namap.setError("Nama Proyek tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(jenis)) {
                    pross.setError("Jenis Proyek tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(mulaii)) {
                    mulai.setError("Tanggal Mulai tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(selesaii)) {
                    selesai.setError("Tanggal Selesai tidak boleh kosong");
                    return;
                }
                final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
                ref.orderByChild("namadepan").equalTo(owwner).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String clubkey="";

                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            clubkey = childSnapshot.getKey();
                        }
                        if (clubkey.equals("")){
                            owner.setError("Inputan User tidak terdaftsr");
                        }
                        else{
                            DatabaseReference fer=FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan);
                            fer.child("uid").setValue(clubkey);
                            fer.child("namaproyek").setValue(namapr);
                            fer.child("tipe").setValue(jenis);
                            fer.child("jenisproyek").setValue(jeniss);
                            fer.child("tanggalmulai").setValue(mulaii);
                            fer.child("tanggalselesai").setValue(selesaii);

                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }
}
