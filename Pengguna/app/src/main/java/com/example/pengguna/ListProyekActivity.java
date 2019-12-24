package com.example.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListProyekActivity extends AppCompatActivity {
    private String uid;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Get your reference to the node with all the entries
        ref = FirebaseDatabase.getInstance().getReference().child("Proyek");

        // Query for all entries with a certain child with value equal to something
        Query allPostFromAuthor = ref.orderByChild("uid").equalTo(uid);

        // Add listener for Firebase response on said query
        allPostFromAuthor.addValueEventListener( new ValueEventListener(){
            List<String> hasil=new ArrayList<>();
            String putty;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren() ){
                    // Iterate through all posts with the same author
                    hasil.add(post.getValue().toString());
                }
                ConstraintLayout layout=findViewById(R.id.listProyek);
                ConstraintSet setan=new ConstraintSet();
                TextView t[]=new TextView[10];
                int he=60;


                for(int j=0;j<10;j++)
                {
                    t[j]=new TextView(ListProyekActivity.this);
                    t[j].setId(View.generateViewId());
                    String fue=Integer.toString(hasil.size()) ;
                    t[j].setText(fue);
                    layout.addView(t[j], 0);
                    setan.clone(layout);
                    setan.connect(t[j].getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, he);
                    setan.applyTo(layout);
                    he+=100;
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        setContentView(R.layout.activity_list_proyek);
    }
}
