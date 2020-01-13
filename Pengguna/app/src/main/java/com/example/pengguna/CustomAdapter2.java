package com.example.pengguna;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter2 extends BaseAdapter {
    Context con;
    List<String> data=new ArrayList<>();
    List<String> data2=new ArrayList<>();
    List<String> data3=new ArrayList<>();
    List<String> data4=new ArrayList<>();
    List<String> data5=new ArrayList<>();
    List<String> data6=new ArrayList<>();
    LayoutInflater inflater;
    int count[];


    public CustomAdapter2(Context context, List<String> data, List<String> data2, List<String> data3, List<String> data4, List<String> data5, List<String> data6, int cun[]){
        this.con=context;
        this.data=data;
        this.data2=data2;
        this.data3=data3;
        this.data4=data4;
        this.data5=data5;
        this.data6=data6;
        this.count=cun;
        inflater=(LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView=inflater.inflate(R.layout.list_helper, null);
        TextView datas=convertView.findViewById(R.id.textView96);
        TextView datas2=convertView.findViewById(R.id.textView97);
        TextView datascmpr=convertView.findViewById(R.id.textView98);

        Button buttonadd=convertView.findViewById(R.id.imageButton43);
        Button buttonmin=convertView.findViewById(R.id.imageButton42);

        final Button buttonok=convertView.findViewById(R.id.button40);

        final TextView harga=convertView.findViewById(R.id.textView118);
        final TextView jumla=convertView.findViewById(R.id.textView116);
        int perhar=0;
        if(data.get(position).equals("Steel Structure")||data.get(position).equals("Storage Tank")){
            perhar=80000;
        }
        if(data.get(position).contains("Kapal") ||data.get(position).equals("Stainless Steel")||data.get(position).equals("Carbon Steel")||data.get(position).equals("Pressure Tank")){
            perhar=96000;
        }
        if(data.get(position).contains("Offshore/Onshore")){
            perhar=144000;
        }
        if(data.get(position).contains("Non Ferro")){
            perhar=120000;
        }
        if(data.get(position).contains("Las Umum")){
            perhar=100000;
        }
        final int har=perhar;
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[position]++;
                jumla.setText(Integer.toString(count[position]));
                long hargas=(har*count[position])*Integer.parseInt(data6.get(position));
                NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                String aa=nf3.format(hargas);
                harga.setText(aa);

                String cek=harga.getText().toString();
                if(cek.equals("0")){
                    buttonok.setEnabled(false);
                }
                else
                {
                    buttonok.setEnabled(true);
                }
            }
        });
        buttonmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count[position]>0){
                    count[position]--;
                }
                jumla.setText(Integer.toString(count[position]));
                long hargas=(har*count[position])*Integer.parseInt(data6.get(position));
                NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                String aa=nf3.format(hargas);
                harga.setText(aa);

                String cek=harga.getText().toString();
                if(cek.equals("0")){
                    buttonok.setEnabled(false);
                }
                else
                {
                    buttonok.setEnabled(true);
                }

            }
        });
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3.get(position)).child("helper").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            keys=dataSnapshot.getValue().toString();
                            int jum=Integer.parseInt(keys)+Integer.parseInt(jumla.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3.get(position)).child("helper").setValue(Integer.toString(jum));
                        }
                        else{
                            FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3.get(position)).child("helper").setValue(jumla.getText().toString());
                        }
                        jumla.setText("0");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3.get(position)).child("hargahelper").addListenerForSingleValueEvent(new ValueEventListener() {
                    String keys;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            keys=dataSnapshot.getValue().toString();
                            int jum=Integer.parseInt(keys.replace(".", ""))+Integer.parseInt(harga.getText().toString().replace(".", ""));
                            NumberFormat nf3=NumberFormat.getInstance(new Locale("da", "DK"));
                            String aa=nf3.format(jum);
                            FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3.get(position)).child("hargahelper").setValue(aa);
                        }
                        else{
                            FirebaseDatabase.getInstance().getReference().child("Proyek").child(data3.get(position)).child("hargahelper").setValue(harga.getText().toString());
                        }
                        harga.setText("0");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                count[position]=0;

                String cek=harga.getText().toString();
                if(cek.equals("0")){
                    buttonok.setEnabled(false);
                }
                else
                {
                    buttonok.setEnabled(true);
                }
            }
        });

        datas.setText(data.get(position));
        datas2.setText(data2.get(position));
        datascmpr.setText("Helper: "+data4.get(position)+"\nHarga Helper: "+data5.get(position));

        return convertView;
    }
}
