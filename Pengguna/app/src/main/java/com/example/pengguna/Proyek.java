package com.example.pengguna;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

public class Proyek implements Serializable{
    private String jenisproyek;
    private String namaproyek;
    private String tipe;
    private String tanggalmulai;
    private String tanggalselesai;
    private String harga;
    private String proyek1;
    private String proyek2;
    private String proyek3;
    private String proyek4;
    private String proyek5;
    private String jumlah1;
    private String jumlah2;
    private String jumlah3;
    private String jumlah4;
    private String jumlah5;
    private String jumlah1f;
    private String jumlah2f;
    private String jumlah3f;
    private String jumlah4f;
    private String jumlah5f;
    private String key;
    private String uid;
    private String wid;

    public Proyek()
    {
    }
    public String getJenisproyek() {
        return jenisproyek;
    }
    public void setJenisproyek(String jenisproyek) {
        this.jenisproyek = jenisproyek;
    }

    public String getNamaproyek() {
        return namaproyek;
    }
    public void setNamaproyek(String namaproyek) {
        this.namaproyek = namaproyek;
    }

    public String getTipe() {
        return tipe;
    }
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getTanggalmulai() {
        return tanggalmulai;
    }
    public void setTanggalmulai(String tanggalmulai) {
        this.tanggalmulai = tanggalmulai;
    }

    public String getTanggalselesai() {
        return tanggalselesai;
    }

    public void setTanggalselesai(String tanggalselesai) {
        this.tanggalselesai = tanggalselesai;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getProyek1() {
        return proyek1;
    }
    public void setProyek1(String proyek1) {
        this.proyek1 = proyek1;
    }

    public String getProyek2() {
        return proyek2;
    }
    public void setProyek2(String proyek2) {
        this.proyek2 = proyek2;
    }
    public String getProyek3() {
        return proyek3;
    }
    public void setProyek3(String proyek3) {
        this.proyek3 = proyek3;
    }
    public String getProyek4() {
        return proyek4;
    }
    public void setProyek4(String proyek4) {
        this.proyek4 = proyek4;
    }
    public String getProyek5() {
        return proyek5;
    }
    public void setProyek5(String proyek5) {
        this.proyek5 = proyek5;
    }

    public String getJumlah1() {
        return jumlah1;
    }
    public void setJumlah1(String jumlah1) {
        this.jumlah1 = jumlah1;
    }
    public String getJumlah2() {
        return jumlah2;
    }
    public void setJumlah2(String jumlah2) {
        this.jumlah2 = jumlah2;
    }
    public String getJumlah3() {
        return jumlah3;
    }
    public void setJumlah3(String jumlah3) {
        this.jumlah3 = jumlah3;
    }
    public String getJumlah4() {
        return jumlah4;
    }
    public void setJumlah4(String jumlah4) {
        this.jumlah4 = jumlah4;
    }
    public String getJumlah5() {
        return jumlah5;
    }
    public void setJumlah5(String jumlah5) {
        this.jumlah5 = jumlah5;
    }

    public String getJumlah1f() {
        return jumlah1f;
    }
    public void setJumlah1f(String jumlah1f) {
        this.jumlah1f = jumlah1f;
    }
    public String getJumlah2f() {
        return jumlah2f;
    }
    public void setJumlah2f(String jumlah2f) {
        this.jumlah2f = jumlah2f;
    }
    public String getJumlah3f() {
        return jumlah3f;
    }
    public void setJumlah3f(String jumlah3f) {
        this.jumlah3f = jumlah3f;
    }
    public String getJumlah4f() {
        return jumlah4f;
    }
    public void setJumlah4f(String jumlah4f) {
        this.jumlah4f = jumlah4f;
    }
    public String getJumlah5f() {
        return jumlah5f;
    }
    public void setJumlah5f(String jumlah5f) {
        this.jumlah5f = jumlah5f;
    }

    public String getUID() {
        return uid;
    }
    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getWID() {
        return wid;
    }
    public void setWID(String wid) {
        this.wid = wid;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String toString()
    {
        return " "+jenisproyek+"" +
                "" +namaproyek+"" +
                "" +tipe+"" +
                "" +proyek1+"" +
                "" +proyek2+"" +
                "" +proyek3+"" +
                "" +proyek4+"" +
                "" +proyek5+"" +
                "" +jumlah1+"" +
                "" +jumlah2+"" +
                "" +jumlah3+"" +
                "" +jumlah4+"" +
                "" +jumlah5+"" +
                "" +jumlah1f+"" +
                "" +jumlah2f+"" +
                "" +jumlah3f+"" +
                "" +jumlah4f+"" +
                "" +jumlah5f+"" +
                "" +tanggalmulai+"" +
                "" +tanggalselesai+"" +
                "" +harga+"" +
                "" +uid+"" +
                "" +wid;
    }
    public Proyek(String jnsprk, String nmprk, String tp,String p1, String p2, String p3, String p4,String p5,String j1f,
                  String j2f, String j3f, String j4f, String j5f,String j1,
                  String j2, String j3, String j4, String j5, String tm, String ts, String hg,
                  String ui, String wi)
    {
        jenisproyek=jnsprk;
        namaproyek=nmprk;
        tipe=tp;
        proyek1=p1;
        proyek2=p2;
        proyek3=p3;
        proyek4=p4;
        proyek5=p5;
        jumlah1=j1;
        jumlah2=j2;
        jumlah3=j3;
        jumlah4=j4;
        jumlah5=j5;
        jumlah1f=j1f;
        jumlah2f=j2f;
        jumlah3f=j3f;
        jumlah4f=j4f;
        jumlah5f=j5f;
        tanggalmulai=tm;
        tanggalselesai=ts;
        harga=hg;
        uid=ui;
        wid=wi;
    }
}