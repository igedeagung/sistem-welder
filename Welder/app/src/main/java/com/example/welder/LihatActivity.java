package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dabes= FirebaseDatabase.getInstance().getReference().child("Welders").child(uid);

        dabes.child("pid").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                final DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek").child(key);
                res.child("jenisproyek").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("tipe").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("alamat").addListenerForSingleValueEvent(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key=dataSnapshot.getValue().toString();
                        TextView view=findViewById(R.id.textView27);
                        view.setText(key);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("bedahari").addListenerForSingleValueEvent(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key=dataSnapshot.getValue().toString();
                        TextView view=findViewById(R.id.textView29);
                        view.setText(key+" Hari");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                res.child("jumlah1").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(!keys.equals("0")){
                            res.child("proyek1").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("jumlah2").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(!keys.equals("0")){
                            res.child("proyek2").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("jumlah3").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(!keys.equals("0")){
                            res.child("proyek3").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("jumlah4").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(!keys.equals("0")){
                            res.child("proyek4").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("jumlah5").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        keys=dataSnapshot.getValue().toString();
                        if(!keys.equals("0")){
                            res.child("proyek5").addListenerForSingleValueEvent(new ValueEventListener() {
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

                res.child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String keyd=dataSnapshot.getValue().toString();
                        DatabaseReference user=FirebaseDatabase.getInstance().getReference().child("Users").child(keyd);
                        user.child("namadepan").addListenerForSingleValueEvent(new ValueEventListener() {
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

                        user.child("notelp").addListenerForSingleValueEvent(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("terima").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                if(key.equals("0")){
                    Button buton=findViewById(R.id.button8);
                    buton.setVisibility(View.GONE);
                }
                if(key.equals("1")){
                    Button btnterima=findViewById(R.id.button6);
                    Button btntolak=findViewById(R.id.button7);
                    Button buton=findViewById(R.id.button8);
                    btnterima.setVisibility(View.GONE);
                    btntolak.setVisibility(View.GONE);
                    buton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button btntrima=findViewById(R.id.button6);
        btntrima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("terima").setValue("1");
                FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("pid").addListenerForSingleValueEvent(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key=dataSnapshot.getValue().toString();

                        FirebaseDatabase.getInstance().getReference().child("Transaksi").push().setValue(new Transaksi(uid, key));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        Button btntlk=findViewById(R.id.button7);
        btntlk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("pid").addListenerForSingleValueEvent(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key=dataSnapshot.getValue().toString();
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("tpos").addListenerForSingleValueEvent(new ValueEventListener() {
                            String key2;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                key2=dataSnapshot.getValue().toString();
                                FirebaseDatabase.getInstance().getReference().child("Proyek").child(key).child(key2).addListenerForSingleValueEvent(new ValueEventListener() {
                                    String kkey;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        kkey=dataSnapshot.getValue().toString();
                                        int h=Integer.parseInt(kkey);
                                        h++;
                                        FirebaseDatabase.getInstance().getReference().child("Proyek").child(key).child(key2).setValue(Integer.toString(h));
                                        FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("pid").setValue("0");
                                        FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("tpos").setValue("0");
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


                finish();
            }
        });

        Button selesai=findViewById(R.id.button8);
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("terima").setValue("0");
                FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("tpos").setValue("0");
                DatabaseReference fr=FirebaseDatabase.getInstance().getReference().child("Welders").child(uid).child("pid");
                fr.addListenerForSingleValueEvent(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key=dataSnapshot.getValue().toString();
                        FirebaseDatabase.getInstance().getReference().child("Proyek").child(key).child("status").setValue("1");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                fr.setValue("0");
                finish();

            }
        });

    }
    public void pluscout(){
        cout++;
    }
}
