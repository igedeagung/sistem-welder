package com.example.welder;

import java.io.Serializable;

public class Transaksi implements Serializable {
    private String wid;
    private String pid;
    private String key;

    public Transaksi(){

    }

    public String getWid(){
        return wid;
    }

    public void setWid(String wid){
        this.wid=wid;
    }

    public String getPid(){
        return pid;
    }

    public void setPid(String pid){
        this.pid=pid;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key=key;
    }

    public String toString(){
        return " "+wid+""+
                ""+pid;
    }

    public Transaksi(String wi, String pi){
        wid=wi;
        pid=pi;
    }

}
