package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.appdemo.ManHinh.LoginActivity;
import com.example.appdemo.ManHinh.Trang_Chu;
import com.example.appdemo.R;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    LinearLayout btnCaNhan,btnChamCong,btnDKNghi,btnDoiMK,btnCapTk,cNAdmin,cNNhanVien;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_fragment_home, null);
        btnCaNhan= view.findViewById(R.id.btnCaNhan);
        btnChamCong= view.findViewById(R.id.btnChamCong);
        btnDKNghi= view.findViewById(R.id.btnDKNghi);
        btnDoiMK= view.findViewById(R.id.btnDoiMK);
        btnCapTk= view.findViewById(R.id.btnCapTk);
        cNAdmin= view.findViewById(R.id.cNAdmin);
        cNNhanVien= view.findViewById(R.id.cNNhanVien);
        String check = "^admin\\w{0,}";
        if (LoginActivity.idGui.matches(check))
            cNNhanVien.setVisibility(View.GONE);
        else
            cNAdmin.setVisibility(View.GONE);
        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDKNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCapTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fragment fragment =new Create_Account();
                RelativeLayout rltWelcome=getActivity().findViewById(R.id.rltWelcome);
                rltWelcome.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frLayout,fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
}