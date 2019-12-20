package com.example.pengguna;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

public class Proyek implements Serializable{
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
    private String key;
    private String uid;
    private String wid;

    public Proyek()
    {
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
        return " "+tipe+"" +
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
                "" +tanggalmulai+"" +
                "" +tanggalselesai+"" +
                "" +harga+"" +
                "" +uid+"" +
                "" +wid;
    }
    public Proyek(String tp,String p1, String p2, String p3, String p4,String p5,String j1,
                  String j2, String j3, String j4, String j5, String tm, String ts, String hg,
                  String ui, String wi)
    {
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
        tanggalmulai=tm;
        tanggalselesai=ts;
        harga=hg;
        uid=ui;
        wid=wi;
    }
}