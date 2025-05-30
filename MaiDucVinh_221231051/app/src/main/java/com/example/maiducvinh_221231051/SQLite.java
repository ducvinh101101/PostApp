package com.example.maiducvinh_221231051;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "monhoc.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "monhoc";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TINCHI = "tinchi";
    public static final String COLUMN_DIEMQUATRINH = "diemquatrinh";
    public static final String COLUMN_DIEMTHI = "diemthi";
    public static final String COLUMN_LOAI = "loai";


    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TINCHI + " INTEGER, " +
                COLUMN_DIEMQUATRINH + " FLOAT, " +
                COLUMN_DIEMTHI + " FLOAT, " +
                COLUMN_LOAI + " INTEGER);";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addHoaDon(String tenMon, int soTin, float diemQuaTrinh, float diemThi, int loai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, getNextId());
        values.put(COLUMN_NAME, tenMon);
        values.put(COLUMN_TINCHI,soTin);
        values.put(COLUMN_DIEMQUATRINH,diemQuaTrinh);
        values.put(COLUMN_DIEMTHI,diemThi);
        values.put(COLUMN_LOAI,loai);
        db.insert(TABLE_NAME, null, values);
    }
    @SuppressLint("Range")
    public String getNextId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String lastId = "MH41";
        if (cursor.moveToFirst()) {
            lastId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        // Tăng số lên 1
        int num = Integer.parseInt(lastId.replace("MH", "")) + 10;
        return "MH" + num;
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
    public List<MonThi> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<MonThi> monthi = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int tinchi = cursor.getInt(cursor.getColumnIndex(COLUMN_TINCHI));
                float quatrinh = cursor.getFloat(cursor.getColumnIndex(COLUMN_DIEMQUATRINH));
                float diemthi = cursor.getFloat(cursor.getColumnIndex(COLUMN_DIEMTHI));
                int loai = cursor.getInt(cursor.getColumnIndex(COLUMN_LOAI));
                monthi.add(new MonThi(id,name,tinchi,quatrinh,diemthi,loai));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return monthi;
    }


}
