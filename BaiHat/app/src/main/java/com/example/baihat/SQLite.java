package com.example.baihat;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "baihat.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "baihat";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEN_BAI_HAT = "ten_baihat";
    public static final String COLUMN_CA_SY = "casy";
    public static final String COLUMN_LIKE = "likeH";
    public static final String COLUMN_SHARE = "share";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public SQLite(android.content.Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TEN_BAI_HAT + " TEXT, " +
                COLUMN_CA_SY + " TEXT, " +
                COLUMN_LIKE + " INTEGER, " +
                COLUMN_SHARE + " INTEGER );";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addKhachHang(String tenBaiHat, String tenCaSy, int solike, int soshare) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_ID, getNextId());
        values.put(COLUMN_TEN_BAI_HAT, tenBaiHat);
        values.put(COLUMN_CA_SY, tenCaSy);
        values.put(COLUMN_LIKE, solike);
        values.put(COLUMN_SHARE, soshare);
        db.insert(TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public int getNextId() {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        android.database.Cursor cursor = db.rawQuery(selectQuery, null);
        String lastId = "41";
        if (cursor.moveToFirst()) {
            lastId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        int num = Integer.parseInt(lastId) + 10;
        return num;
    }

    public void deleteKhachHang(int maKhachHang) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(maKhachHang)});
    }

    public void updateKhachHang(String maKhachHang, String tenKhachHang, String tenCasy, int solike, int soshare) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_TEN_BAI_HAT, tenKhachHang);
        values.put(COLUMN_CA_SY, tenCasy);
        values.put(COLUMN_LIKE, solike);
        values.put(COLUMN_SHARE, soshare);
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{maKhachHang});
    }

    @SuppressLint("Range")
    public String getMaKhachHang(String tenKhachHang) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?";
        android.database.Cursor cursor = db.rawQuery(selectQuery, new String[]{tenKhachHang});
        String maKhachHang = null;
        if (cursor.moveToFirst()) {
            maKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        return maKhachHang;
    }
    @SuppressLint("Range")
    public java.util.List<BaiHat> getAllKhachHang() {
        java.util.List<BaiHat> khachHangList = new java.util.ArrayList<>();
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int maKhachHang = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String tenKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_BAI_HAT));
                String soDienThoai = cursor.getString(cursor.getColumnIndex(COLUMN_CA_SY));
                int ngayDanhGia = cursor.getInt(cursor.getColumnIndex(COLUMN_LIKE));
                int binhChon = cursor.getInt(cursor.getColumnIndex(COLUMN_SHARE));
                khachHangList.add(new BaiHat(maKhachHang,tenKhachHang,soDienThoai,ngayDanhGia,binhChon));
            }
            cursor.close();
        }
        return khachHangList;
    }

    @SuppressLint("Range")
    public BaiHat getKhachHang(int maKhachHang) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(maKhachHang)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String tenKhachHang = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_BAI_HAT));
            String soDienThoai = cursor.getString(cursor.getColumnIndex(COLUMN_CA_SY));
            int ngayDanhGia = cursor.getInt(cursor.getColumnIndex(COLUMN_LIKE));
            int binhChon = cursor.getInt(cursor.getColumnIndex(COLUMN_SHARE));
            cursor.close();
            return new BaiHat(maKhachHang,tenKhachHang,soDienThoai,ngayDanhGia,binhChon);
        }
        return null;
    }
}

