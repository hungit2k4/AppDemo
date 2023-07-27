package com.example.appdemo.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Holder.QuanLyNhanVienHolder;
import com.example.appdemo.R;
import com.example.appdemo.models.NhanVien;

import java.util.ArrayList;

public class QuanLyNhanVienAdapter extends RecyclerView.Adapter<QuanLyNhanVienHolder> {
    ArrayList<NhanVien> list= new ArrayList<>();
    Context c;

    public QuanLyNhanVienAdapter(ArrayList<NhanVien> list, Context c) {
        this.list = list;
        this.c = c;
    }
    @NonNull
    @Override
    public QuanLyNhanVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhanvien_item,null);
        return new QuanLyNhanVienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyNhanVienHolder holder, int position) {
        NhanVien nhanVien=list.get(position);
        holder.tvMaNV.setText("Mã NV: "+nhanVien.getMaNV());

        holder.tvTenNV.setText("Tên nhân viên: "+nhanVien.getTen());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}