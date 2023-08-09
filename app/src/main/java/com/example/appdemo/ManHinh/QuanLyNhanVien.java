package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appdemo.Apdapter.QuanLyNhanVienAdapter;
import com.example.appdemo.R;
import com.example.appdemo.models.NhanVien;

import java.util.ArrayList;

public class QuanLyNhanVien extends AppCompatActivity {
    ArrayList<NhanVien> list= new ArrayList<>();
    QuanLyNhanVienAdapter nhanVienAdapter;
    RecyclerView rcvNhanVien;
    ImageView btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        rcvNhanVien= findViewById(R.id.rcvNhanvien);
        btnAdd= findViewById(R.id.btnAdd);
        nhanVienAdapter = new QuanLyNhanVienAdapter(list,this);
        rcvNhanVien.setAdapter(nhanVienAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvNhanVien.setLayoutManager(layoutManager);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLyNhanVien.this, ThemNV.class);
                startActivity(intent);
            }
        });
    }
}