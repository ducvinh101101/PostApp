package com.example.khachhang;

import java.time.LocalDate;
import java.util.Date;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private LocalDate ngayDanhGia;
    private float binhChon;

    public KhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, LocalDate ngayDanhGia, float binhChon) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.ngayDanhGia = ngayDanhGia;
        this.binhChon = binhChon;
    }

    public float getDiemDanhGia(){
        float diemDanhGia = binhChon + (5-binhChon)*(51+1)/100;
        return Float.parseFloat(String.format("%.1f", diemDanhGia));
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public LocalDate getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(LocalDate ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }

    public float getBinhChon() {
        return binhChon;
    }

    public void setBinhChon(float binhChon) {
        this.binhChon = binhChon;
    }
}
