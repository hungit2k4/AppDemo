package com.example.appdemo.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;

public class QuanLyNhanVienHolder extends RecyclerView.ViewHolder {
    public TextView tvMaNV,tvTenNV;
   public ImageView btnEditNV,btnDelete;
    public QuanLyNhanVienHolder(@NonNull View itemView) {
        super(itemView);
        tvMaNV= itemView.findViewById(R.id.tvMaNV);
        tvTenNV= itemView.findViewById(R.id.tvTenNV);
        btnEditNV= itemView.findViewById(R.id.btnEditNV);
        btnDelete= itemView.findViewById(R.id.btnDelete);
    }
}
