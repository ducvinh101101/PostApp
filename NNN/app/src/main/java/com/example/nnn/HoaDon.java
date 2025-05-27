package com.example.nnn;


public class HoaDon {
    private String maHoaDon;
    private String hoTen;
    private String ngayThang;
    private int donGia;
    private int soNgay;

    public HoaDon(){};
    public HoaDon(String maHoaDon, String hoTen, String ngayThang, int donGia, int soNgay){
        this.maHoaDon=maHoaDon;
        this.hoTen=hoTen;
        this.donGia=donGia;
        this.soNgay=soNgay;
        this.ngayThang=ngayThang;
    }
    public int tienHD(){
        return donGia*soNgay*(100-5*(51%4+1));
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(int soNgay) {
        this.soNgay = soNgay;
    }
}
