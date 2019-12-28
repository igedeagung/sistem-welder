package com.example.pengguna;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context con;
    List<String> data=new ArrayList<>();
    List<String> data2=new ArrayList<>();
    List<String> data3=new ArrayList<>();

    LayoutInflater inflater;

    public CustomAdapter(Context context, List<String> data, List<String> data2, List<String> data3){
        this.con=context;
        this.data=data;
        this.data2=data2;
        this.data3=data3;
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
        convertView=inflater.inflate(R.layout.list_proyek, null);
        TextView datas=convertView.findViewById(R.id.textView96);
        TextView datas2=convertView.findViewById(R.id.textView97);
        Button button=convertView.findViewById(R.id.butondetail);

        datas.setText(data.get(position));
        datas2.setText(data2.get(position));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gogo=new Intent(parent.getContext(), ListProyekActivity.class);
                gogo.putExtra("id", data3.get(position));
                parent.getContext().startActivity(gogo);
            }
        });
        return convertView;
    }
}
