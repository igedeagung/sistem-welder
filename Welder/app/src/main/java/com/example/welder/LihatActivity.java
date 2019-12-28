package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LihatActivity extends AppCompatActivity {

    private int cout=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dabes= FirebaseDatabase.getInstance().getReference().child("Welders").child(uid);

        dabes.child("pid").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                final DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek").child(key);
                res.child("jenisproyek").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        TextView piew=findViewById(R.id.textView14);
                        piew.setText(keys);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("tipe").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        TextView piew=findViewById(R.id.textView16);
                        piew.setText(keys);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("jumlah1").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(keys.equals("1")){
                            res.child("proyek1").addValueEventListener(new ValueEventListener() {
                                String keys;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    keys=dataSnapshot.getValue().toString();
                                    TextView piew=findViewById(R.id.textView18);
                                    piew.setText(keys);
                                    pluscout();
                                    ConstraintLayout layout=findViewById(R.id.konstraint);
                                    ConstraintSet ctr= new ConstraintSet();

                                    TextView piew2=findViewById(R.id.textView19);
                                    TextView piew3=findViewById(R.id.textView20);
                                    TextView piew4=findViewById(R.id.textView24);
                                    TextView piew5=findViewById(R.id.textView25);

                                    if(cout==0){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView18, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew2.setVisibility(View.GONE);
                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }
                                    if(cout==1){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView19, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==2){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView20, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==3){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew5.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("jumlah2").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(keys.equals("1")){
                            res.child("proyek2").addValueEventListener(new ValueEventListener() {
                                String keys;
                                TextView piew;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(cout==0){
                                        piew=findViewById(R.id.textView18);
                                    }
                                    else{
                                        piew=findViewById(R.id.textView19);
                                    }
                                    keys=dataSnapshot.getValue().toString();
                                    piew.setText(keys);
                                    pluscout();
                                    ConstraintLayout layout=findViewById(R.id.konstraint);
                                    ConstraintSet ctr= new ConstraintSet();

                                    TextView piew2=findViewById(R.id.textView19);
                                    TextView piew3=findViewById(R.id.textView20);
                                    TextView piew4=findViewById(R.id.textView24);
                                    TextView piew5=findViewById(R.id.textView25);

                                    if(cout==0){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView18, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew2.setVisibility(View.GONE);
                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }
                                    if(cout==1){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView19, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==2){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView20, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==3){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew5.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("jumlah3").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(keys.equals("1")){
                            res.child("proyek3").addValueEventListener(new ValueEventListener() {
                                String keys;
                                TextView piew;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(cout==0){
                                        piew=findViewById(R.id.textView18);
                                    }
                                    if(cout==1){
                                        piew=findViewById(R.id.textView19);
                                    }
                                    if(cout==2){
                                        piew=findViewById(R.id.textView20);
                                    }
                                    keys=dataSnapshot.getValue().toString();
                                    piew.setText(keys);
                                    pluscout();
                                    ConstraintLayout layout=findViewById(R.id.konstraint);
                                    ConstraintSet ctr= new ConstraintSet();

                                    TextView piew2=findViewById(R.id.textView19);
                                    TextView piew3=findViewById(R.id.textView20);
                                    TextView piew4=findViewById(R.id.textView24);
                                    TextView piew5=findViewById(R.id.textView25);

                                    if(cout==0){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView18, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew2.setVisibility(View.GONE);
                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }
                                    if(cout==1){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView19, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==2){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView20, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==3){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew5.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("jumlah4").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(keys.equals("1")){
                            res.child("proyek4").addValueEventListener(new ValueEventListener() {
                                String keys;
                                TextView piew;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(cout==0){
                                        piew=findViewById(R.id.textView18);
                                    }
                                    if(cout==1){
                                        piew=findViewById(R.id.textView19);
                                    }
                                    if(cout==2){
                                        piew=findViewById(R.id.textView19);
                                    }
                                    if(cout==3){
                                        piew=findViewById(R.id.textView24);
                                    }
                                    keys=dataSnapshot.getValue().toString();
                                    piew.setText(keys);
                                    pluscout();
                                    ConstraintLayout layout=findViewById(R.id.konstraint);
                                    ConstraintSet ctr= new ConstraintSet();

                                    TextView piew2=findViewById(R.id.textView19);
                                    TextView piew3=findViewById(R.id.textView20);
                                    TextView piew4=findViewById(R.id.textView24);
                                    TextView piew5=findViewById(R.id.textView25);

                                    if(cout==0){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView18, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew2.setVisibility(View.GONE);
                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }
                                    if(cout==1){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView19, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==2){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView20, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==3){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew5.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("jumlah5").addValueEventListener(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(keys.equals("1")){
                            res.child("proyek5").addValueEventListener(new ValueEventListener() {
                                String keys;
                                TextView piew;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(cout==0){
                                        piew=findViewById(R.id.textView18);
                                    }
                                    if(cout==1){
                                        piew=findViewById(R.id.textView19);
                                    }
                                    if(cout==2){
                                        piew=findViewById(R.id.textView19);
                                    }
                                    if(cout==3){
                                        piew=findViewById(R.id.textView20);
                                    }
                                    if(cout==4){
                                        piew=findViewById(R.id.textView25);
                                    }
                                    keys=dataSnapshot.getValue().toString();
                                    piew.setText(keys);
                                    pluscout();
                                    ConstraintLayout layout=findViewById(R.id.konstraint);
                                    ConstraintSet ctr= new ConstraintSet();

                                    TextView piew2=findViewById(R.id.textView19);
                                    TextView piew3=findViewById(R.id.textView20);
                                    TextView piew4=findViewById(R.id.textView24);
                                    TextView piew5=findViewById(R.id.textView25);

                                    if(cout==0){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView18, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew2.setVisibility(View.GONE);
                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }
                                    if(cout==1){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView19, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew3.setVisibility(View.GONE);
                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==2){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView20, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew4.setVisibility(View.GONE);
                                        piew5.setVisibility(View.GONE);
                                    }

                                    if(cout==3){
                                        ctr.clone(layout);
                                        ctr.connect(R.id.textView24, ConstraintSet.BOTTOM, R.id.textView26, ConstraintSet.TOP, 0);
                                        ctr.applyTo(layout);

                                        piew5.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("uid").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String keyd=dataSnapshot.getValue().toString();
                        DatabaseReference user=FirebaseDatabase.getInstance().getReference().child("Users").child(keyd);
                        user.child("namadepan").addValueEventListener(new ValueEventListener() {
                            String key;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                key=dataSnapshot.getValue().toString();
                                TextView piew=findViewById(R.id.textView32);
                                piew.setText(key);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        user.child("notelp").addValueEventListener(new ValueEventListener() {
                            String key;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                key=dataSnapshot.getValue().toString();
                                TextView piew=findViewById(R.id.textView34);
                                piew.setText(key);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void pluscout(){
        cout++;
    }
}
