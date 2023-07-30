package com.example.appdemo.data.acountdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdemo.data.acdphelper.AcDphelper;
import com.example.appdemo.models.TinNhan;

import java.util.ArrayList;

public class TinNhanDAO {
    AcDphelper acDphelper;
    SQLiteDatabase database;

    public TinNhanDAO(Context c) {
        acDphelper = new AcDphelper(c);
    }
    public ArrayList<TinNhan> getAllTinNhan(Context c,String idGuiVaNhan){
        ArrayList<TinNhan> list = new ArrayList<>();
        database = acDphelper.getReadableDatabase();
        Cursor cursor= database.query("tinnhan",null,"idgui=? or idnhan=?"
                ,new String[]{idGuiVaNhan,idGuiVaNhan},null,null,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new TinNhan(cursor.getString(0),cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void addTinNhan(TinNhan tn){
        SQLiteDatabase db = acDphelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idgui",tn.getIdGui());
        values.put("noidung",tn.getNoiDung());
        values.put("idnhan",tn.getIdNhan());
        db.insert("tinnhan",null,values);
    }
    public void updateDatabase(){
        SQLiteDatabase db =acDphelper.getWritableDatabase();
        acDphelper.updateTN(db);
    }
}
