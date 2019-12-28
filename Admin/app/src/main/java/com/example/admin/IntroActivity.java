package com.example.admin;

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

        Button regis=findViewById(R.id.button24);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(IntroActivity.this, RegisterActivity.class);
                startActivity(pindahh);
            }
        });

        Button login=findViewById(R.id.button17);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(pindahh);
            }
        });
    }
}
