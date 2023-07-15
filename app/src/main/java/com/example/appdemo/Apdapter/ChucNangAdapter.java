package com.example.appdemo.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Holder.ChucNangHolder;
import com.example.appdemo.Model.ChucNang;
import com.example.appdemo.R;

import java.util.ArrayList;

public class ChucNangAdapter extends RecyclerView.Adapter<ChucNangHolder> {
    ArrayList<ChucNang> list;
    Context context;

    public ChucNangAdapter(ArrayList<ChucNang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ChucNangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trang_chu_item,null);
        return new ChucNangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChucNangHolder holder, int position) {
        ChucNang chucNang = list.get(position);
        holder.imvCN.setImageResource(chucNang.getIcon());
        holder.tvCN1.setText(chucNang.getTxt1());
        holder.tvCN2.setText(chucNang.getTxt2());
        holder.layoutCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"chonhhh",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
