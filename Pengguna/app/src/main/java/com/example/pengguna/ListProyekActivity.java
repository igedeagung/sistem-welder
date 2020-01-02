package com.example.pengguna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengguna.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListProyekActivity extends AppCompatActivity {
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
    private List<String> jumlah;
    private List<String> proyek;
    private DatabaseReference ref;
    private String pessan;
    private Button buttonku;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_proyek);

//        TextView vieww=findViewById(R.id.textView28);
        Bundle bundle=getIntent().getExtras();
        pessan=bundle.getString("id");

        nama=findViewById(R.id.textView31);
        jenis=findViewById(R.id.textView33);
        lingkup=findViewById(R.id.textView35);
        mulai=findViewById(R.id.textView37);
        selesai=findViewById(R.id.textView39);
        constraint=findViewById(R.id.constraintt);
        ctr=new ConstraintSet();

//        vieww.setText(pessan);
        ref= FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan);

        ref.child("namaproyek").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                nama.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jenisproyek").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                jenis.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tipe").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                lingkup.setText(key);
                getspek(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tanggalselesai").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                selesai.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("tanggalmulai").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key=dataSnapshot.getValue().toString();
                mulai.setText(key);
                Date date=new Date(), date2=new Date();
                try{
                    String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    date = new SimpleDateFormat("yyyy-MM-dd").parse(key);
                    date2=new SimpleDateFormat("yyyy-MM-dd").parse(dates);
                    final long beda= (date2.getTime()-date.getTime())/86400000;

                    ref.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                        String key;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            key=dataSnapshot.getValue().toString();
//                            final int  hau=Integer.parseInt(key);
                            ref.child("sudahnilai").addListenerForSingleValueEvent(new ValueEventListener() {
                                String kekey;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    kekey=dataSnapshot.getValue().toString();
                                    if(kekey.equals("0")&&key.equals("1")){
                                        buttonku=findViewById(R.id.button38);
                                        buttonku.setVisibility(View.VISIBLE);
                                        buttonku.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ShowDialog();
                                            }
                                        });
                                    }
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



                }catch (ParseException e) {              // Insert this block.
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
                key=dataSnapshot.getValue().toString();
                TextView vieww=findViewById(R.id.textView41);
                vieww.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ref.child("jumlah1").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                final TextView piew=findViewById(R.id.textView100);
                TextView piew2=findViewById(R.id.textView101);
                if(!key.equals("0")){
                    piew2.setText(key);
                    ref.child("proyek1").addListenerForSingleValueEvent(new ValueEventListener() {
                        String ow;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ow=dataSnapshot.getValue().toString();
                            getpos(ow);
                            ow=ow.replace("+", "\n");
                            piew.setText(ow);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    piew.setVisibility(View.GONE);
                    piew2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah2").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                final TextView piew=findViewById(R.id.textView102);
                TextView piew2=findViewById(R.id.textView103);
                if(!key.equals("0")){
                    piew2.setText(key);
                    ref.child("proyek2").addListenerForSingleValueEvent(new ValueEventListener() {
                        String ow;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ow=dataSnapshot.getValue().toString();
                            getpos(ow);
                            ow=ow.replace("+", "\n");
                            piew.setText(ow);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    piew.setVisibility(View.GONE);
                    piew2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah3").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                final TextView piew=findViewById(R.id.textView104);
                TextView piew2=findViewById(R.id.textView105);
                if(!key.equals("0")){
                    piew2.setText(key);
                    ref.child("proyek3").addListenerForSingleValueEvent(new ValueEventListener() {
                        String ow;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ow=dataSnapshot.getValue().toString();
                            getpos(ow);
                            ow=ow.replace("+", "\n");
                            piew.setText(ow);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    piew.setVisibility(View.GONE);
                    piew2.setVisibility(View.GONE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah4").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            final TextView piew=findViewById(R.id.textView106);
            TextView piew2=findViewById(R.id.textView107);
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                if(!key.equals("0")){
                    piew2.setText(key);
                    ref.child("proyek4").addListenerForSingleValueEvent(new ValueEventListener() {
                        String ow;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ow=dataSnapshot.getValue().toString();
                            getpos(ow);
                            ow=ow.replace("+", "\n");
                            piew.setText(ow);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    piew.setVisibility(View.GONE);
                    piew2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("jumlah5").addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            final TextView piew=findViewById(R.id.textView108);
            TextView piew2=findViewById(R.id.textView109);
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                if(!key.equals("0")){
                    piew2.setText(key);
                    ref.child("proyek5").addListenerForSingleValueEvent(new ValueEventListener() {
                        String ow;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ow=dataSnapshot.getValue().toString();
                            getpos(ow);
                            ow=ow.replace("+", "\n");
                            piew.setText(ow);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    piew.setVisibility(View.GONE);
                    piew2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        TextView gg=findViewById(R.id.textView61);
//        gg.setText(pessan);
        DatabaseReference rese=FirebaseDatabase.getInstance().getReference().child("Welders");
        rese.orderByChild("pid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
            List<String> item=new ArrayList<>();
            List<String> item2=new ArrayList<>();
            List<String> item3=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    item.add(post.child("namalengkap").getValue().toString());
                    item2.add(post.child("notelp").getValue().toString());
                    item3.add(post.child("posisi").getValue().toString());
                }
                int te=140;
                int ue=200;
                int ve=260;
                FrameLayout frame=findViewById(R.id.frameLayout9);
                TextView t[]=new TextView[item.size()];
                TextView u[]=new TextView[item2.size()];
                TextView v[]=new TextView[item3.size()];

                for(int i=0; i<item.size(); i++){
                    FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(40,te,0,0);
                    t[i]=new TextView(ListProyekActivity.this);
                    t[i].setText("Nama: "+item.get(i));
                    t[i].setLayoutParams(params);;
                    frame.addView(t[i]);
                    te+=190;
                }
                for(int j=0; j<item2.size(); j++){
                    FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(40,ue,0,0);
                    u[j]=new TextView(ListProyekActivity.this);
                    u[j].setText("Nomor Telepon: "+ item2.get(j));
                    u[j].setLayoutParams(params);;
                    frame.addView(u[j]);
                    ue+=190;
                }

                for(int j=0; j<item2.size(); j++){
                    FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(40,ve,0,0);
                    v[j]=new TextView(ListProyekActivity.this);
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
    public void getspek(String setring){
        spek=setring;
    }
    public void getpos(String setring){
        pos=setring;
    }
    public void ShowDialog(){
        final AlertDialog.Builder popdialog=new AlertDialog.Builder(this);

        LinearLayout linearLayout= new LinearLayout(this);
        final RatingBar rating= new RatingBar(this);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        linearLayout.addView(rating);

        popdialog.setTitle("Beri Nilai !");
        popdialog.setView(linearLayout);


        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        popdialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String hasil= String.valueOf(rating.getProgress());
                DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Welders");
                res.orderByChild("pid").equalTo(pessan).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot post: dataSnapshot.getChildren()){
                            String key=post.getKey();
                            FirebaseDatabase.getInstance().getReference().child("Welders").child(key).child("rating").setValue(hasil);
                            FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan).child("sudahnilai").setValue("1");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
                buttonku.setVisibility(View.GONE);
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        popdialog.create();
        popdialog.show();
    }
}
