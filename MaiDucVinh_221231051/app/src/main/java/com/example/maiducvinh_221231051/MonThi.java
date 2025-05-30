package com.example.maiducvinh_221231051;

public class MonThi {
    private String maMon;
    private String tenMon;
    private int soTin;
    private float diemQuaTrinh;
    private float diemThi;
    private int loai=51%3;

    public MonThi(){}
    public MonThi(String maMon,String tenMon, int soTin, float diemQuaTrinh, float diemThi, int loai){
        this.maMon=maMon;
        this.tenMon=tenMon;
        this.soTin=soTin;
        this.diemQuaTrinh=diemQuaTrinh;
        this.diemThi=diemThi;
        this.loai=loai;
    }

    public float tinhDiemTK(){
        return (float) (diemQuaTrinh*0.3+diemThi*0.7);
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoTin() {
        return soTin;
    }

    public void setSoTin(int soTin) {
        this.soTin = soTin;
    }

    public float getDiemQuaTrinh() {
        return diemQuaTrinh;
    }

    public void setDiemQuaTrinh(float diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
    }

    public float getDiemThi() {
        return diemThi;
    }

    public void setDiemThi(float diemThi) {
        this.diemThi = diemThi;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
