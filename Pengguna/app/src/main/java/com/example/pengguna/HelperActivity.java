package com.example.pengguna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Proyek");

        ref.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            List<String> item=new ArrayList<>();
            List<String> item2=new ArrayList<>();
            List<String> item3=new ArrayList<>();
            List<String> item4=new ArrayList<>();
            List<String> item5=new ArrayList<>();
            List<String> item6=new ArrayList<>();
            int cun[];

            ProgressBar barbar=findViewById(R.id.progressBar);
            TextView kosong=findViewById(R.id.textView98);
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item.clear();
                item2.clear();
                item3.clear();
                item4.clear();
                item5.clear();
                item6.clear();

                for (DataSnapshot post: dataSnapshot.getChildren()){
                    if(post.child("status").getValue().toString().equals("0")){
                        item.add(post.child("namaproyek").getValue().toString());
                        item2.add(post.child("tanggalmulai").getValue().toString());
                        item6.add(post.child("bedahari").getValue().toString());
                        item3.add(post.getKey());
                        if(post.child("helper").exists()){
                            item4.add(post.child("helper").getValue().toString());
                        }
                        else{
                            item4.add("0");
                        }
                        if(post.child("hargahelper").exists()){
                            item5.add(post.child("hargahelper").getValue().toString());
                        }
                        else{
                            item5.add("0");
                        }
                    }
                }
                if(item.size()>0){
                    cun=new int[item.size()];
                    for(int u=0; u<item.size(); u++){
                        cun[u]=0;
                    }
                    ListView lisst=findViewById(R.id.listku);
                    CustomAdapter2 custom=new CustomAdapter2(getApplicationContext(), item, item2, item3, item4, item5, item6, cun);
                    lisst.setAdapter(custom);
                    barbar.setVisibility(View.GONE);
                }
                else{
                    barbar.setVisibility(View.GONE);
                    kosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
