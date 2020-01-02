package com.example.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context con;
    ArrayList<String>data= new ArrayList<>();
    ArrayList<String>data2= new ArrayList<>();
    ArrayList<String>data3= new ArrayList<>();
    String tipes;

    public CustomAdapter(Context context, ArrayList<String>data, ArrayList<String>data2, ArrayList<String>data3, String tipes){
        this.con=context;
        this.data=data;
        this.data2=data2;
        this.data3=data3;
        this.tipes=tipes;
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
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(con);
            convertView=inflater.inflate(R.layout.welder_list, parent, false);
        }
        TextView datas=convertView.findViewById(R.id.textView4);
        TextView datas2=convertView.findViewById(R.id.textView5);
        Button button=convertView.findViewById(R.id.buttton);

        datas.setText(data.get(position));
        datas2.setText(data2.get(position));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipes.equals("welder")){
                    Intent detil= new Intent(parent.getContext(),DetilWelderctivity.class);
                    detil.putExtra("email", data3.get(position));
                    parent.getContext().startActivity(detil);

                }
                if (tipes.equals("proyek")){
                    Intent detil= new Intent(parent.getContext(),DetilProyekActivity.class);
                    detil.putExtra("email", data3.get(position));
                    parent.getContext().startActivity(detil);
                }
                if(tipes.equals("pengguna")){
                    Intent detil= new Intent(parent.getContext(),ListPenggunaActivity.class);
                    detil.putExtra("email", data3.get(position));
                    parent.getContext().startActivity(detil);
                }
            }
        });

        return convertView;
    }
}
