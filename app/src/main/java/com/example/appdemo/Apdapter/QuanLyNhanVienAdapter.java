package com.example.appdemo.Apdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Holder.QuanLyNhanVienHolder;
import com.example.appdemo.ManHinh.ThemNV;
import com.example.appdemo.R;
import com.example.appdemo.models.NhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuanLyNhanVienAdapter extends RecyclerView.Adapter<QuanLyNhanVienHolder> {
    ArrayList<NhanVien> list= new ArrayList<>();
    Context c;
    DatabaseReference databaseReference;
    public QuanLyNhanVienAdapter(ArrayList<NhanVien> list, Context c) {
        this.list = list;
        this.c = c;
    }
    @NonNull
    @Override
    public QuanLyNhanVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhanvien_item,parent,false);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(0,10,0,10);
        view.setLayoutParams(layoutParams);
        return new QuanLyNhanVienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyNhanVienHolder holder, @SuppressLint("RecyclerView") int position) {
        NhanVien nhanVien=list.get(position);
        holder.tvMaNV.setText("Mã NV: "+nhanVien.getMaNV());
        holder.tvTenNV.setText("Tên nhân viên: "+nhanVien.getTen());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc chắn muốn xóa thông tin nhân viên"+nhanVien.getMaNV()+"không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       databaseReference = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
                      databaseReference.child(nhanVien.getMaNV()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              Toast.makeText(v.getContext(), "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
                          }
                      });
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        holder.btnEditNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThemNV.class);
                intent.putExtra("check","1");

            }
        });
    }

    public void setListNhanVien(ArrayList<NhanVien> listNV){
        this.list=listNV;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
