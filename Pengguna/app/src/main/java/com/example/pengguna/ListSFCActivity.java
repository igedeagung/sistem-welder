package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.core.Context;

public class ListSFCActivity extends AppCompatActivity {
    private Button btnsmaw;
    private Button btnfcaw;
    private Button btngtaw;
    private Button btnsmgtaw;
    private Button btnsmfcaw;
    private String pesan;
    private String pesan1, pesan2, pesan3;

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sfc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        pesan=bundle.getString("test2");
        TextView viewww =findViewById(R.id.textView4);
        viewww.setText(pesan);

        btnsmaw = findViewById(R.id.button);
        btnsmaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmaw= new Intent(ListSFCActivity.this, SMAWActivity.class);
                pindahsmaw.putExtra("final1", pesan1);
                pindahsmaw.putExtra("final2", pesan2);
                pindahsmaw.putExtra("final3", pesan3);
                startActivity(pindahsmaw);
            }
        });

        btnfcaw = findViewById(R.id.button2);
        btnfcaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahfcaw= new Intent(ListSFCActivity.this, FCAWActivity.class);
                pindahfcaw.putExtra("final1", pesan1);
                pindahfcaw.putExtra("final2", pesan2);
                pindahfcaw.putExtra("final3", pesan3);
                startActivity(pindahfcaw);
            }
        });

        btngtaw = findViewById(R.id.button3);
        btngtaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahgtaw= new Intent(ListSFCActivity.this, GTAWActivity.class);
                pindahgtaw.putExtra("final1", pesan1);
                pindahgtaw.putExtra("final2", pesan2);
                pindahgtaw.putExtra("final3", pesan3);
                startActivity(pindahgtaw);
            }
        });

        btnsmgtaw = findViewById(R.id.button4);
        btnsmgtaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmgtaw= new Intent(ListSFCActivity.this, SMGTAWActivity.class);
                pindahsmgtaw.putExtra("final1", pesan1);
                pindahsmgtaw.putExtra("final2", pesan2);
                pindahsmgtaw.putExtra("final3", pesan3);
                startActivity(pindahsmgtaw);
            }
        });

        btnsmfcaw = findViewById(R.id.button5);
        btnsmfcaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsmfcaw= new Intent(ListSFCActivity.this, SMFCAWActivity.class);
                pindahsmfcaw.putExtra("final1", pesan1);
                pindahsmfcaw.putExtra("final2", pesan2);
                pindahsmfcaw.putExtra("final3", pesan3);
                startActivity(pindahsmfcaw);
            }
        });

        ConstraintLayout constraintt=findViewById(R.id.parentt);
        ConstraintSet ctr = new ConstraintSet();
        if(pesan.equals("Steel Structure")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button2, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 350);
            ctr.applyTo(constraintt);
            viewww.setTextColor(Color.parseColor("#ffffff"));


            constraintt.setBackground(getDrawable(R.drawable.jembatan2));
            btngtaw.setVisibility(View.GONE);
            btnsmfcaw.setVisibility(View.GONE);
            btnsmgtaw.setVisibility(View.GONE);

            pesan1="Steel Structure";
            pesan2="yha";
            pesan3="yha";
        }
        if(pesan.equals("Kapal Ferro")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button4, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 200);
            ctr.connect(R.id.button2, ConstraintSet.BOTTOM, R.id.button4, ConstraintSet.TOP, 80);
            ctr.applyTo(constraintt);



            btngtaw.setVisibility(View.GONE);
            btnsmfcaw.setVisibility(View.GONE);

            pesan1="Konstruksi Maritim";
            pesan2="Kapal";
            pesan3="Kapal Ferro";
        }
        if(pesan.equals("Kapal Aluminium")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button3, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 450);
            ctr.applyTo(constraintt);

            btnsmaw.setVisibility(View.GONE);
            btnsmfcaw.setVisibility(View.GONE);
            btnfcaw.setVisibility(View.GONE);
            btnsmgtaw.setVisibility(View.GONE);

            pesan1="Konstruksi Maritim";
            pesan2="Kapal";
            pesan3="Kapal Aluminium";
        }
        if(pesan.equals("Onshore/Offshore")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button4, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 200);
            ctr.applyTo(constraintt);
            viewww.setTextColor(Color.parseColor("#000000"));

            constraintt.setBackground(getDrawable(R.drawable.offshore));

            btnsmfcaw.setVisibility(View.GONE);

            pesan1="Konstruksi Maritim";
            pesan2="Onshore/Offshore";
            pesan3="yha";
        }
        if(pesan.equals("Carbon Steel")){
            ctr.clone(constraintt);

            ctr.connect(R.id.button4, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 200);
            ctr.connect(R.id.button, ConstraintSet.BOTTOM, R.id.button3, ConstraintSet.TOP, 80);
            ctr.applyTo(constraintt);

            viewww.setTextColor(Color.parseColor("#000000"));


            constraintt.setBackground(getDrawable(R.drawable.pipapotong));

            btnsmfcaw.setVisibility(View.GONE);
            btnfcaw.setVisibility(View.GONE);

            pesan1="Perpipaan";
            pesan2="Carbon Steel";
            pesan3="yha";
        }
        if(pesan.equals("Stainless Steel")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button4, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 200);
            ctr.connect(R.id.button, ConstraintSet.BOTTOM, R.id.button3, ConstraintSet.TOP, 80);

            ctr.applyTo(constraintt);
            viewww.setTextColor(Color.parseColor("#000000"));
            constraintt.setBackground(getDrawable(R.drawable.pipapotong));
            btnsmfcaw.setVisibility(View.GONE);
            btnfcaw.setVisibility(View.GONE);

            pesan1="Perpipaan";
            pesan2="Stainless Steel";
            pesan3="yha";
        }
        if(pesan.equals("Non Ferro")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button4, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 200);
            ctr.connect(R.id.textView4, ConstraintSet.BOTTOM, R.id.button3, ConstraintSet.TOP, 80);
            ctr.applyTo(constraintt);
            viewww.setTextColor(Color.parseColor("#000000"));
            constraintt.setBackground(getDrawable(R.drawable.pipapotong));
            btnsmfcaw.setVisibility(View.GONE);
            btnfcaw.setVisibility(View.GONE);
            btnsmaw.setVisibility(View.GONE);

            pesan1="Perpipaan";
            pesan2="Non Ferro";
            pesan3="yha";
        }
        if(pesan.equals("Storage Tank")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button, ConstraintSet.BOTTOM, R.id.button4, ConstraintSet.TOP, 80);
            ctr.applyTo(constraintt);
            constraintt.setBackground(getDrawable(R.drawable.stpr));
            btnfcaw.setVisibility(View.GONE);
            btngtaw.setVisibility(View.GONE);

            pesan1="Industry Manufacture";
            pesan2="Storage Tank";
            pesan3="yha";
        }
        if(pesan.equals("Pressure Tank")){
            ctr.clone(constraintt);
            ctr.connect(R.id.button4, ConstraintSet.BOTTOM, R.id.parentt, ConstraintSet.BOTTOM, 200);
            ctr.connect(R.id.button2, ConstraintSet.BOTTOM, R.id.button4, ConstraintSet.TOP, 80);

            ctr.applyTo(constraintt);
            constraintt.setBackground(getDrawable(R.drawable.stpr));
            btngtaw.setVisibility(View.GONE);
            btnsmfcaw.setVisibility(View.GONE);

            pesan1="Industry Manufacture";
            pesan2="Pressure Tank";
            pesan3="yha";
        }
    }
}
