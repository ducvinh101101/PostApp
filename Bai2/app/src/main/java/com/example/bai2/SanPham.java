package com.example.bai2;

public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private int giaSanPham;
    private boolean khuyenMai;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, int giaSanPham, boolean khuyenMai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.khuyenMai = khuyenMai;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public boolean getKhuyenMai() {
        return khuyenMai;
    }
    public void setKhuyenMai(boolean khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
}
