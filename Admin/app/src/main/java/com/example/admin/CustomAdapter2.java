package com.example.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomAdapter2 extends BaseAdapter {
    Context con;
    String[] data;
    String[] data2;
    String[] data4;
    String data3;
    String data5;
    LayoutInflater inflater;

    public CustomAdapter2(Context context, String[] data, String[] data2, String data3, String[] data4, String data5){
        this.con=context;
        this.data=data;
        this.data2=data2;
        this.data3=data3;
        this.data4=data4;
        this.data5=data5;
        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView=inflater.inflate(R.layout.jenis_list, null);
        TextView datas=convertView.findViewById(R.id.textVieww5);
        TextView datas2=convertView.findViewById(R.id.textVieww4);
        Button button=convertView.findViewById(R.id.button23);

        final String datax=data5+"f";
        datas.setText(data[position]);
        datas2.setText(data2[position]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference res= FirebaseDatabase.getInstance().getReference().child("Welders").child(data4[position]).child("pid");
                res.setValue(data3);
                DatabaseReference res2= FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3);
                res2.child(datax).addValueEventListener(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key= dataSnapshot.getValue().toString();
                        int k=Integer.parseInt(key);
                        k=k-1;
                        key=Integer.toString(k);
                        DatabaseReference rese= FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3).child(datax);
                        rese.setValue(key);
                        if (k==0){
                            Intent detil= new Intent(parent.getContext(),DetilProyekActivity.class);
                            parent.getContext().startActivity(detil);
                            ((Activity)con).finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return convertView;
    }
}
