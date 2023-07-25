package com.example.appdemo.data.acdphelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AcDphelper extends SQLiteOpenHelper {

    public AcDphelper( Context context) {
        super(context, "ACOUNT", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlAcc = "CREATE TABLE ACCOUNT( Id integer PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT," +
                "Password TEXT)";
        db.execSQL(sqlAcc);
        String sqlNV="create table nhanvien(\n" +
                "maNV text primary key,\n" +
                "ten text,\n" +
                "ngaySinh text,\n" +
                "gioiTinh text,\n" +
                "soDT integer,\n" +
                "diaChi text,\n" +
                "chucVu text\n" +
                ")";
        db.execSQL(sqlNV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS ACCOUNT");
            onCreate(db);
    }
    public void updateAccount(SQLiteDatabase db){
        db.execSQL("drop table if exists account");
        String sqlAcc = "CREATE TABLE ACCOUNT( Id integer PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT," +
                "Password TEXT)";
        db.execSQL(sqlAcc);
    }
    public void updateNV(SQLiteDatabase db){
        db.execSQL("drop table if exists nhanvien");
        String sqlNV="create table nhanvien(\n" +
                "maNV text primary key,\n" +
                "ten text,\n" +
                "ngaySinh text,\n" +
                "gioiTinh text,\n" +
                "soDT integer,\n" +
                "diaChi text,\n" +
                "chucVu text\n" +
                ")";
        db.execSQL(sqlNV);
    }
}
