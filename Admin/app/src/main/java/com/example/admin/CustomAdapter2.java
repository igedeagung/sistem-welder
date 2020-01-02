package com.example.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class CustomAdapter2 extends BaseAdapter {
    Context con;
    ArrayList<String> data= new ArrayList<>();
    ArrayList<String> data2= new ArrayList<>();
    ArrayList<String> data4= new ArrayList<>();
    String data3;
    String data5;
    LayoutInflater inflater;
    DatabaseReference juju;

    public CustomAdapter2(Context context, ArrayList<String> data,ArrayList<String> data2, String data3, ArrayList<String> data4, String data5){
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
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row=null;
        if(convertView==null){
            row=inflater.inflate(R.layout.jenis_list, null);

        }
        else{
            row=convertView;
        }
        TextView datas=row.findViewById(R.id.textVieww5);
        TextView datas2=row.findViewById(R.id.textVieww4);
        final Button button=row.findViewById(R.id.button23);
        final ListView lisst=row.findViewById(R.id.lilis);
        final String datax=data5+"f";
        datas.setText(data.get(position));
        datas2.setText(data2.get(position));

        juju=FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3).child(datax);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganti(position, datax, parent);
            }
        });
        return row;
    }

    public void ganti(int position, String datax, final ViewGroup parent){
        DatabaseReference res= FirebaseDatabase.getInstance().getReference().child("Welders").child(data4.get(position)).child("pid");
        res.setValue(data3);
        DatabaseReference res2= FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3);
        res2.child(datax).addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key= dataSnapshot.getValue().toString();
                int k=Integer.parseInt(key);
                int jul=k-1;
                String ih=Integer.toString(jul);
                getstr(k);

//                        notifyDataSetChanged();
                juju.setValue(ih);

//                        notifyDataSetChanged();

                if (jul==0){
                    ((Activity)parent.getContext()).finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private String kaka;
    public void getstr(int k){
        kaka=Integer.toString(k);
    }
}
