package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Locale;

public class KonfPesanActivity extends AppCompatActivity {
    private int count=0;
    private int counttrans=0;
    private int countako=0;
    private TextView hargatotal;
    private Proyek proyeku;
    private int hargamesin=0;
    private int hargatottal;
    private String alamatko="0";
    private ProgressDialog diialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konf_pesan);

        proyeku=(Proyek)getIntent().getSerializableExtra("proyek");

        TextView hargawelder=findViewById(R.id.textView134);
        hargawelder.setText(proyeku.getHarga());
        hargatotal=findViewById(R.id.textView137);
        hargatotal.setText(proyeku.getHarga());
        hargatottal=Integer.parseInt(proyeku.getHarga().replace(".", ""));

        final TextView juml=findViewById(R.id.textView122);

        ImageButton min=findViewById(R.id.imageButton41);
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count>0){
                    count--;
                    juml.setText(Integer.toString(count));
                    updateharga();
                    update();
                }
            }
        });

        ImageButton plus=findViewById(R.id.imageButton44);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                juml.setText(Integer.toString(count));
                updateharga();
                update();
            }
        });

        final EditText transport=findViewById(R.id.editText33);
        transport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(transport.getText().toString().equals("")){
                    counttrans=0;
                    update();
                }
                else{
                    counttrans=Integer.parseInt(transport.getText().toString());
                    update();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final EditText akom=findViewById(R.id.editText35);
        akom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(akom.getText().toString().equals("")){
                    countako=0;
                    update();
                }
                else{
                    countako=Integer.parseInt(akom.getText().toString());
                    update();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final EditText alamakom=findViewById(R.id.editText34);
        alamakom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(alamakom.getText().toString().equals("")){
                    alamatko="0";
                }
                else{
                    alamatko=alamakom.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button submit=findViewById(R.id.button41);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diialog= new ProgressDialog(KonfPesanActivity.this);
                diialog.setMessage("Tunggu Sebentar");
                diialog.setCancelable(false);
                diialog.show();
                if(count>0){
                    proyeku.setPakaimesin(Integer.toString(count));
                    proyeku.setHargamesin(Integer.toString(hargamesin));
                }
                else{
                    proyeku.setPakaimesin("0");
                    proyeku.setHargamesin("0");
                }
                proyeku.setHargatransport(Integer.toString(counttrans));
                proyeku.setHargaako(Integer.toString(countako));
                proyeku.setAlamako(alamatko);
                proyeku.setHargatotal(Integer.toString(hargatottal));

                FirebaseDatabase.getInstance().getReference().child("Proyek").push().setValue(proyeku).addOnSuccessListener(KonfPesanActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        diialog.dismiss();
                        Intent pinda=new Intent(KonfPesanActivity.this, MainActivity.class);
                        pinda.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(pinda);
                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan", Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        });

        Button back =findViewById(R.id.button42);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void updateharga(){
        TextView hargamessin=findViewById(R.id.textView124);
        if(proyeku.getTipe().equals("SMAW")){
            hargamesin=71250;
        }
        else if(proyeku.getTipe().equals("GTAW")){
            hargamesin=500000;
        }
        else if(proyeku.getTipe().equals("FCAW")){
            hargamesin=95000;
        }
        else{
            hargamesin=0;
        }
        int hargas=(count*hargamesin)*Integer.parseInt(proyeku.getBedahari());
        hargamessin.setText(Integer.toString(hargas));
    }
    public void update(){

        if(proyeku.getTipe().equals("SMAW")){
            hargamesin=71250;
        }
        else if(proyeku.getTipe().equals("GTAW")){
            hargamesin=500000;
        }
        else if(proyeku.getTipe().equals("FCAW")){
            hargamesin=95000;
        }
        else{
            hargamesin=0;
        }
        hargatottal=(hargamesin*count+countako+counttrans)*Integer.parseInt(proyeku.getBedahari())+Integer.parseInt(proyeku.getHarga().replace(".",""));
        NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
        String aa=nf3.format(hargatottal);
        hargatotal.setText(aa);
    }
}
