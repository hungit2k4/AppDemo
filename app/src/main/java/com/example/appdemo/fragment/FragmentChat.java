package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appdemo.Apdapter.ChatAdapter;
import com.example.appdemo.ManHinh.LoginActivity;
import com.example.appdemo.R;
import com.example.appdemo.models.TinNhan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentChat extends Fragment {
    public static String idGui;
    private ArrayList<TinNhan>  list=new ArrayList<>();
    private String child;
    RecyclerView rcvChat;
    EditText edtChat;
    ImageView btnSendTN;
    ChatAdapter adapter;
    TextView tvTenchat;
    RelativeLayout rltWelcome;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,null);
        edtChat = view.findViewById(R.id.edtChat);
        btnSendTN = view.findViewById(R.id.btnSendTN);
        rcvChat = view.findViewById(R.id.rcvChat);
        tvTenchat = view.findViewById(R.id.tvTenchat);
        tvTenchat.setText(LoginActivity.idNhan);
        child= "admin2vsadmin";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admin2vsadmin");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot child : snapshot.getChildren()) {
                    TinNhan tinNhan = child.getValue(TinNhan.class);
                    list.add(tinNhan);
                }

                adapter.setListTN(list); // Cập nhật dữ liệu mới
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // ...

        });
        adapter = new ChatAdapter(list,getContext());
        rcvChat.setAdapter(adapter);
       LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
       rcvChat.setLayoutManager(layoutManager);

        btnSendTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nd= edtChat.getText().toString();
                list.add(new TinNhan(LoginActivity.idGui,nd,LoginActivity.idNhan));
            DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference();
            databaseReference.child(child).setValue(list, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
            }
        });

        return view;
    }
}