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

public class LihatProyekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_proyek);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Proyek");

        ref.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            List<String> item=new ArrayList<>();
            List<String> item2=new ArrayList<>();
            List<String> item3=new ArrayList<>();
            ProgressBar barbar=findViewById(R.id.progressBar);
            TextView kosong=findViewById(R.id.textView98);
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    item.add(post.child("namaproyek").getValue().toString());
                    item2.add(post.child("tipe").getValue().toString());
                    item3.add(post.getKey());
                }
                if(item.size()>0){

                    ListView lisst=findViewById(R.id.listku);
                    CustomAdapter custom=new CustomAdapter(getApplicationContext(), item, item2, item3);
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
