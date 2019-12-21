package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private FirebaseAuth auth;
    private Button regis;
    private EditText ndepan, nbelakang, username, email, notelp, password, kpassword;
    private DatabaseReference database;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Welders");

        regis = findViewById(R.id.btn_daftar);
        ndepan= findViewById(R.id.edit_nmdepan);
        nbelakang= findViewById(R.id.edit_nmblakang);
        username= findViewById(R.id.edit_username);
        email= findViewById(R.id.edit_email);
        notelp= findViewById(R.id.edit_notelp);
        password= findViewById(R.id.edit_psswd);
        kpassword= findViewById(R.id.edit_kpsswd);

        backbtn=findViewById(R.id.btn_kembali);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali= new Intent(RegisterActivity.this, IntroActivity.class);
                startActivity(kembali);
                finish();
            }
        });

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
                                                        id_db.child("status").setValue(0);

                                                        Intent intent2 = new Intent(RegisterActivity.this, VerifActivity.class);
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
