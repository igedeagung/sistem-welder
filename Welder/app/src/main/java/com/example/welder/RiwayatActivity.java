package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RiwayatActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private ArrayList<String> item1=new ArrayList<>();
    private ArrayList<String> item2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mauth= FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final ProgressBar barbar=findViewById(R.id.progressBar);
        barbar.setVisibility(View.VISIBLE);
        final TextView piew=findViewById(R.id.textView25);

        FirebaseDatabase.getInstance().getReference().child("Transaksi").orderByChild("wid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            ArrayList<String> key=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key.clear();
                item1.clear();
                item2.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    key.add(post.child("pid").getValue().toString());
                }
                if(key.size()>0){

                    piew.setVisibility(View.INVISIBLE);

                    for(int i=0; i<key.size(); i++){
                        final DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek").child(key.get(i));
                        res.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                            String kuy;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                kuy=dataSnapshot.getValue().toString();
                                if(kuy.equals("1")){
                                    res.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            item1.add(dataSnapshot.child("namaproyek").getValue().toString());
                                            item2.add(dataSnapshot.child("sudahnilai").getValue().toString());
                                            ListView liis=findViewById(R.id.list);
                                            CustomAdapter customAdapter= new CustomAdapter(getApplicationContext(), item1, item2, key);
                                            customAdapter.notifyDataSetChanged();
                                            barbar.setVisibility(View.INVISIBLE);
                                            liis.setAdapter(customAdapter);
//                                TextView pieww=findViewById(R.id.textView3);
//                                pieww.setText(Integer.toString(item2.size()));
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


                    }


                }
                else{
                    barbar.setVisibility(View.INVISIBLE);
                    piew.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tambahya(String setring){
        item1.add(setring);
    }

    public void tambahya2(String setring){
        item2.add(setring);
    }
}
