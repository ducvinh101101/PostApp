package com.example.bai1;

public class HoaDon {
    private String maHoaDon;
    private String tenKhachHang;
    private String ngayLap;
    private int donGia;
    private int soNgayLuuTru;

    public HoaDon() {

    }

    public HoaDon(String maHoaDon, String tenKhachHang, String ngayLap, int donGia, int soNgayLuuTru) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.donGia = donGia;
        this.soNgayLuuTru = soNgayLuuTru;
    }

    public int getTongTien() {
        return donGia * soNgayLuuTru*(100 - 5*(51%4+1));
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoNgayLuuTru() {
        return soNgayLuuTru;
    }

    public void setSoNgayLuuTru(int soNgayLuuTru) {
        this.soNgayLuuTru = soNgayLuuTru;
    }
}
