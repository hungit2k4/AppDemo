package com.example.appdemo.Apdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;

public class XinNghi_Adapter extends RecyclerView.Adapter<XinNghi_Adapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvMaNvOff, tvNgayStart, tvNgayEnd, tvLiDo, tvStatus;
        public ImageButton ibtnAccept, ibtnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaNvOff = itemView.findViewById(R.id.tvMaNvOff);
            tvNgayStart = itemView.findViewById(R.id.tvNgayStart);
            tvNgayEnd = itemView.findViewById(R.id.tvNgayEnd);
            tvLiDo = itemView.findViewById(R.id.tvLiDo);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ibtnAccept = itemView.findViewById(R.id.ibtnAccept);
            ibtnCancel = itemView.findViewById(R.id.ibtnCancel);

        }
    }
    @NonNull
    @Override
    public XinNghi_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xinnghi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XinNghi_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
