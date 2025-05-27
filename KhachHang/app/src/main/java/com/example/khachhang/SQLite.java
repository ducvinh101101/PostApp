package com.example.khachhang;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "khachhang.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "khachhang";
    public static final String COLUMN_MA_KHACH_HANG = "ma_khach_hang";
    public static final String COLUMN_TEN_KHACH_HANG = "ten_khach_hang";
    public static final String COLUMN_SO_DIEN_THOAI = "so_dien_thoai";
    public static final String COLUMN_NGAY_DANH_GIA = "ngay_danh_gia";
    public static final String COLUMN_BINH_CHON = "binh_chon";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public SQLite(android.content.Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_MA_KHACH_HANG + " TEXT PRIMARY KEY, " +
                COLUMN_TEN_KHACH_HANG + " TEXT, " +
                COLUMN_SO_DIEN_THOAI + " TEXT, " +
                COLUMN_NGAY_DANH_GIA + " TEXT, " +
                COLUMN_BINH_CHON + " FLOAT DEFAULT 0);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addKhachHang(String tenKhachHang, String soDienThoai, String ngayDanhGia, float binhChon) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_MA_KHACH_HANG, getNextId());
        values.put(COLUMN_TEN_KHACH_HANG, tenKhachHang);
        values.put(COLUMN_SO_DIEN_THOAI, soDienThoai);
        values.put(COLUMN_NGAY_DANH_GIA, ngayDanhGia);
        values.put(COLUMN_BINH_CHON, binhChon);
        db.insert(TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public String getNextId() {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_MA_KHACH_HANG + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_MA_KHACH_HANG + " DESC LIMIT 1";
        android.database.Cursor cursor = db.rawQuery(selectQuery, null);
        String lastId = "KH41";
        if (cursor.moveToFirst()) {
            lastId = cursor.getString(cursor.getColumnIndex(COLUMN_MA_KHACH_HANG));
        }
        cursor.close();
        int num = Integer.parseInt(lastId.replace("KH", "")) + 10;
        return "KH" + num;
    }

    public void deleteKhachHang(String maKhachHang) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_MA_KHACH_HANG + "=?", new String[]{maKhachHang});
    }

    public void updateKhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, String ngayDanhGia, float binhChon) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_TEN_KHACH_HANG, tenKhachHang);
        values.put(COLUMN_SO_DIEN_THOAI, soDienThoai);
        values.put(COLUMN_NGAY_DANH_GIA, ngayDanhGia);
        values.put(COLUMN_BINH_CHON, binhChon);
        db.update(TABLE_NAME, values, COLUMN_MA_KHACH_HANG + "=?", new String[]{maKhachHang});
    }

    @SuppressLint("Range")
    public String getMaKhachHang(String tenKhachHang) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_MA_KHACH_HANG + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TEN_KHACH_HANG + "=?";
        android.database.Cursor cursor = db.rawQuery(selectQuery, new String[]{tenKhachHang});
        String maKhachHang = null;
        if (cursor.moveToFirst()) {
            maKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_MA_KHACH_HANG));
        }
        cursor.close();
        return maKhachHang;
    }
    @SuppressLint("Range")
    public java.util.List<KhachHang> getAllKhachHang() {
        java.util.List<KhachHang> khachHangList = new java.util.ArrayList<>();
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String maKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_MA_KHACH_HANG));
                String tenKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_KHACH_HANG));
                String soDienThoai = cursor.getString(cursor.getColumnIndex(COLUMN_SO_DIEN_THOAI));
                String ngayDanhGia = cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_DANH_GIA));
                float binhChon = cursor.getFloat(cursor.getColumnIndex(COLUMN_BINH_CHON));
                try {
                    LocalDate date = LocalDate.parse(ngayDanhGia, DATE_FORMATTER);
                    KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, soDienThoai, date, binhChon);
                    khachHangList.add(khachHang);
                } catch (DateTimeParseException e) {
                    Log.e("SQLite", "Failed to parse date: " + ngayDanhGia + " for customer: " + maKhachHang, e);
                    // Optionally skip or handle invalid records
                }
            }
            cursor.close();
        }
        return khachHangList;
    }

    @SuppressLint("Range")
    public KhachHang getKhachHang(String maKhachHang) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_NAME, null, COLUMN_MA_KHACH_HANG + "=?", new String[]{maKhachHang}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String tenKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_KHACH_HANG));
            String soDienThoai = cursor.getString(cursor.getColumnIndex(COLUMN_SO_DIEN_THOAI));
            String ngayDanhGia = cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_DANH_GIA));
            float binhChon = cursor.getFloat(cursor.getColumnIndex(COLUMN_BINH_CHON));
            cursor.close();
            try {
                LocalDate date = LocalDate.parse(ngayDanhGia, DATE_FORMATTER);
                return new KhachHang(maKhachHang, tenKhachHang, soDienThoai, date, binhChon);
            } catch (DateTimeParseException e) {
                Log.e("SQLite", "Failed to parse date: " + ngayDanhGia + " for customer: " + maKhachHang, e);
                return null;
            }
        }
        return null;
    }
}
