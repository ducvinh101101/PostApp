package com.example.hanghoa;

public class HangHoa {
    private int maHang;
    private String tenHang;
    private int giaNiemYet;
    private boolean giamGia;

    public HangHoa(int maHang, String tenHang, int giaNiemYet, boolean giamGia) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.giaNiemYet = giaNiemYet;
        this.giamGia = giamGia;
    }

    public int getGiaBan() {
        return giamGia ? (int) (giaNiemYet * 0.9) : giaNiemYet;
    }
    public int getMaHang() {
        return maHang;
    }
    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }
    public String getTenHang() {
        return tenHang;
    }
    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }
    public int getGiaNiemYet() {
        return giaNiemYet;
    }
    public void setGiaNiemYet(int giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }
    public boolean isGiamGia() {
        return giamGia;
    }
    public void setGiamGia(boolean giamGia) {
        this.giamGia = giamGia;
    }


}
