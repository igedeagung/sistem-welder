package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifActivity extends AppCompatActivity {
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
    private CircleImageView imageView2;
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
    private int juml2=100;
    private int juml3=200;
    private ArrayList<Uri> filesertif=new ArrayList<>();
    private ArrayList<Uri> filesertifmar=new ArrayList<>();
    private ArrayList<Uri> filesertiftof=new ArrayList<>();
    private ArrayList<String> namasertif=new ArrayList<>();
    private ArrayList<String> namasertifmar=new ArrayList<>();
    private ArrayList<String> namasertiftof=new ArrayList<>();

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
                chooseImage();
            }
        });
        sertifmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage3();
            }
        });
        sertifij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage4();
            }
        });
        sertiftof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage5();
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage2();
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void chooseImage2() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 75);
    }
    private void chooseImage3() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 76);
    }
    private void chooseImage4() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 77);
    }
    private void chooseImage5() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 78);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            filesertif.add(filePath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView imagi=new ImageView(VerifActivity.this);
                imagi.setImageBitmap(bitmap);
                imagi.setId(juml);

                ConstraintLayout mylayout= findViewById(R.id.parentt);
                mylayout.addView(imagi);

                ConstraintSet set=new ConstraintSet();
                set.clone(mylayout);
                set.connect(R.id.editText8, ConstraintSet.BOTTOM, R.id.buttonn, ConstraintSet.TOP, 30+(400*(juml+1)));

                set.constrainHeight(imagi.getId(), 400);
                set.constrainWidth(imagi.getId(), 300);

                set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(imagi.getId(), ConstraintSet.TOP, R.id.editText8, ConstraintSet.BOTTOM, 5+(400*juml));

                set.applyTo(mylayout);

                juml++;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if(requestCode == 76 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath3 = data.getData();
            filesertifmar.add(filePath3);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath3);
                ImageView imagi=new ImageView(VerifActivity.this);
                imagi.setImageBitmap(bitmap);
                imagi.setId(juml2);

                ConstraintLayout mylayout= findViewById(R.id.parentt);
                mylayout.addView(imagi);

                ConstraintSet set=new ConstraintSet();
                set.clone(mylayout);
                set.connect(R.id.buttonn, ConstraintSet.BOTTOM, R.id.buttonn2, ConstraintSet.TOP, 30+(400*(juml2-99)));

                set.constrainHeight(imagi.getId(), 400);
                set.constrainWidth(imagi.getId(), 300);

                set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(imagi.getId(), ConstraintSet.TOP, R.id.buttonn, ConstraintSet.BOTTOM, 5+(400*(juml2-100)));

                set.applyTo(mylayout);

                juml2++;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if(requestCode == 77 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filesertifj = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filesertifj);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if(requestCode == 78 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath4 = data.getData();
            filesertiftof.add(filePath4);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath4);
                ImageView imagi=new ImageView(VerifActivity.this);
                imagi.setImageBitmap(bitmap);
                imagi.setId(juml3);

                ConstraintLayout mylayout= findViewById(R.id.parentt);
                mylayout.addView(imagi);

                ConstraintSet set=new ConstraintSet();
                set.clone(mylayout);
                set.connect(R.id.buttonn3, ConstraintSet.BOTTOM, R.id.button, ConstraintSet.TOP, 30+(400*(juml3-199)));

                set.constrainHeight(imagi.getId(), 400);
                set.constrainWidth(imagi.getId(), 300);

                set.connect(imagi.getId(), ConstraintSet.LEFT, R.id.parentt, ConstraintSet.LEFT, 0);
                set.connect(imagi.getId(), ConstraintSet.RIGHT, R.id.parentt, ConstraintSet.RIGHT, 0);
                set.connect(imagi.getId(), ConstraintSet.TOP, R.id.buttonn3, ConstraintSet.BOTTOM, 5+(400*(juml3-200)));

                set.applyTo(mylayout);

                juml3++;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if(requestCode == 75 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath2 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
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

        if(filesertif.size()>0 && filePath2 !=null && filesertifj !=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            for(int i=0; i<filesertif.size(); i++){
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
            for(int i=0; i<filesertifmar.size(); i++){
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
            for(int i=0; i<filesertiftof.size(); i++){
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
            Intent pindahtunggu=new Intent(VerifActivity.this, MainActivity.class);
            pindahtunggu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(pindahtunggu);
        }
        else {
            if(filesertif.size()==0){
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
}
