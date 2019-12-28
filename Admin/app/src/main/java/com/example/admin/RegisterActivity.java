package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText ndepan, nbelakang, username, email, notelp, password, kpassword, jenis;
    private Button backbtn, regis;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private EditText pos;
    private String[] listItems2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Admin");
        ndepan= findViewById(R.id.editText);
        nbelakang= findViewById(R.id.editText2);
        username= findViewById(R.id.editText3);
        email= findViewById(R.id.editText4);
        notelp= findViewById(R.id.editText5);
        password= findViewById(R.id.editText8);
        kpassword= findViewById(R.id.editText9);
        listItems2 = getResources().getStringArray(R.array.tingkatan);

        backbtn=findViewById(R.id.button26);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pos = (EditText) findViewById(R.id.editText7);
        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
                mBuilder.setTitle("Tipe Admin");
                mBuilder.setSingleChoiceItems(listItems2, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pos.setText(listItems2[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        regis = findViewById(R.id.button25);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namdepan=ndepan.getText().toString().trim();
                final String nambelakang=nbelakang.getText().toString().trim();
                final String userrname =username.getText().toString().trim();
                final String eemail =email.getText().toString().trim();
                final String nnotelp =notelp.getText().toString().trim();
                final String ppassword =password.getText().toString().trim();
                String kkpassword =kpassword.getText().toString().trim();
                final String jenis=pos.getText().toString().trim();

                if(TextUtils.isEmpty(namdepan)) {
                    ndepan.setError("Harap isi Nama Depan Anda");
                    return;
                }
                if(TextUtils.isEmpty(nambelakang)) {
                    nbelakang.setError("Harap isi Nama Belakang Anda");
                    return;
                }
                if(TextUtils.isEmpty(userrname)) {
                    username.setError("Harap isi Username Anda");
                    return;
                }
                if(TextUtils.isEmpty(eemail)) {
                    email.setError("Harap isi Email Anda");
                    return;
                }
                if(TextUtils.isEmpty(nnotelp)) {
                    notelp.setError("Harap isi Nomor Telepon Anda");
                    return;
                }
                if(TextUtils.isEmpty(ppassword)) {
                    password.setError("Harap isi Password Anda");
                    return;
                }
                if(TextUtils.isEmpty(kkpassword)) {
                    kpassword.setError("Harap isi Konfirmasi Password Anda");
                    return;
                }
                if(TextUtils.isEmpty(jenis)) {
                    pos.setError("Harap pilih Posisi Anda");
                    return;
                }
                if(!isPhoneValid(nnotelp)){
                    notelp.setError("Nomor Telepon anda tidak valid");
                    return;
                }
                if(!isEmailValid(eemail)){
                    email.setError("Email anda tidak valid");
                    return;
                }
                if(ppassword.length()<6){
                    password.setError("Password anda terlalu singkat");
                    return;
                }
                if(ppassword.compareTo(kkpassword)!=0){
                    kpassword.setError("Password dan Konfirmasi Password anda tidak sama");
                    return;
                }
                database.orderByChild("username")
                        .equalTo(userrname)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String clubkey="";

                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    clubkey = childSnapshot.getKey();
                                }
                                if (clubkey.equals("")){
                                    auth.createUserWithEmailAndPassword(eemail, ppassword)
                                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        password.setError("Authentication failed." + task.getException());
                                                    } else {
                                                        String user_id=auth.getCurrentUser().getUid();
                                                        DatabaseReference id_db = database.child(user_id);
                                                        id_db.child("namadepan").setValue(namdepan);
                                                        id_db.child("namabelakang").setValue(nambelakang);
                                                        id_db.child("username").setValue(userrname);
                                                        id_db.child("email").setValue(eemail);
                                                        id_db.child("notelp").setValue(nnotelp);
                                                        id_db.child("jenis").setValue(jenis);

                                                        Intent intent2 = new Intent(RegisterActivity.this, MainActivity.class);
                                                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent2);
                                                    }
                                                }
                                            });
                                }
                                else{
                                    username.setError("Username Sudah terdaftar");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                username.setError("Nomor Telepon Tidak terdaftar");
                            }
                        });
            }
        });

    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isPhoneValid(String phone) {
        String expression = "^(^\\+62\\s?|^0)(\\d{3,4}-?){2}\\d{3,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
