package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appdemo.Apdapter.ChucNangAdapter;
import com.example.appdemo.Model.ChucNang;
import com.example.appdemo.R;

import java.util.ArrayList;

public class Trang_Chu extends AppCompatActivity {
    ArrayList<ChucNang> list;
    ChucNangAdapter adapter;
    RecyclerView rcvCN;
    TextView btnAccount,btnThongBao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        rcvCN=findViewById(R.id.rcvCN);
        btnAccount = findViewById(R.id.btnAccount);
        list= new ArrayList<>();
        list.add(new ChucNang(R.drawable.icon_thongtin,"Thông tin","Thông tin cá nhân"));
        list.add(new ChucNang(R.drawable.baseline_calendar_today_24,"Lịch họp","Thông tin lịch họp"));
        list.add(new ChucNang(R.drawable.baseline_calendar_month_24,"Chấm công","Bảng chấm công"));
        list.add(new ChucNang(R.drawable.baseline_query_builder_24,"Lịch sử nghỉ","Lịch sử nghỉ,đi muộn"));
        adapter=new ChucNangAdapter(list,this);
        rcvCN.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        rcvCN.setLayoutManager(layoutManager);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Chu.this, Infor_account.class);
                startActivity(intent);
            }
        });
        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Chu.this, Infor_account.class);
                startActivity(intent);
            }
        });
    }
}