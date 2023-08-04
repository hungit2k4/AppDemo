package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appdemo.Apdapter.QuanLyNhanVienAdapter;
import com.example.appdemo.R;
import com.example.appdemo.models.NhanVien;

import java.util.ArrayList;

public class QuanLyNhanVien extends AppCompatActivity {
    ArrayList<NhanVien> list= new ArrayList<>();
    QuanLyNhanVienAdapter nhanVienAdapter;
    RecyclerView rcvNhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        rcvNhanVien= findViewById(R.id.rcvNhanvien);
        nhanVienAdapter = new QuanLyNhanVienAdapter(list,this);
        rcvNhanVien.setAdapter(nhanVienAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvNhanVien.setLayoutManager(layoutManager);
    }
}