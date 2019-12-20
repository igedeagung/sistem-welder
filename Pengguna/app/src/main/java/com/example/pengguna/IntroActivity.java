package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button daftar=findViewById(R.id.button16);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahregis= new Intent(IntroActivity.this, RegisterActivity.class);
                startActivity(pindahregis);
            }
        });

        Button login=findViewById(R.id.button15);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahlogin= new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(pindahlogin);
            }
        });
    }
}
