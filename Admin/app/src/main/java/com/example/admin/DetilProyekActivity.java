package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetilProyekActivity extends AppCompatActivity {
    private TextView owener;
    private TextView nama;
    private TextView jenis;
    private TextView lingkup;
    private TextView mulai;
    private TextView selesai;
    private TextView alamat;
    private TextView t[]=new TextView[10];;
    private ConstraintLayout constraint;
    private ConstraintSet ctr;
    private int j=0;
    private int count=0;
    private String spek;
    private String pos;
    private FirebaseAuth mauth;
    private List<String> item=new ArrayList<>();
    private List<String> item2=new ArrayList<>();
    private List<String> item3=new ArrayList<>();
    private List<String> item4=new ArrayList<>();
    private String keyy;
    private int coun;
    private Button del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_proyek);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        TextView vieww=findViewById(R.id.textView28);
        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("email");

        mauth= FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();

        String uid=user.getUid();

        FirebaseDatabase.getInstance().getReference().child("Admin").child(uid).child("jenis").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                if(key.equals("Manajer Lapangan")||key.equals("Manajer Administrasi")){
                    Button btn=findViewById(R.id.button29);
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        owener=findViewById(R.id.textView29);
        nama=findViewById(R.id.textView31);
        jenis=findViewById(R.id.textView33);
        lingkup=findViewById(R.id.textView35);
        mulai=findViewById(R.id.textView37);
        selesai=findViewById(R.id.textView39);
        constraint=findViewById(R.id.constraintt);
        ctr=new ConstraintSet();


        final Button track=findViewById(R.id.button16);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gg=new Intent(DetilProyekActivity.this, MapsActivity.class);
                gg.putExtra("email", pessan);
                startActivity(gg);;
            }
        });

        Button edit=findViewById(R.id.button28);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gg=new Intent(DetilProyekActivity.this, EditProyekActivity.class);
                gg.putExtra("email", pessan);
                startActivity(gg);;
            }
        });

        Button delete=findViewById(R.id.button29);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference hehe=FirebaseDatabase.getInstance().getReference().child("Welders");
                hehe.orderByChild("pid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
                    ArrayList<String> hui=new ArrayList<>();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot podt:dataSnapshot.getChildren()){
                            hui.add(podt.getKey());
                        }
                        if(hui.size()>0) {
                            for (int i = 0; i < hui.size(); i ++) {
                                FirebaseDatabase.getInstance().getReference().child("Welders").child(hui.get(i)).child("pid").setValue("0");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek");
                res.child(pessan).removeValue();
                finish();
            }
        });
