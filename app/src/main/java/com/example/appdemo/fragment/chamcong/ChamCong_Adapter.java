package com.example.appdemo.fragment.chamcong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;
import com.example.appdemo.models.ChamCong;

import java.util.ArrayList;

public class ChamCong_Adapter extends RecyclerView.Adapter<ChamCong_Adapter.ViewHolder> {

    ArrayList<ChamCong> list;
    Context context;

    public ChamCong_Adapter(ArrayList<ChamCong> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamcong, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgAvatar_item, btnDelete;
        public TextView tvMaNV, tvTenNV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar_item = itemView.findViewById(R.id.imgAvatar_item);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvMaNV = itemView.findViewById(R.id.tvMaNV);
            tvTenNV = itemView.findViewById(R.id.tvTenNV);

        }

    }
}
