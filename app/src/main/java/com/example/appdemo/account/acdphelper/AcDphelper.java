package com.example.appdemo.account.acdphelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AcDphelper extends SQLiteOpenHelper {

    public AcDphelper( Context context) {
        super(context, "ACOUNT", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE ACCOUNT( Id integer PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT," +
                "Password TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS ACCOUNT");
            onCreate(db);
    }
    public void updateAccount(SQLiteDatabase db){
        db.execSQL("drop table if exists account");
        String sql = "CREATE TABLE ACCOUNT( Id integer PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT," +
                "Password TEXT)";
        db.execSQL(sql);
    }
}
