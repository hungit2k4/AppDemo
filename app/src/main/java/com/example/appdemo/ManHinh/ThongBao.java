package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.Apdapter.NotifyAdapter;
import com.example.appdemo.R;
import com.example.appdemo.models.Notify;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ThongBao extends Fragment {

    private RecyclerView rcvThongBao;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thong_bao, container, false);
        rcvThongBao = view.findViewById(R.id.rcvThongBao);
        rcvThongBao.setHasFixedSize(true);
        rcvThongBao.setLayoutManager(new LinearLayoutManager(getContext()));

        String check = "^admin\\w{0,}";
        boolean checkAccount = LoginActivity.idGui.matches(check);

        databaseRef = FirebaseDatabase.getInstance().getReference("Thông báo");

        FirebaseRecyclerOptions<Notify> options = new FirebaseRecyclerOptions.Builder<Notify>().setQuery(databaseRef, Notify.class).build();
        FirebaseRecyclerAdapter<Notify, NotifyAdapter.ViewHolder> adapter = new FirebaseRecyclerAdapter<Notify, NotifyAdapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position, @NonNull Notify model) {
                String key;

                    key = getRef(position).getKey();

                databaseRef.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild("contentNotify")) {
                            DatabaseReference databaseRef_image = FirebaseDatabase.getInstance().getReference("ImageAccount");
                            databaseRef_image.child(key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.hasChild("urlImage")) {
                                        Picasso.get().load(snapshot.child("urlImage").getValue().toString()).into(holder.image_food);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            holder.tvContentNotifi.setText(snapshot.child("contentNotify").getValue().toString());
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), DangKyNghi.class);
                                    startActivity(intent);
                                }
                            });
                            holder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    databaseRef.child(key).removeValue();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notify, parent, false);
                return new NotifyAdapter.ViewHolder(view);
            }
        };

        rcvThongBao.setAdapter(adapter);
        adapter.startListening();
        return view;
    }
}