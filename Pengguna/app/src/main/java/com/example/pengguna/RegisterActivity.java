package com.example.pengguna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        database = FirebaseDatabase.getInstance().getReference().child("Users");

        regis = findViewById(R.id.button17);
        ndepan= findViewById(R.id.editText);
        nbelakang= findViewById(R.id.editText2);
        username= findViewById(R.id.editText3);
        email= findViewById(R.id.editText4);
        notelp= findViewById(R.id.editText5);
        password= findViewById(R.id.editText6);
        kpassword= findViewById(R.id.editText7);

        backbtn=findViewById(R.id.button25);
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
                final ProgressDialog diialog= new ProgressDialog(RegisterActivity.this);
                diialog.setMessage("Tunggu Sebentar");
                diialog.setCancelable(false);
                diialog.show();

                if(TextUtils.isEmpty(namdepan)) {
                    diialog.dismiss();
                    ndepan.setError("Harap isi Nama Depan Anda");
                    ndepan.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(nambelakang)) {
                    diialog.dismiss();
                    nbelakang.setError("Harap isi Nama Belakang Anda");
                    nbelakang.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(userrname)) {
                    diialog.dismiss();
                    username.setError("Harap isi Username Anda");
                    username.requestFocus();

                    return;
                }
                if(TextUtils.isEmpty(eemail)) {
                    diialog.dismiss();
                    email.setError("Harap isi Email Anda");
                    email.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(nnotelp)) {
                    diialog.dismiss();
                    notelp.setError("Harap isi Nomor Telepon Anda");
                    notelp.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(ppassword)) {
                    diialog.dismiss();
                    password.setError("Harap isi Password Anda");
                    password.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(kkpassword)) {
                    diialog.dismiss();
                    kpassword.setError("Harap isi Konfirmasi Password Anda");
                    kpassword.requestFocus();
                    return;
                }
                if(!isPhoneValid(nnotelp)){
                    diialog.dismiss();
                    notelp.setError("Nomor Telepon anda tidak valid");
                    notelp.requestFocus();
                    return;
                }
                if(!isEmailValid(eemail)){
                    diialog.dismiss();
                    email.setError("Email anda tidak valid");
                    email.requestFocus();
                    return;
                }
                if(ppassword.length()<6){
                    diialog.dismiss();
                    password.setError("Password anda terlalu singkat");
                    password.requestFocus();
                    return;
                }
                if(ppassword.compareTo(kkpassword)!=0){
                    diialog.dismiss();
                    password.setError("Password dan Konfirmasi Password anda tidak sama");
                    password.requestFocus();
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
                                                        diialog.dismiss();
                                                        password.setError("Authentication failed." + task.getException());
                                                        password.requestFocus();
                                                    } else {
                                                        String user_id=auth.getCurrentUser().getUid();
                                                        DatabaseReference id_db = database.child(user_id);
                                                        id_db.child("namadepan").setValue(namdepan);
                                                        id_db.child("namabelakang").setValue(nambelakang);
                                                        id_db.child("username").setValue(userrname);
                                                        id_db.child("email").setValue(eemail);
                                                        id_db.child("notelp").setValue(nnotelp);
                                                        id_db.child("rating").setValue(0);
                                                        diialog.dismiss();
                                                        Intent intent2 = new Intent(RegisterActivity.this, MainActivity.class);
                                                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent2);
                                                    }
                                                }
                                            });
                                }
                                else{
                                    diialog.dismiss();
                                    username.setError("Username Sudah terdaftar");
                                    username.requestFocus();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                diialog.dismiss();
                                notelp.setError("Nomor Telepon Tidak terdaftar");
                                notelp.requestFocus();
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
