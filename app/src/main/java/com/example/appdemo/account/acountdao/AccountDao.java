package com.example.appdemo.account.acountdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appdemo.account.Account;
import com.example.appdemo.account.acdphelper.AcDphelper;

import java.util.ArrayList;

public class AccountDao {

    private  AcDphelper acDphelper;
   private SQLiteDatabase database;

    public  AccountDao(Context c){
        acDphelper = new AcDphelper(c);
    }
    public ArrayList<Account> getListAcount() {

        ArrayList<Account> list = new ArrayList<>();
        database = acDphelper.getReadableDatabase();
        database.beginTransaction();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM ACCOUNT", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    list.add(new Account(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("TAG", e.getMessage());
        } finally {
            database.endTransaction();
        }
        return list;
    }

    public long addAccount(Account account) {

        ContentValues values = new ContentValues();

        values.put("Username", account.getUsername());
        values.put("Password", account.getPassword());

        long check = database.insert("ACCOUNT", null, values);
        return check;
    }

    public long deleteAccount(int id) {

        long check = database.delete("ACCOUNT", "Id=?", new String[]{String.valueOf(id)});

        return check;
    }

    public long updateAccount(Account account) {

        ContentValues values = new ContentValues();

        values.put("Username", account.getUsername());
        values.put("Password", account.getPassword());

        long check = database.update("ACCOUNT", values, "Id=?", new String[]{String.valueOf(account.getId())});

        return check;
    }
    public void updateDatabase(){
        SQLiteDatabase db=acDphelper.getWritableDatabase();
       acDphelper.updateAccount(db);
    }

}
