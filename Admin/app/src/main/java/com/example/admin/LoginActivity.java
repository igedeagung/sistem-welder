package com.example.admin;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private EditText username, password;
    private Button loginbtn;
    private Button backbtn;
    private String emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Admin");

        username=findViewById(R.id.editTextt1);
        password=findViewById(R.id.editTextt2);

        backbtn=findViewById(R.id.button27);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginbtn=findViewById(R.id.buttonn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Tunggu Sebentar");
                dialog.show();

                final String usernames=username.getText().toString();
                final String passwordd = password.getText().toString();
                emails="";

                if (TextUtils.isEmpty(usernames)) {
                    dialog.dismiss();
                    username.setError("Username anda kosong");
                    username.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(passwordd)) {
                    dialog.dismiss();
                    password.setError("Password anda kosong");
                    password.requestFocus();
                    return;
                }
                // Query for all entries with a certain child with value equal to something
                Query allPostFromAuthor = ref.orderByChild("username").equalTo(usernames);

                // Add listener for Firebase response on said query
                allPostFromAuthor.addValueEventListener( new ValueEventListener(){
                    List<String> hasil=new ArrayList<>();
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot post : dataSnapshot.getChildren() ){
                            // Iterate through all posts with the same author
                            hasil.add(post.child("email").getValue().toString());
                        }
                        for(int j=0;j<hasil.size();j++)
                        {
                            emails=hasil.get(j);
                        }
                        if(emails.isEmpty())
                        {
                            dialog.dismiss();
                            username.setError("Username tidak terdaftar");
                            username.requestFocus();
                        }
                        else{
                            //authenticate user
                            auth.signInWithEmailAndPassword(emails, passwordd)
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                // there was an error
                                                if (password.length() < 6) {
                                                    dialog.dismiss();
                                                    password.setError("Password anda kurang");
                                                    password.requestFocus();
                                                } else {
                                                    dialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                dialog.dismiss();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);

                                            }
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });

    }
}
