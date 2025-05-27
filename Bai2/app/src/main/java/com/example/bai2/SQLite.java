package com.example.bai2;

import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sanpham.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "sanpham";
    public static final String COLUMN_MASANPHAM = "masanpham";
    public static final String COLUMN_TENSANPHAM = "tensanpham";
    public static final String COLUMN_GIASANPHAM = "giasanpham";
    public static final String COLUMN_KHUYENMAI = "khuyenmai";

    public SQLite(android.content.Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_MASANPHAM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TENSANPHAM + " TEXT, " +
                COLUMN_GIASANPHAM + " INTEGER, " +
                COLUMN_KHUYENMAI + " BOOLEAN);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addSanPham(String tenSanPham, int giaSanPham, boolean khuyenMai) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_TENSANPHAM, tenSanPham);
        values.put(COLUMN_GIASANPHAM, giaSanPham);
        values.put(COLUMN_KHUYENMAI, khuyenMai);
        db.insert(TABLE_NAME, null, values);
    }
    public void deleteSanPham(int maSanPham) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_MASANPHAM + " = ?", new String[]{String.valueOf(maSanPham)});
    }
    public void updateSanPham(int maSanPham, String tenSanPham, int giaSanPham, boolean khuyenMai) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_TENSANPHAM, tenSanPham);
        values.put(COLUMN_GIASANPHAM, giaSanPham);
        values.put(COLUMN_KHUYENMAI, khuyenMai);
        db.update(TABLE_NAME, values, COLUMN_MASANPHAM + " = ?", new String[]{String.valueOf(maSanPham)});
    }

    @android.annotation.SuppressLint("Range")
    public java.util.List<SanPham> getAllSanPham() {
        java.util.List<SanPham> sanPhamList = new java.util.ArrayList<>();
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham sanPham = new SanPham();
                sanPham.setMaSanPham(cursor.getString(cursor.getColumnIndex(COLUMN_MASANPHAM)));
                sanPham.setTenSanPham(cursor.getString(cursor.getColumnIndex(COLUMN_TENSANPHAM)));
                sanPham.setGiaSanPham(cursor.getInt(cursor.getColumnIndex(COLUMN_GIASANPHAM)));
                sanPham.setKhuyenMai(cursor.getInt(cursor.getColumnIndex(COLUMN_KHUYENMAI)) > 0);
                sanPhamList.add(sanPham);
            }
            cursor.close();
        }
        return sanPhamList;
    }

    @android.annotation.SuppressLint("Range")
    public SanPham getSanPhamById(int maSanPham) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_NAME, null, COLUMN_MASANPHAM + " = ?", new String[]{String.valueOf(maSanPham)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            SanPham sanPham = new SanPham();
            sanPham.setMaSanPham(cursor.getString(cursor.getColumnIndex(COLUMN_MASANPHAM)));
            sanPham.setTenSanPham(cursor.getString(cursor.getColumnIndex(COLUMN_TENSANPHAM)));
            sanPham.setGiaSanPham(cursor.getInt(cursor.getColumnIndex(COLUMN_GIASANPHAM)));
            sanPham.setKhuyenMai(cursor.getInt(cursor.getColumnIndex(COLUMN_KHUYENMAI)) > 0);
            cursor.close();
            return sanPham;
        }
        return null;
    }

    public void deleteAllSanPham() {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }



}
