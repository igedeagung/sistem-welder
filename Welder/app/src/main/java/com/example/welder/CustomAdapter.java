package com.example.welder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    Context con;
    ArrayList<String> item1;
    ArrayList<String> item2;
    ArrayList<String> item3;

    LayoutInflater inflater;
    DatabaseReference juju;

    public CustomAdapter(Context context, ArrayList<String> item1, ArrayList<String> item2, ArrayList<String> item3){
        this.con=context;
        this.item1=item1;
        this.item2=item2;
        this.item3=item3;
        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return item1.size();
    }

    @Override
    public Object getItem(int position) {
        return item1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row=null;
        if(convertView==null){
            row=inflater.inflate(R.layout.list_riwayat, null);
        }
        else{
            row=convertView;
        }

        TextView datas=row.findViewById(R.id.textVieww4);
        TextView datas2=row.findViewById(R.id.textVieww5);
        Button buton=row.findViewById(R.id.button23);
        datas.setText(item1.get(position));
        datas2.setText(item2.get(position));

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah=new Intent(parent.getContext(), DetilRiwayatActivity.class);
                pindah.putExtra("key", item3.get(position));
                parent.getContext().startActivity(pindah);
            }
        });
        return row;
    }
}