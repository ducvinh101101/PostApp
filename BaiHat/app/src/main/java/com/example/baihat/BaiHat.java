package com.example.baihat;

public class BaiHat {
    private int id;
    private String tenBai;
    private String caSy;
    private int soLike;
    private int soShare;

    public BaiHat(){

    }

    public BaiHat(int id, String tenBai, String caSy, int soLike, int soShare){
        this.id=id;
        this.tenBai=tenBai;
        this.caSy=caSy;
        this.soLike=soLike;
        this.soShare=soShare;
    }

    public int diem(){
        return soLike+soShare*5+51;
    }

    public String getTen(){
        String[] parts = caSy.split(" ");
        String lastName = parts[parts.length - 1];
        return lastName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBai() {
        return tenBai;
    }

    public void setTenBai(String tenBai) {
        this.tenBai = tenBai;
    }

    public String getCaSy() {
        return caSy;
    }

    public void setCaSy(String caSy) {
        this.caSy = caSy;
    }

    public int getSoLike() {
        return soLike;
    }

    public void setSoLike(int soLike) {
        this.soLike = soLike;
    }

    public int getSoShare() {
        return soShare;
    }

    public void setSoShare(int soShare) {
        this.soShare = soShare;
    }
}
