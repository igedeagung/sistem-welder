package com.example.welder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class VerifActivity extends AppCompatActivity {
    private EditText spes;
    private EditText pos;
    private EditText sertif;
    private EditText nmpanggil;
    private EditText nmlengkap;
    private EditText noktp;
    private EditText almtlkp;
    private EditText almtdms;
    private Button btnsubmit;
    private Button btnback;
    private String[] listItems;
    private String[] listItems2;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private Uri filePath;
    private ImageView imageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);


        spes = (EditText) findViewById(R.id.editText6);
        pos = (EditText) findViewById(R.id.editText7);
        sertif = (EditText) findViewById(R.id.editText8);
        nmpanggil = (EditText) findViewById(R.id.editText);
        nmlengkap = (EditText) findViewById(R.id.editText2);
        noktp = (EditText) findViewById(R.id.editText3);
        almtlkp = (EditText) findViewById(R.id.editText4);
        almtdms=(EditText) findViewById(R.id.editText5);
        imageView = (ImageView) findViewById(R.id.imgView);
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
                if (TextUtils.isEmpty(sertifikasi)){
                    sertif.setError("Sertifikasi harus diisi");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            String cb= filePath.toString();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                sertif.setText(cb);
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

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final String name=System.currentTimeMillis() + "." + GetFileExtension(filePath);
            StorageReference ref = storageReference.child("sertifikasi_welder/" +name );
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();
                            String user_id=auth.getCurrentUser().getUid();
                            DatabaseReference id_db = database.child(user_id);
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
                            id_db.child("sertifikasi").setValue(name);
                            id_db.child("status").setValue(1);
                            id_db.child("acc").setValue(0);
                            Intent pindahtunggu=new Intent(VerifActivity.this, MainActivity.class);
                            pindahtunggu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(pindahtunggu);
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
        else {
            nmlengkap.setError("huhu");
        }
    }
}
