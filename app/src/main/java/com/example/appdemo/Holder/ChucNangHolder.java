package com.example.appdemo.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;

public class ChucNangHolder extends RecyclerView.ViewHolder {
  public  TextView tvCN1,tvCN2;
  public ImageView imvCN;
  public LinearLayout layoutCN;
    public ChucNangHolder(@NonNull View itemView) {
        super(itemView);
        imvCN=itemView.findViewById(R.id.imvCN);
        tvCN1= itemView.findViewById(R.id.tvCN1);
        tvCN2=itemView.findViewById(R.id.tvCN2);
        layoutCN= itemView.findViewById(R.id.layoutCN);
    }
}
