package com.example.bai1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "sinhvien.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "sinhvien";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TOAN = "toan";
    public static final String COLUMN_LY = "ly";
    public static final String COLUMN_HOA = "hoa";

    public SQLite(Context context) {
        super(null, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_ID + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TOAN + " REAL, " +
                COLUMN_LY + " REAL, " +
                COLUMN_HOA + " REAL);";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addStudent(String studentId, String name, double toan, double ly, double hoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TOAN, toan);
        values.put(COLUMN_LY, ly);
        values.put(COLUMN_HOA, hoa);
        db.insert(TABLE_NAME, null, values);
    }
    public void updateStudent(int id, String studentId, String name, double toan, double ly, double hoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TOAN, toan);
        values.put(COLUMN_LY, ly);
        values.put(COLUMN_HOA, hoa);
        db.update(TABLE_NAME, values, COLUMN_STUDENT_ID + "=?", new String[]{studentId});
    }

    public void deleteStudent(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{studentId});
    }

    public void deleteAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    @SuppressLint("Range")
    public SinhVien getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String studentId = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            double toan = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOAN));
            double ly = cursor.getDouble(cursor.getColumnIndex(COLUMN_LY));
            double hoa = cursor.getDouble(cursor.getColumnIndex(COLUMN_HOA));
            cursor.close();
            return new SinhVien(id, studentId, name, toan, ly, hoa);
        }
        return null;
    }
    @SuppressLint("Range")
    public List<SinhVien> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME, null);
        List<SinhVien> sinhVienList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String studentId = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                double toan = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOAN));
                double ly = cursor.getDouble(cursor.getColumnIndex(COLUMN_LY));
                double hoa = cursor.getDouble(cursor.getColumnIndex(COLUMN_HOA));
                sinhVienList.add(new SinhVien(id, studentId, name, toan, ly, hoa));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return sinhVienList;
    }

}
