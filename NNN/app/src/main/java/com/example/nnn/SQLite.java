package com.example.nnn;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "hoadon.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "hoadonkhachsan";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_GIA = "dongia";
    public static final String COLUMN_SONGAYLUUTRU = "songayluutru";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DAY + " TEXT, " +
                COLUMN_GIA + " INTEGER, " +
                COLUMN_SONGAYLUUTRU + " INTEGER);";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addHoaDon(String tenKhachHang, String ngayLap, int donGia, int soNgayLuuTru) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, getNextId());
        values.put(COLUMN_NAME, tenKhachHang);
        values.put(COLUMN_DAY, ngayLap);
        values.put(COLUMN_GIA, donGia);
        values.put(COLUMN_SONGAYLUUTRU, soNgayLuuTru);
        db.insert(TABLE_NAME, null, values);
    }
    @SuppressLint("Range")
    public String getNextId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String lastId = "HD10";
        if (cursor.moveToFirst()) {
            lastId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        // Tăng số lên 1
        int num = Integer.parseInt(lastId.replace("HD", "")) + 1;
        return "HD" + num;
    }
    public void updateHoaDon(String id , String name, String day, int donGia, int soNgayLuuTru) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DAY, day);
        values.put(COLUMN_GIA, donGia);
        values.put(COLUMN_SONGAYLUUTRU, soNgayLuuTru);
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{id});
    }

    public void deleteHoaDon(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{studentId});
    }

    public void deleteAllHoaDon() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    @SuppressLint("Range")
    public HoaDon getHoaDon(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String day = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
            int donGia = cursor.getInt(cursor.getColumnIndex(COLUMN_GIA));
            int soNgayLuuTru = cursor.getInt(cursor.getColumnIndex(COLUMN_SONGAYLUUTRU));
            cursor.close();
            return new HoaDon(id, name, day, donGia, soNgayLuuTru);
        }
        return null;
    }
    @SuppressLint("Range")
    public List<HoaDon> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<HoaDon> hoaDonList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String day = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
                int donGia = cursor.getInt(cursor.getColumnIndex(COLUMN_GIA));
                int soNgayLuuTru = cursor.getInt(cursor.getColumnIndex(COLUMN_SONGAYLUUTRU));
                hoaDonList.add(new HoaDon(id, name, day, donGia, soNgayLuuTru));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return hoaDonList;
    }

    @SuppressLint("Range")
    public List<HoaDon> searchHoaDonByName(String searchName) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<HoaDon> hoaDonList = new ArrayList<>();
        // Use LIKE for partial matching, % wildcards for substring search
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + searchName + "%"});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String day = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
                int donGia = cursor.getInt(cursor.getColumnIndex(COLUMN_GIA));
                int soNgayLuuTru = cursor.getInt(cursor.getColumnIndex(COLUMN_SONGAYLUUTRU));
                hoaDonList.add(new HoaDon(id, name, day, donGia, soNgayLuuTru));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return hoaDonList;
    }

    @SuppressLint("Range")
    public List<HoaDon> getAllHoaDonSort() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME, null);
        List<HoaDon> hoaDonList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String day = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
                int donGia = cursor.getInt(cursor.getColumnIndex(COLUMN_GIA));
                int soNgayLuuTru = cursor.getInt(cursor.getColumnIndex(COLUMN_SONGAYLUUTRU));
                hoaDonList.add(new HoaDon(id, name, day, donGia, soNgayLuuTru));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return hoaDonList;
    }
}
