package com.example.appdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.Apdapter.NotifyAdapter;
import com.example.appdemo.models.Notify;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThongBao extends Fragment {

    private RecyclerView rcvThongBao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thong_bao, container, false);
        rcvThongBao = view.findViewById(R.id.rcvThongBao);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Thông báo");
        FirebaseRecyclerOptions<Notify> options = new FirebaseRecyclerOptions.Builder<Notify>().setQuery(databaseRef, Notify.class).build();
        FirebaseRecyclerAdapter<Notify, NotifyAdapter.ViewHolder> adapter = new FirebaseRecyclerAdapter<Notify, NotifyAdapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position, @NonNull Notify model) {

            }

            @NonNull
            @Override
            public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };

        return view;
    }
}