//        vieww.setText(pessan);
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan);
        ref.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            String sts;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sts= dataSnapshot.getValue().toString();
                if(sts.equals("1")){
                    track.setVisibility(View.GONE);
                    FrameLayout layy=findViewById(R.id.frameLayout8);
                    layy.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
            String uid;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid=dataSnapshot.getValue().toString();
                DatabaseReference hey= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                hey.child("namadepan").addListenerForSingleValueEvent(new ValueEventListener() {
                    String ow;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ow=dataSnapshot.getValue().toString();
                        owener.setText(ow);
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

        ref.child("namaproyek").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    nama.setText(key);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jenisproyek").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    jenis.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tipe").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    lingkup.setText(key);
                    getspek(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tanggalmulai").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    mulai.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tanggalselesai").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    selesai.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("alamat").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    TextView piuw = findViewById(R.id.textView41);
                    piuw.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah1").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key= dataSnapshot.getValue().toString();
                    TextView view=findViewById(R.id.textView67);
                    TextView view2=findViewById(R.id.textView61);
                    TextView view3=findViewById(R.id.textView72);

                    if(key.equals("0")){
                        view.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                    }
                    else{
                        view.setText(key);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah2").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key= dataSnapshot.getValue().toString();
                    TextView view=findViewById(R.id.textView68);
                    TextView view2=findViewById(R.id.textView62);
                    TextView view3=findViewById(R.id.textView73);

                    if(key.equals("0")){
                        view.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                    }
                    else{
                        view.setText(key);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah3").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key= dataSnapshot.getValue().toString();
                    TextView view=findViewById(R.id.textView69);
                    TextView view2=findViewById(R.id.textView63);
                    TextView view3=findViewById(R.id.textView74);

                    if(key.equals("0")){
                        view.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                    }
                    else{
                        view.setText(key);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah4").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    key= dataSnapshot.getValue().toString();
                    TextView view=findViewById(R.id.textView70);
                    TextView view2=findViewById(R.id.textView64);
                    TextView view3=findViewById(R.id.textView75);

                    if(key.equals("0")){
                        view.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                    }
                    else{
                        view.setText(key);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah5").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    key= dataSnapshot.getValue().toString();
                    TextView view=findViewById(R.id.textView71);
                    TextView view2=findViewById(R.id.textView65);
                    TextView view3=findViewById(R.id.textView76);

                    if(key.equals("0")){
                        view.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                    }
                    else{
                        view.setText(key);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek1").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ow=dataSnapshot.getValue().toString();
                    getpos(ow);
                    ow=ow.replace("+", "\n");
                    TextView vieww=findViewById(R.id.textView61);
                    vieww.setText(ow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek2").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    ow=dataSnapshot.getValue().toString();
                    getpos(ow);
                    ow=ow.replace("+", "\n");
                    TextView vieww=findViewById(R.id.textView62);
                    vieww.setText(ow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek3").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ow=dataSnapshot.getValue().toString();
                    getpos(ow);
                    ow=ow.replace("+", "\n");
                    TextView vieww=findViewById(R.id.textView63);
                    vieww.setText(ow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek4").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ow=dataSnapshot.getValue().toString();
                    getpos(ow);
                    ow=ow.replace("+", "\n");
                    TextView vieww=findViewById(R.id.textView64);
                    vieww.setText(ow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("proyek5").addValueEventListener(new ValueEventListener() {
            String ow;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ow=dataSnapshot.getValue().toString();
                    getpos(ow);
                    ow=ow.replace("+", "\n");
                    TextView vieww=findViewById(R.id.textView65);
                    vieww.setText(ow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        final TextView pindah=findViewById(R.id.textView72);
        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah1");
                pindahh.putExtra("marine", jenis.getText().toString());
                TextView view3=findViewById(R.id.textView87);
                pindahh.putExtra("sertif", view3.getText().toString());
                pindahh.putExtra("namap", nama.getText().toString());

                startActivity(pindahh);
            }
        });

        final TextView pindah2=findViewById(R.id.textView73);
        pindah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah2");
                pindahh.putExtra("marine", jenis.getText().toString());
                TextView view3=findViewById(R.id.textView87);
                pindahh.putExtra("sertif", view3.getText().toString());
                pindahh.putExtra("namap", nama.getText().toString());

                startActivity(pindahh);
            }
        });

        ref.child("jumlah1f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    key= dataSnapshot.getValue().toString();
                    TextView view3=findViewById(R.id.textView72);

                    if(key.equals("0")){
                        view3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah2f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    key= dataSnapshot.getValue().toString();
                    TextView view3=findViewById(R.id.textView73);

                    if(key.equals("0")){
                        view3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah3f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    key= dataSnapshot.getValue().toString();
                    TextView view3=findViewById(R.id.textView74);

                    if(key.equals("0")){
                        view3.setVisibility(View.GONE);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah4f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key= dataSnapshot.getValue().toString();
                    TextView view3=findViewById(R.id.textView75);

                    if(key.equals("0")){
                        view3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah5f").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    key= dataSnapshot.getValue().toString();
                    TextView view3=findViewById(R.id.textView76);

                    if(key.equals("0")){
                        view3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("sertifm").addValueEventListener(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key= dataSnapshot.getValue().toString();
                    TextView view3=findViewById(R.id.textView87);
                    view3.setText(key);
                }
                else{
                    TextView view3=findViewById(R.id.textView87);
                    view3.setText("Tidak Ada");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("hargatotal").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    key=dataSnapshot.getValue().toString();
                    TextView liew=findViewById(R.id.textVieww);
                    NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                    String aa=nf3.format(Integer.parseInt(key));
                    liew.setText(aa);
                }
                else{
                    ref.child("harga").addListenerForSingleValueEvent(new ValueEventListener() {
                        String keys;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            keys=dataSnapshot.getValue().toString();
                            TextView liew=findViewById(R.id.textVieww);
                            NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                            String aa=nf3.format(Integer.parseInt(keys.replace(".", "")));
                            liew.setText(aa);
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

        FirebaseDatabase.getInstance().getReference().child("Transaksi").orderByChild("pid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
            List<String>hh=new ArrayList<>();
            FrameLayout frame=findViewById(R.id.frameLayout9);

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    hh.add(post.child("wid").getValue().toString());
                }
                final TextView piew=findViewById(R.id.textView61);

                if(hh.size()>0){
                    for(int i=0; i<hh.size(); i++){
                        keyy =hh.get(i);
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(hh.get(i)).child("namalengkap").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                item.add(dataSnapshot.getValue().toString());
                                int te=140;
                                TextView t[]=new TextView[item.size()];
                                for(int i=0; i<item.size(); i++){
                                    FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(40,te,0,0);
                                    t[i]=new TextView(DetilProyekActivity.this);
                                    t[i].setText("Nama: "+item.get(i));
                                    t[i].setLayoutParams(params);;
                                    frame.addView(t[i]);
                                    te+=190;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(hh.get(i)).child("notelp").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                item2.add(dataSnapshot.getValue().toString());
                                int ue=200;
                                TextView u[]=new TextView[item2.size()];
                                for(int j=0; j<item2.size(); j++){
                                    FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(40,ue,0,0);
                                    u[j]=new TextView(DetilProyekActivity.this);
                                    u[j].setText("Nomor Telepon: "+ item2.get(j));
                                    u[j].setLayoutParams(params);;
                                    frame.addView(u[j]);
                                    ue+=190;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("Welders").child(hh.get(i)).child("posisi").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                item4.add(keyy);
                                item3.add(dataSnapshot.getValue().toString());
                                int ve=260;
                                TextView v[]=new TextView[item3.size()];
                                Button b[]=new Button[item3.size()];
                                for(int j=0; j<item2.size(); j++){
                                    FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(40,ve,0,0);
                                    v[j]=new TextView(DetilProyekActivity.this);
                                    v[j].setText(item3.get(j));
                                    v[j].setLayoutParams(params);
                                    frame.addView(v[j]);
                                    ve+=190;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextView pindah3=findViewById(R.id.textView74);
        pindah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah3");
                pindahh.putExtra("marine", jenis.getText().toString());
                TextView view3=findViewById(R.id.textView87);
                pindahh.putExtra("sertif", view3.getText().toString());
                pindahh.putExtra("namap", nama.getText().toString());

                startActivity(pindahh);
            }
        });

        final TextView pindah4=findViewById(R.id.textView75);
        pindah4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah4");
                pindahh.putExtra("marine", jenis.getText().toString());
                TextView view3=findViewById(R.id.textView87);
                pindahh.putExtra("sertif", view3.getText().toString());
                pindahh.putExtra("namap", nama.getText().toString());

                startActivity(pindahh);
            }
        });

        final TextView pindah5=findViewById(R.id.textView76);
        pindah5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahh=new Intent(DetilProyekActivity.this, CariWelderActivity.class);
                pindahh.putExtra("spek", spek);
                pindahh.putExtra("pos", pos);
                pindahh.putExtra("key", pessan);
                pindahh.putExtra("jumlahnya", "jumlah5");
                pindahh.putExtra("marine", jenis.getText().toString());
                TextView view3=findViewById(R.id.textView87);
                pindahh.putExtra("sertif", view3.getText().toString());
                pindahh.putExtra("namap", nama.getText().toString());

                startActivity(pindahh);
            }
        });

    }

    public void getspek(String setring){
        spek=setring;
    }
    public void getpos(String setring){
        pos=setring;
    }
}
