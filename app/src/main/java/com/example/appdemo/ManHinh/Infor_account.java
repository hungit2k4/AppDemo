package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdemo.R;

public class Infor_account extends AppCompatActivity {
    Button btnHuyAcc,btnLuuAcc;
    ImageView btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_account);
        btnHuyAcc=findViewById(R.id.btnHuyAcc);
        btnLuuAcc=findViewById(R.id.btnLuuAcc);
        btnBack = findViewById(R.id.btnBack);
        btnHuyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLuuAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}