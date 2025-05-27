package com.example.hanghoa;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class SQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "hanghoa.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "hanghoa";
    public static final String COLUMN_MAHANG = "mahang";
    public static final String COLUMN_TENHANG = "tenhang";
    public static final String COLUMN_GIANIEMYET = "gianiemyet";
    public static final String COLUMN_GIAMGIA = "giamgia";

    public SQLite(android.content.Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_MAHANG + " INTEGER PRIMARY KEY, " +
                COLUMN_TENHANG + " TEXT, " +
                COLUMN_GIANIEMYET + " INTEGER, " +
                COLUMN_GIAMGIA + " BOOLEAN);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Range")
    public int getNextId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_MAHANG + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_MAHANG + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String lastId = "41";
        if (cursor.moveToFirst()) {
            lastId = cursor.getString(cursor.getColumnIndex(COLUMN_MAHANG));
        }
        cursor.close();
        int num = Integer.parseInt(lastId) + 10;
        return num;
    }

    public void addHangHoa(String tenHang, int giaNiemYet, boolean giamGia) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_MAHANG, getNextId());
        values.put(COLUMN_TENHANG, tenHang);
        values.put(COLUMN_GIANIEMYET, giaNiemYet);
        values.put(COLUMN_GIAMGIA, giamGia);
        db.insert(TABLE_NAME, null, values);
    }

    public void deleteHangHoa(int maHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_MAHANG + " = ?", new String[]{String.valueOf(maHang)});
    }
    public void updateHangHoa(int maHang, String tenHang, int giaNiemYet, boolean giamGia) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_TENHANG, tenHang);
        values.put(COLUMN_GIANIEMYET, giaNiemYet);
        values.put(COLUMN_GIAMGIA, giamGia);
        db.update(TABLE_NAME, values, COLUMN_MAHANG + " = ?", new String[]{String.valueOf(maHang)});
    }

    @SuppressLint("Range")
    public List<HangHoa> getAllHangHoa() {
        java.util.List<HangHoa> hangHoaList = new java.util.ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                HangHoa hangHoa = new HangHoa(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_MAHANG)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TENHANG)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_GIANIEMYET)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_GIAMGIA)) > 0
                );
                hangHoaList.add(hangHoa);
            }
            cursor.close();
        }
        return hangHoaList;
    }
    @SuppressLint("Range")
    public HangHoa getHangHoaById(int maHang) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_MAHANG + " = ?", new String[]{String.valueOf(maHang)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            HangHoa hangHoa = new HangHoa(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_MAHANG)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TENHANG)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_GIANIEMYET)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_GIAMGIA)) > 0
            );
            cursor.close();
            return hangHoa;
        }
        return null;
    }


}

