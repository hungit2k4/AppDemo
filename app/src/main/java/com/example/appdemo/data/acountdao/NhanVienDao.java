package com.example.appdemo.data.acountdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdemo.models.NhanVien;

import java.util.ArrayList;

public class NhanVienDao {
    private SQLiteDatabase database;
    public ArrayList<NhanVien> getListNV(){
        ArrayList<NhanVien> list= new ArrayList<>();
        Cursor c = database.rawQuery("select * from nhanvien",null);
        if (c.getCount()>0){
            c.moveToFirst();
            do{
                list.add(new NhanVien(c.getString(0),c.getString(1),
                        c.getString(2),c.getInt(3),
                        c.getInt(4),c.getString(5),c.getString(6)));
            }while (c.moveToNext());
        }
      return list;
    }
    public void addNV(NhanVien nv){
        ContentValues values = new ContentValues();
        values.put("manv",nv.getMaNV());
        values.put("ten",nv.getTen());
        values.put("ngaysinh",nv.getNgaySinh());
        values.put("gioitinh",nv.getGioiTinh());
        values.put("sodt",nv.getSoDT());
        values.put("diachi",nv.getDiaChi());
        values.put("chucvu",nv.getChucVu());
    }
    public void deletaNV(String id){
        database.delete("nhanvien","manv=?",new String[]{id});
    }
    public void updateNV(NhanVien nv){
        ContentValues values = new ContentValues();
        values.put("ten",nv.getTen());
        values.put("ngaysinh",nv.getNgaySinh());
        values.put("gioitinh",nv.getGioiTinh());
        values.put("sodt",nv.getSoDT());
        values.put("diachi",nv.getDiaChi());
        values.put("chucvu",nv.getChucVu());
        database.update("nhanvien",values,"mavn=?",new String[]{nv.getMaNV()});
    }

}
