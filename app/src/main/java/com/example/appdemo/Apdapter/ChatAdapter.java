package com.example.appdemo.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Holder.ChatHolder;
import com.example.appdemo.ManHinh.LoginActivity;
import com.example.appdemo.R;
import com.example.appdemo.models.TinNhan;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {
    ArrayList<TinNhan> list;
    Context c;

    public ChatAdapter(ArrayList<TinNhan> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        TinNhan tinNhan = list.get(position);
        String test = tinNhan.getIdGui();
        if (LoginActivity.idGui.equals(tinNhan.getIdGui())){
            holder.tvChatGui.setText(tinNhan.getNoiDung());
            holder.tvChatGui.setBackground(ContextCompat.getDrawable(c,R.drawable.khung_tngui));
        }if (LoginActivity.idGui.equals(tinNhan.getIdNhan())){
            holder.tvChatNhan.setText(tinNhan.getNoiDung());
            holder.tvChatNhan.setBackground(ContextCompat.getDrawable(c,R.drawable.khung_tnnhan));
        }
    }
    public void setListTN(ArrayList<TinNhan>listtn) {
        this.list = listtn;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
