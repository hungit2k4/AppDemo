package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyNhanVien extends AppCompatActivity {
    ArrayList<NhanVien> list= new ArrayList<>();
    QuanLyNhanVienAdapter nhanVienAdapter;
    RecyclerView rcvNhanVien;
    ImageView btnAdd,btnBackQLNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        rcvNhanVien= findViewById(R.id.rcvNhanvien);
        btnAdd= findViewById(R.id.btnAdd);
        btnBackQLNV= findViewById(R.id.btnBackQLNV);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list= new ArrayList<>();
                for (DataSnapshot x:snapshot.getChildren()){
                    NhanVien nhanVien = x.getValue(NhanVien.class);
                    list.add(nhanVien);
                }
                nhanVienAdapter.setListNhanVien(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nhanVienAdapter = new QuanLyNhanVienAdapter(list,QuanLyNhanVien.this);
        rcvNhanVien.setAdapter(nhanVienAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(QuanLyNhanVien.this);
        rcvNhanVien.setLayoutManager(layoutManager);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLyNhanVien.this, ThemNV.class);
                startActivity(intent);
            }
        });
        btnBackQLNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}