package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import java.lang.Math;

public class CariWelderActivity extends AppCompatActivity {

    private String spekk;
    private String[] item, item2, item4;
    private String item3, item5;
    private int flag=0;
    private int flagg;
    private ListView lisst;
    private CustomAdapter2 customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_welder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("spek");
        final String pessan2=bundle.getString("pos");
        final String pessan3=bundle.getString("key");
        final String julah=bundle.getString("jumlahnya");
        final String marine=bundle.getString("marine");

        if(pessan2.contains("6GR")){
            flag=3;
        }
        else if(pessan2.contains("6G")){
            flag=2;
        }
        else{
            flag=1;
        }

        if(pessan.equals("SMAW")){
            spekk="spesifikasi1";
        }
        if(pessan.equals("FCAW")){
            spekk="spesifikasi2";
        }
        if(pessan.equals("GTAW")){
            spekk="spesifikasi3";
        }
        if(pessan.equals("SMAW/FCAW")){
            spekk="spesifikasi4";
        }
        if(pessan.equals("SMAW/GTAW")){
            spekk="spesifikasi5";
        }
        if(pessan.equals("OAW")){
            spekk="spesifikasi6";
        }


        FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan3).child("alamat").addValueEventListener(new ValueEventListener() {
            String keya;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keya=dataSnapshot.getValue().toString();
                final LatLng loc1=getLocationFromAddress(getApplicationContext(), keya);

                DatabaseReference res= FirebaseDatabase.getInstance().getReference().child("Welders");
                res.orderByChild(spekk).equalTo(pessan).addValueEventListener(new ValueEventListener() {
                    String acc;
                    String sibuk;
                    String pos;
                    String keyy;

                    String alamats;
                    String flagmar="0";
                    ArrayList<String> hasil=new ArrayList<>();
                    ArrayList<String> hasil2=new ArrayList<>();
                    ArrayList<String> hasil3=new ArrayList<>();
                    ArrayList<String> hasil4=new ArrayList<>();
                    ArrayList<String> hasil5=new ArrayList<>();
                    ArrayList<String> hasil6=new ArrayList<>();

                    ProgressBar barbar=findViewById(R.id.progressBar3);

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        hasil.clear();
                        hasil2.clear();
                        hasil3.clear();
                        hasil4.clear();
                        hasil5.clear();
                        hasil6.clear();
                        for(DataSnapshot post : dataSnapshot.getChildren() ){
                            // Iterate through all posts with the same author
                            acc=post.child("acc").getValue().toString();
                            sibuk=post.child("pid").getValue().toString();
                            pos=post.child("posisi").getValue().toString();
                            alamats=post.child("alamatdomisili").getValue().toString();
                            keyy=post.getKey();
                            LatLng loc2=getLocationFromAddress(getApplicationContext(), alamats);

                            //get rating
                            getRating(keyy);


                            if(post.child("sertifikasimarine").exists()){
                                flagmar=post.child("sertifikasimarine").getValue().toString();
                            }

                            if(pos.contains("6GR")){
                                flagg=3;
                            }
                            else if(pos.contains("6G")){
                                flagg=2;
                            }
                            else{
                                flagg=1;
                            }
                            int hasiljarak;
                            int hasilrating;
                            int hasilpeng;

                            if(pessan.equals("OAW")){
                                if(acc.equals("1")&&sibuk.equals("0")){
                                    //mendapatkan jarak
                                    double bedajrk=distance(loc1.latitude,loc1.longitude, loc2.latitude, loc2.longitude);

                                    //mendapatkan pembobotan jarak
                                    if((bedajrk/1000)<=25){
                                        hasiljarak=5;
                                    }
                                    else if((bedajrk/1000)<=275){
                                        hasiljarak=4;
                                    }
                                    else if((bedajrk/1000)<=900){
                                        hasiljarak=3;
                                    }
                                    else if((bedajrk/1000)<=3000){
                                        hasiljarak=2;
                                    }
                                    else{
                                        hasiljarak=1;
                                    }

                                    //mendapatkan pembobotan jumlah proyek
                                    if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=4){
                                        hasilpeng=1;
                                    }
                                    else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=8){
                                        hasilpeng=2;
                                    }
                                    else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=12){
                                        hasilpeng=3;
                                    }
                                    else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=16){
                                        hasilpeng=5;
                                    }
                                    else{
                                        hasilpeng=5;
                                    }

                                    //mendapatkan pembobotan rating
                                    if(Double.parseDouble(post.child("rating").getValue().toString())<=1){
                                        hasilrating=1;
                                    }
                                    else if(Double.parseDouble(post.child("rating").getValue().toString())<=2){
                                        hasilrating=2;
                                    }
                                    else if(Double.parseDouble(post.child("rating").getValue().toString())<=3){
                                        hasilrating=3;
                                    }
                                    else if(Double.parseDouble(post.child("rating").getValue().toString())<=4){
                                        hasilrating=5;
                                    }
                                    else{
                                        hasilrating=5;
                                    }

                                    //menghitung prioritas
                                    double prioritas=0.45*hasiljarak+hasilpeng*0.45+hasilrating*0.1;
                                    String jarak2=String.format("%.2f", bedajrk/1000);
                                    DecimalFormat df=new DecimalFormat("00000.00");
                                    String jaraks=df.format(10000-bedajrk/1000);
                                    String prio=String.format("%.2f", prioritas);
                                    String text="Rating= "+post.child("rating").getValue().toString()+"\nJumlah Proyek= "+post.child("jumlahproyek").getValue().toString()+"\nJarak= "+jarak2+"km";

                                    //prioritas disimpan
                                    hasil4.add("Prioritas= "+prio+"-"+jaraks+"-"+text+"-"+post.child("namalengkap").getValue().toString()+"-"+post.child("alamatdomisili").getValue().toString()+"-"+post.getKey());
                                }
                            }
                            else{
                                if(marine.equals("Konstruksi Maritim")){
                                    if(!flagmar.equals("0")){
                                        if(acc.equals("1")&&sibuk.equals("0")&&flagg>=flag){
                                            double bedajrk=distance(loc1.latitude,loc1.longitude, loc2.latitude, loc2.longitude);
                                            if((bedajrk/1000)<=25){
                                                hasiljarak=5;
                                            }
                                            else if((bedajrk/1000)<=275){
                                                hasiljarak=4;
                                            }
                                            else if((bedajrk/1000)<=900){
                                                hasiljarak=3;
                                            }
                                            else if((bedajrk/1000)<=3000){
                                                hasiljarak=2;
                                            }
                                            else{
                                                hasiljarak=1;
                                            }

                                            if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=4){
                                                hasilpeng=1;
                                            }
                                            else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=8){
                                                hasilpeng=2;
                                            }
                                            else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=12){
                                                hasilpeng=3;
                                            }
                                            else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=16){
                                                hasilpeng=5;
                                            }
                                            else{
                                                hasilpeng=5;
                                            }

                                            if(Double.parseDouble(post.child("rating").getValue().toString())<=1){
                                                hasilrating=1;
                                            }
                                            else if(Double.parseDouble(post.child("rating").getValue().toString())<=2){
                                                hasilrating=2;
                                            }
                                            else if(Double.parseDouble(post.child("rating").getValue().toString())<=3){
                                                hasilrating=3;
                                            }
                                            else if(Double.parseDouble(post.child("rating").getValue().toString())<=4){
                                                hasilrating=5;
                                            }
                                            else{
                                                hasilrating=5;
                                            }

                                            double prioritas=0.45*hasiljarak+hasilpeng*0.45+hasilrating*0.1;
                                            String jarak2=String.format("%.2f", bedajrk/1000);
                                            DecimalFormat df=new DecimalFormat("00000.00");
                                            String jaraks=df.format(10000-bedajrk/1000);
                                            String prio=String.format("%.2f", prioritas);
                                            String text="Rating= "+post.child("rating").getValue().toString()+"\nJumlah Proyek= "+post.child("jumlahproyek").getValue().toString()+"\nJarak= "+jarak2+"km";

                                            hasil4.add("Prioritas= "+prio+"-"+jaraks+"-"+text+"-"+post.child("namalengkap").getValue().toString()+"-"+post.child("alamatdomisili").getValue().toString()+"-"+post.getKey());
                                        }
                                    }
                                }
                                else{
                                    if(acc.equals("1")&&sibuk.equals("0")&&flagg>=flag){
                                        double bedajrk=distance(loc1.latitude,loc1.longitude, loc2.latitude, loc2.longitude);
                                        if((bedajrk/1000)<=25){
                                            hasiljarak=5;
                                        }
                                        else if((bedajrk/1000)<=275){
                                            hasiljarak=4;
                                        }
                                        else if((bedajrk/1000)<=900){
                                            hasiljarak=3;
                                        }
                                        else if((bedajrk/1000)<=3000){
                                            hasiljarak=2;
                                        }
                                        else{
                                            hasiljarak=1;
                                        }

                                        if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=4){
                                            hasilpeng=1;
                                        }
                                        else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=8){
                                            hasilpeng=2;
                                        }
                                        else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=12){
                                            hasilpeng=3;
                                        }
                                        else if(Integer.parseInt(post.child("jumlahproyek").getValue().toString())<=16){
                                            hasilpeng=5;
                                        }
                                        else{
                                            hasilpeng=5;
                                        }

                                        if(Double.parseDouble(post.child("rating").getValue().toString())<=1){
                                            hasilrating=1;
                                        }
                                        else if(Double.parseDouble(post.child("rating").getValue().toString())<=2){
                                            hasilrating=2;
                                        }
                                        else if(Double.parseDouble(post.child("rating").getValue().toString())<=3){
                                            hasilrating=3;
                                        }
                                        else if(Double.parseDouble(post.child("rating").getValue().toString())<=4){
                                            hasilrating=5;
                                        }
                                        else{
                                            hasilrating=5;
                                        }

                                        double prioritas=0.45*hasiljarak+hasilpeng*0.45+hasilrating*0.1;
                                        String jarak2=String.format("%.2f", bedajrk/1000);
                                        DecimalFormat df=new DecimalFormat("00000.00");
                                        String jaraks=df.format(10000-bedajrk/1000);
                                        String prio=String.format("%.2f", prioritas);
                                        String text="Rating= "+post.child("rating").getValue().toString()+"\nJumlah Proyek= "+post.child("jumlahproyek").getValue().toString()+"\nJarak= "+jarak2+"km";

                                        hasil4.add("Prioritas= "+prio+"-"+jaraks+"-"+text+"-"+post.child("namalengkap").getValue().toString()+"-"+post.child("alamatdomisili").getValue().toString()+"-"+post.getKey());
                                    }
                                }
                            }
                        }
                        if(hasil4.size()>0){
                            int i ;
                            //list diurutkan dari prioritas terbesar ke terkecil
                            Collections.sort(hasil4, Collections.<String>reverseOrder());

                            for(int k=0; k<hasil4.size(); k++){
                                String[] hsl=hasil4.get(k).split("-");
                                hasil.add(hsl[3]);
                                hasil2.add(hsl[4]);
                                hasil3.add(hsl[5]);
                                hasil5.add(hsl[2]);
                                hasil6.add(hsl[0]);
                            }

                            item3=pessan3;
                            item5=julah;

                            lisst=findViewById(R.id.lilis);

                            customAdapter=new CustomAdapter2(getApplicationContext(), hasil2, hasil, item3, hasil3, item5, hasil5, hasil6);
                            customAdapter.notifyDataSetChanged();
                            barbar.setVisibility(View.INVISIBLE);
                            if(lisst.getAdapter()==null){
                                lisst.setAdapter(customAdapter);
                            }
                            else{
                                customAdapter.notifyDataSetChanged();
                                lisst.setAdapter(customAdapter);
                            }


                        }
                        else{
                            LinearLayout leyut=findViewById(R.id.linearLayout2);
                            leyut.setVisibility(View.INVISIBLE);
                            barbar.setVisibility(View.INVISIBLE);
                            TextView kosong =findViewById(R.id.textView43);
                            kosong.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        TextView tex=findViewById(R.id.textView42);
//        tex.setText(pessan);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    public double distance (double lat_a, double lng_a, double lat_b, double lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Double(distance * meterConversion).doubleValue();
    }
    public void getRating(final String keyy){
        FirebaseDatabase.getInstance().getReference().child("Transaksi").orderByChild("wid").equalTo(keyy).addValueEventListener(new ValueEventListener() {
            ArrayList<String> key=new ArrayList<>();
            ArrayList<String> key1=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key.clear();
                key1.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    key.add(post.child("pid").getValue().toString());
                }
                if(key.size()>0){
                    for(int i=0; i<key.size(); i++){
                        final DatabaseReference res=FirebaseDatabase.getInstance().getReference().child("Proyek").child(key.get(i));
                        res.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                            String kes;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                kes=dataSnapshot.getValue().toString();
                                if(kes.equals("1")){
                                    res.child("sudahnilai").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            key1.add(dataSnapshot.getValue().toString());
                                            double loh=0;
                                            for(int i=0; i<key1.size(); i++){
                                                loh+=Integer.parseInt(key1.get(i));
                                            }
                                            if(key1.size()>0){
                                                loh=loh/(key1.size());
                                                FirebaseDatabase.getInstance().getReference().child("Welders").child(keyy).child("rating").setValue(String.format("%.1f", loh));
                                            }
                                            else{
                                                FirebaseDatabase.getInstance().getReference().child("Welders").child(keyy).child("rating").setValue("0.0");
                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Welders").child(keyy).child("rating").setValue("0.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
