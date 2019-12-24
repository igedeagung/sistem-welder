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

public class CustomAdapter extends BaseAdapter {
    Context con;
    String[] data;
    String[] data2;
    String[] data3;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] data, String[] data2, String[] data3){
        this.con=context;
        this.data=data;
        this.data2=data2;
        this.data3=data3;
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
        convertView=inflater.inflate(R.layout.welder_list, null);
        TextView datas=convertView.findViewById(R.id.textView4);
        TextView datas2=convertView.findViewById(R.id.textView5);
        Button button=convertView.findViewById(R.id.buttton);

        datas.setText(data[position]);
        datas2.setText(data2[position]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detil= new Intent(parent.getContext(),DetilWelderctivity.class);
                detil.putExtra("email", data3[position]);
                parent.getContext().startActivity(detil);

            }
        });
        return convertView;
    }
}
