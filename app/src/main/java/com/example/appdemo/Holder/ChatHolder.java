package com.example.appdemo.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;

public class ChatHolder extends RecyclerView.ViewHolder {
    public TextView tvChatNhan,tvChatGui;
    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        tvChatNhan =itemView.findViewById(R.id.tvChatNhan);
        tvChatGui =itemView.findViewById(R.id.tvChatGui);
    }
}
