package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class VerifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);

        EditText perus=findViewById(R.id.editText22);
        EditText deskripsi=findViewById(R.id.editText25);
        EditText alamat =findViewById(R.id.editText23);
        EditText rubahpesan=findViewById(R.id.editText26);
        EditText rubahwaktu=findViewById(R.id.editText27);


    }
}
