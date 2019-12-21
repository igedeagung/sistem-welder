package com.example.welder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final Button pindahregis=findViewById(R.id.btn_buat_akun);
        pindahregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdhregis=new Intent(IntroActivity.this, RegisterActivity.class);
                startActivity(pdhregis);
            }
        });

        Button pindahlogin=findViewById(R.id.btn_punya_akun);
        pindahlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdhlogin=new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(pdhlogin);
            }
        });
    }
}
