package com.example.appdemo.Apdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;
import com.example.appdemo.models.Notify;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {

    private ArrayList<Notify> list;
    private Context context;

    public NotifyAdapter(ArrayList<Notify> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContentNotifi;
        public ImageView ibtnDelete;
        public ImageView image_food;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContentNotifi = itemView.findViewById(R.id.tvContentNotifi);
            ibtnDelete = itemView.findViewById(R.id.ibtnDelete);
            image_food = itemView.findViewById(R.id.image_food);
        }

        public void onBin(String url){
            if(url != null){
                Picasso.get().load(url).into(image_food);
            }
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notify, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

