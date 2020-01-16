package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private EditText spes;
    private EditText pos;
    private Button sertif;
    private Button sertifmar;
    private Button sertifij;
    private Button sertiftof;
    private EditText nmpanggil;
    private EditText nmlengkap;
    private EditText noktp;
    private EditText almtlkp;
    private EditText almtdms;
    private Button profil;
    private Button btnsubmit;
    private Button btnback;
    private String[] listItems;
    private String[] listItems2;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private Uri filePath;
    private Uri filesertifj;
    private Uri filePath2;
    private Uri filePath3;
    private Uri filePath4;

    private ImageView imageView;
    private ImageView imageView2;
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private String nmpgl;
    private String nmlkp;
    private String nomktp;
    private String alamlkp;
    private String alamdms;
    private String spesifik;
    private String posisi;
    private String sertifikasi;
    private String spek1="0";
    private String spek2="0";
    private String spek3="0";
    private String spek4="0";
    private String spek5="0";
    private String spek6="0";
    private ProgressDialog dialog;
    private int juml=0;
    private int jumlas=0;
    private ArrayList<Integer> cek=new ArrayList<>();
    private int juml2=100;
    private int jumlas2=0;
    private ArrayList<Integer> cek2=new ArrayList<>();
    private int juml3=200;
    private int jumlas3=0;
    private ArrayList<Integer> cek3=new ArrayList<>();
    private ArrayList<Uri> filesertif=new ArrayList<>();
    private ArrayList<Uri> filesertifmar=new ArrayList<>();
    private ArrayList<Uri> filesertiftof=new ArrayList<>();
    private ArrayList<String> namasertif=new ArrayList<>();
    private ArrayList<String> namasertifmar=new ArrayList<>();
    private ArrayList<String> namasertiftof=new ArrayList<>();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private int pick=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);


        spes = (EditText) findViewById(R.id.editText6);
        pos = (EditText) findViewById(R.id.editText7);
        sertif = (Button) findViewById(R.id.editText8);
        sertifmar = (Button) findViewById(R.id.buttonn);
        sertifij = (Button) findViewById(R.id.buttonn2);
        sertiftof = (Button) findViewById(R.id.buttonn3);
        nmpanggil = (EditText) findViewById(R.id.editText);
        nmlengkap = (EditText) findViewById(R.id.editText2);
        noktp = (EditText) findViewById(R.id.editText3);
        almtlkp = (EditText) findViewById(R.id.editText4);
        almtdms=(EditText) findViewById(R.id.editText5);
        imageView = (ImageView) findViewById(R.id.imgView);
        imageView2 = findViewById(R.id.profile);
        profil=findViewById(R.id.button5);
        btnsubmit=findViewById(R.id.button);
        btnback=findViewById(R.id.button2);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Welders");


        listItems = getResources().getStringArray(R.array.proyek);
        listItems2 = getResources().getStringArray(R.array.tingkatan);
        checkedItems = new boolean[listItems.length];

        spes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(VerifActivity.this);
                mBuilder.setTitle("Spesifikasi Proses Welding");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            if (listItems[mUserItems.get(i)].equals("SMAW")){
                                spek1="SMAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("FCAW")){
                                spek2="FCAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("GTAW")){
                                spek3="GTAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("SMAW/FCAW")){
                                spek4="SMAW/FCAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("SMAW/GTAW")){
                                spek5="SMAW/GTAW";
                            }
                            if (listItems[mUserItems.get(i)].equals("OAW")){
                                spek6="OAW";
                            }
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        spes.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            spes.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(VerifActivity.this);
                mBuilder.setTitle("Posisi Pengelasan");
                mBuilder.setSingleChoiceItems(listItems2, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pos.setText(listItems2[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        sertif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick=1;
                alertDialog();
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick=2;
                alertDialog();
            }
        });
        sertifmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick=3;
                alertDialog();
            }
        });
        sertifij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick=4;
                alertDialog();
            }
        });
        sertiftof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick=5;
                alertDialog();
            }
        });



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmpgl=nmpanggil.getText().toString();
                nmlkp=nmlengkap.getText().toString();
                nomktp=noktp.getText().toString();
                alamlkp=almtlkp.getText().toString();
                alamdms=almtdms.getText().toString();
                spesifik=spes.getText().toString();
                posisi=pos.getText().toString();
                sertifikasi=sertif.getText().toString();

                if (TextUtils.isEmpty(nmpgl)){
                    nmpanggil.setError("Nama Panggilan harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(nmlkp)){
                    nmlengkap.setError("Nama Lengkap harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(nomktp)){
                    noktp.setError("Nomor KTP harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(alamlkp)){
                    almtlkp.setError("Alamat Lengkap harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(alamdms)){
                    almtdms.setError("Alamat Domisili harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(spesifik)){
                    spes.setError("Spesifikasi harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(posisi)){
                    pos.setError("Posisi harus diisi");
                    return;
                }
                uploadImage();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signOut();
                Intent loginIntent= new Intent(VerifActivity.this, IntroActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int imgreq=0;
        switch (pick){
            case 1:
                imgreq=71;
                break;
            case 2:
                imgreq=75;
                break;
            case 3:
                imgreq=76;
                break;
            case 4:
                imgreq=77;
                break;
            case 5:
                imgreq=78;
                break;
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), imgreq);
    }
    private void chooseImagee() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            int imgreq=0;
            switch (pick){
                case 1:
                    imgreq=1888;
                    break;
                case 2:
                    imgreq=1889;
                    break;
                case 3:
                    imgreq=1890;
                    break;
                case 4:
                    imgreq=1891;
                    break;
                case 5:
                    imgreq=1892;
                    break;
            }
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, imgreq);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == 71&& data != null && data.getData() != null ) || requestCode==1888 && resultCode == RESULT_OK)
        {
            try {

                if(requestCode==1888){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        filePath = getImageUri(VerifActivity.this, bitmap);
                        filesertif.add(filePath);
                    }
                }
                else{
                    filePath = data.getData();
                    filesertif.add(filePath);
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                }

                final ImageView imagi=new ImageView(VerifActivity.this);
                final Button del=new Button(VerifActivity.this);
                del.setId(juml+1000);
                del.setText("Hapus");
                del.setTextSize(14);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagi.setVisibility(View.GONE);
                        del.setVisibility(View.GONE);

                        int juju=0;
                        cek.set(imagi.getId(), 1);
                        filesertif.set(imagi.getId(), null);
                        jumlas--;
                        for(int i=0; i<juml; i++){
                            if(cek.get(i)!=1){
                                ConstraintLayout mylayout= findViewById(R.id.parentt);
                                ConstraintSet set=new ConstraintSet();
                                set.clone(mylayout);
                                set.connect(R.id.editText8, ConstraintSet.BOTTOM, R.id.buttonn, ConstraintSet.TOP, 30+(480*(juju+1)));

                                set.connect(i, ConstraintSet.TOP, R.id.editText8, ConstraintSet.BOTTOM, 5+(480*juju));

                                int ha=i+1000;
                                set.connect(ha, ConstraintSet.TOP, R.id.editText8, ConstraintSet.BOTTOM, 400+(480*juju));

                                set.applyTo(mylayout);
                                juju++;
                            }
                        }
                    }
                });

                imagi.setImageBitmap(bitmap);
                imagi.setId(juml);

                ConstraintLayout mylayout= findViewById(R.id.parentt);
                mylayout.addView(imagi);
                mylayout.addView(del);

                ConstraintSet set=new ConstraintSet();
                set.clone(mylayout);
                set.connect(R.id.editText8, ConstraintSet.BOTTOM, R.id.buttonn, ConstraintSet.TOP, 30+(480*(jumlas+1)));

                set.constrainHeight(imagi.getId(), 400);
                set.constrainWidth(imagi.getId(), 300);

                set.constrainHeight(del.getId(), ConstraintSet.WRAP_CONTENT);
                set.constrainWidth(del.getId(), 200);

                set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                if(jumlas==0){
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.editText8, ConstraintSet.BOTTOM, 5+(480*jumlas));
                }
                else{
                    set.connect(imagi.getId(), ConstraintSet.TOP, R.id.editText8, ConstraintSet.BOTTOM, 5+(480*jumlas));
                }


                set.connect(del.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(del.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(del.getId(), ConstraintSet.TOP, R.id.editText8, ConstraintSet.BOTTOM, 400+(480*jumlas));

                set.applyTo(mylayout);

                juml++;
                jumlas++;
                cek.add(0);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if((requestCode == 76 && data != null && data.getData() != null )||requestCode==1890&& resultCode == RESULT_OK
                 )
        {

            try {
                if(requestCode==1890){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        filePath3 = getImageUri(VerifActivity.this, bitmap);
                        filesertifmar.add(filePath3);
                    }
                }
                else{
                    filePath3 = data.getData();
                    filesertifmar.add(filePath3);
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath3);
                }

                final ImageView imagi=new ImageView(VerifActivity.this);
                final Button del=new Button(VerifActivity.this);
                del.setId(juml2+1000);
                del.setText("Hapus");
                del.setTextSize(14);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagi.setVisibility(View.GONE);
                        del.setVisibility(View.GONE);

                        int juju=0;
                        int res=imagi.getId();
                        res-=100;
                        cek2.set(res, 1);
                        filesertifmar.set(res, null);
                        jumlas2--;
                        for(int i=0; i<juml2-100; i++){
                            if(cek2.get(i)!=1){
                                ConstraintLayout mylayout= findViewById(R.id.parentt);
                                ConstraintSet set=new ConstraintSet();
                                set.clone(mylayout);
                                set.connect(R.id.buttonn, ConstraintSet.BOTTOM, R.id.buttonn2, ConstraintSet.TOP, 30+(480*(juju+1)));

                                int ya=i+100;
                                set.connect(ya, ConstraintSet.TOP, R.id.buttonn, ConstraintSet.BOTTOM, 5+(480*juju));

                                int ha=i+1100;
                                set.connect(ha, ConstraintSet.TOP, R.id.buttonn, ConstraintSet.BOTTOM, 400+(480*juju));

                                set.applyTo(mylayout);
                                juju++;
                            }
                        }
                    }
                });
                imagi.setImageBitmap(bitmap);
                imagi.setId(juml2);

                ConstraintLayout mylayout= findViewById(R.id.parentt);
                mylayout.addView(imagi);
                mylayout.addView(del);

                ConstraintSet set=new ConstraintSet();
                set.clone(mylayout);
                set.connect(R.id.buttonn, ConstraintSet.BOTTOM, R.id.buttonn2, ConstraintSet.TOP, 30+(480*(jumlas2+1)));

                set.constrainHeight(imagi.getId(), 400);
                set.constrainWidth(imagi.getId(), 300);

                set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(imagi.getId(), ConstraintSet.TOP, R.id.buttonn, ConstraintSet.BOTTOM, 5+(480*(jumlas2)));

                set.connect(del.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(del.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(del.getId(), ConstraintSet.TOP, R.id.buttonn, ConstraintSet.BOTTOM, 400+(480*jumlas2));

                set.applyTo(mylayout);

                juml2++;
                jumlas2++;
                cek2.add(0);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if((requestCode == 77&& data != null && data.getData() != null )||requestCode==1891 && resultCode == RESULT_OK)
        {

            try {
                if(requestCode==1891){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        filesertifj = getImageUri(VerifActivity.this, bitmap);
                    }
                }
                else{
                    filesertifj = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filesertifj);
                }
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if((requestCode == 78 && data != null && data.getData() != null) || requestCode==1892 && resultCode == RESULT_OK )
        {

            try {
                if(requestCode==1892){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        filePath4 = getImageUri(VerifActivity.this, bitmap);
                        filesertiftof.add(filePath4);
                    }
                }
                else{
                    filePath4 = data.getData();
                    filesertiftof.add(filePath4);
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath4);
                }

                final ImageView imagi=new ImageView(VerifActivity.this);
                final Button del=new Button(VerifActivity.this);
                del.setId(juml3+1000);
                del.setText("Hapus");
                del.setTextSize(14);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagi.setVisibility(View.GONE);
                        del.setVisibility(View.GONE);

                        int juju=0;
                        int res=imagi.getId();
                        res-=200;
                        cek3.set(res, 1);
                        filesertiftof.set(res, null);
                        jumlas3--;
                        for(int i=0; i<juml3-200; i++){
                            if(cek3.get(i)!=1){
                                ConstraintLayout mylayout= findViewById(R.id.parentt);
                                ConstraintSet set=new ConstraintSet();
                                set.clone(mylayout);
                                set.connect(R.id.buttonn3, ConstraintSet.BOTTOM, R.id.button, ConstraintSet.TOP, 30+(480*(juju+1)));

                                int ya=i+200;
                                set.connect(ya, ConstraintSet.TOP, R.id.buttonn3, ConstraintSet.BOTTOM, 5+(480*juju));

                                int ha=i+1200;
                                set.connect(ha, ConstraintSet.TOP, R.id.buttonn3, ConstraintSet.BOTTOM, 400+(480*juju));

                                set.applyTo(mylayout);
                                juju++;
                            }
                        }
                    }
                });
                imagi.setImageBitmap(bitmap);
                imagi.setId(juml3);

                ConstraintLayout mylayout= findViewById(R.id.parentt);
                mylayout.addView(imagi);
                mylayout.addView(del);

                ConstraintSet set=new ConstraintSet();
                set.clone(mylayout);
                set.connect(R.id.buttonn3, ConstraintSet.BOTTOM, R.id.button, ConstraintSet.TOP, 30+(480*(jumlas3+1)));

                set.constrainHeight(imagi.getId(), 400);
                set.constrainWidth(imagi.getId(), 300);

                set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(imagi.getId(), ConstraintSet.TOP, R.id.buttonn3, ConstraintSet.BOTTOM, 5+(480*(jumlas3)));

                set.connect(del.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(del.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(del.getId(), ConstraintSet.TOP, R.id.buttonn3, ConstraintSet.BOTTOM, 400+(480*jumlas3));

                set.applyTo(mylayout);

                juml3++;
                jumlas3++;
                cek3.add(0);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if((requestCode == 75&& data != null && data.getData() != null)||requestCode==1889  && resultCode == RESULT_OK)
        {

            try {
                if(requestCode==1889){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        filePath2 = getImageUri(VerifActivity.this, bitmap);
                    }
                }
                else{
                    filePath2 = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                }

                imageView2.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    private void uploadImage() {

        if(jumlas>0 && filePath2 !=null && filesertifj !=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            for(int i=0; i<filesertif.size(); i++){
                if(filesertif.get(i)!=null){
                    final String name=System.currentTimeMillis() +Integer.toString(i)+ "." + GetFileExtension(filesertif.get(i));
                    namasertif.add(name);
                    StorageReference ref = storageReference.child("sertifikasi_welder/" +name );
                    ref.putFile(filesertif.get(i))
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Hiding the progressDialog after done uploading.
                                    progressDialog.dismiss();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Hiding the progressDialog.
                                    progressDialog.dismiss();
                                    Toast.makeText(VerifActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    // Setting progressDialog Title.
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }
            }
            for(int i=0; i<filesertifmar.size(); i++){
                if(filesertifmar.get(i)!=null){
                    final String name=System.currentTimeMillis() +Integer.toString(i)+ "." + GetFileExtension(filesertifmar.get(i));
                    namasertifmar.add(name);
                    StorageReference ref = storageReference.child("sertifikasi_welder_marine/" +name );
                    ref.putFile(filesertifmar.get(i))
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Hiding the progressDialog after done uploading.
                                    progressDialog.dismiss();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Hiding the progressDialog.
                                    progressDialog.dismiss();
                                    Toast.makeText(VerifActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    // Setting progressDialog Title.
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }
            }
            for(int i=0; i<filesertiftof.size(); i++){
                if(filesertiftof.get(i)!=null){
                    final String name=System.currentTimeMillis() +Integer.toString(i)+ "." + GetFileExtension(filesertiftof.get(i));
                    namasertiftof.add(name);
                    StorageReference ref = storageReference.child("sertifikasi_welder_toefl/" +name );
                    ref.putFile(filesertiftof.get(i))
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Hiding the progressDialog after done uploading.
                                    progressDialog.dismiss();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Hiding the progressDialog.
                                    progressDialog.dismiss();
                                    Toast.makeText(VerifActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    // Setting progressDialog Title.
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }
            }

            final String name2=System.currentTimeMillis() + "." + GetFileExtension(filePath2);
            StorageReference ref2 = storageReference.child("profil_welder/" +name2 );
            ref2.putFile(filePath2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Hiding the progressDialog.
                            progressDialog.dismiss();
                            Toast.makeText(VerifActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });

            final String name3=System.currentTimeMillis() + "." + GetFileExtension(filesertifj);
            StorageReference ref3 = storageReference.child("ijasah_welder/" +name3 );
            ref3.putFile(filesertifj)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Hiding the progressDialog.
                            progressDialog.dismiss();
                            Toast.makeText(VerifActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
            String user_id=auth.getCurrentUser().getUid();
            DatabaseReference id_db = database.child(user_id);
            String nama1="0";
            String nama2="0";
            String nama3="0";

            for (int i=0; i<namasertif.size();i++ ){
                if(i==0){
                    nama1=namasertif.get(i);
                }
                else{
                    nama1=nama1+", "+namasertif.get(i);
                }
            }
            for (int i=0; i<namasertifmar.size();i++ ){
                if(i==0){
                    nama2=namasertifmar.get(i);
                }
                else{
                    nama2=nama2+", "+namasertifmar.get(i);
                }
            }
            for (int i=0; i<namasertiftof.size();i++ ){
                if(i==0){
                    nama3=namasertiftof.get(i);
                }
                else{
                    nama3=nama3+", "+namasertiftof.get(i);
                }
            }

            id_db.child("namalengkap").setValue(nmlkp);
            id_db.child("namapanggilan").setValue(nmpgl);
            id_db.child("noktp").setValue(nomktp);
            id_db.child("alamatlengkap").setValue(alamlkp);
            id_db.child("alamatdomisili").setValue(alamdms);
            id_db.child("spesifikasi1").setValue(spek1);
            id_db.child("spesifikasi2").setValue(spek2);
            id_db.child("spesifikasi3").setValue(spek3);
            id_db.child("spesifikasi4").setValue(spek4);
            id_db.child("spesifikasi5").setValue(spek5);
            id_db.child("spesifikasi6").setValue(spek6);
            id_db.child("posisi").setValue(posisi);
            id_db.child("sertifikasi").setValue(nama1);
            id_db.child("sertifikasimarine").setValue(nama2);
            id_db.child("sertifikasitoefl").setValue(nama3);
            id_db.child("sertifikasiijasah").setValue(name3);
            id_db.child("profil").setValue(name2);
            id_db.child("status").setValue(1);
            id_db.child("acc").setValue(0);
            id_db.child("pid").setValue("0");
            id_db.child("tanggalmasuk").setValue("Belum di Accept Admin");
            id_db.child("id").setValue("Belum di Accept Admin");
            id_db.child("terima").setValue("0");
            id_db.child("rating").setValue("0");
            id_db.child("tpos").setValue("0");
            id_db.child("jumlahproyek").setValue("0");
            Intent pindahtunggu=new Intent(VerifActivity.this, MainActivity.class);
            pindahtunggu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(pindahtunggu);
        }
        else {
            if(jumlas==0){
                sertif.setError("Sertifikasi harus diisi");
            }
            if(filePath2==null){
                profil.setError("Profil harus diisi");
            }
            if(filesertifj==null){
                sertifij.setError("Ijazah harus diisi");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1888);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                filePath=getImageUri(VerifActivity.this, bitmap);
            } else {
                // Permission Denied
                Toast.makeText(VerifActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    public Uri getImageUri(Context incontext, Bitmap inimage){
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        inimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path=MediaStore.Images.Media.insertImage(incontext.getContentResolver(), inimage, "Title", null);
        return Uri.parse(path);
    }

    public void alertDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(VerifActivity.this);
        builder.setTitle("Pilih metode");

        final String[] met={"Kamera", "Galeri"};
        builder.setItems(met, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(VerifActivity.this, met[which], Toast.LENGTH_SHORT).show();
                switch (which){
                    case 0:
                        chooseImagee();
                        break;
                    case 1:
                        chooseImage();
                        break;
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
