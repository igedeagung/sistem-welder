package com.example.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int flag[]=new int[1000];
    private Marker mark[]=new Marker[1000];
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle=getIntent().getExtras();
        final String pessan=bundle.getString("email");
        FirebaseDatabase.getInstance().getReference().child("Proyek").child(pessan).child("alamat").addValueEventListener(new ValueEventListener() {
            String keyy;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyy=dataSnapshot.getValue().toString();
                LatLng loc=getLocationFromAddress(MapsActivity.this, keyy);
                mMap.addMarker(new MarkerOptions().position(loc)).setTitle("Lokasi Proyek");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Welders").orderByChild("pid").equalTo(pessan).addValueEventListener(new ValueEventListener() {
            List<String> hh=new ArrayList<>();
            List<String> hh2=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    hh.add(post.child("latlong").getValue().toString());
                    hh2.add(post.child("namalengkap").getValue().toString());
                }
                if(hh.size()>0){
                    for(i=0; i<hh.size(); i++){
//                        MarkerOptions s =new MarkerOptions().position(new LatLng(0,0));
//                        mark[i]=mMap.addMarker(s);
                        String lat;
                        String longi;
                        if(dataSnapshot.exists()){
                            String key[]=hh.get(i).split(",");
                            lat=key[0];
                            longi=key[1];
                        }
                        else{
                            lat="0";
                            longi="0";
                        }
                        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(longi));

                        mMap.addMarker(new MarkerOptions().position(sydney));
//                            mark[i].setPosition(sydney);
//
//                            LatLng sydney = new LatLng(0, 0);
//                            mMap.addMarker(new MarkerOptions().position(sydney)).setTitle(dataSnapshot.child("namalengkap").getValue().toString());
//                            //mark[i].setPosition(sydney);
//                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
}
