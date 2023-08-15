package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appdemo.ManHinh.InputCodeActivity;
import com.example.appdemo.models.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.appdemo.Apdapter.QuanLyNhanVienAdapter;
import com.example.appdemo.ManHinh.DangKyNghi;
import com.example.appdemo.ManHinh.Fragment_ChamCong;
import com.example.appdemo.ManHinh.Infor_account;
import com.example.appdemo.ManHinh.LoginActivity;
import com.example.appdemo.ManHinh.QuanLyNhanVien;
import com.example.appdemo.ManHinh.Trang_Chu;
import com.example.appdemo.R;
import com.example.appdemo.fragment.create_account.Fragment_Create_Account;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    LinearLayout btnCaNhan, btnChamCong, btnDKNghi, btnDoiMK, btnCapTk, cNAdmin, cNNhanVien, btnAddNV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        btnCaNhan = view.findViewById(R.id.btnCaNhan);
        btnChamCong = view.findViewById(R.id.btnChamCong);
        btnDKNghi = view.findViewById(R.id.btnDKNghi);
        btnDoiMK = view.findViewById(R.id.btnDoiMK);
        btnCapTk = view.findViewById(R.id.btnCapTk);
        cNAdmin = view.findViewById(R.id.cNAdmin);
        cNNhanVien = view.findViewById(R.id.cNNhanVien);
        btnAddNV = view.findViewById(R.id.btnAddNV);
        String check = "^admin\\w{0,}";
        if (LoginActivity.idGui.matches(check))
            cNNhanVien.setVisibility(View.GONE);
        else
            cNAdmin.setVisibility(View.GONE);
        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Infor_account.class);
                startActivity(intent);
            }
        });
        btnChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment_ChamCong();

                Trang_Chu.rltWelcome.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frLayout, fragment).addToBackStack(null).commit();
            }
        });
        btnDKNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getContext(), DangKyNghi.class);
            startActivity(intent);
            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InputCodeActivity.class);
                Account account= LoginActivity.account;
                intent.putExtra("key", account.getUsername());
                intent.putExtra("fullName", account.getFullName());
                intent.putExtra("user", account.getUsername());
                intent.putExtra("pass", account.getPassword());
                startActivity(intent);
            }
        });
        btnCapTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment_Create_Account();
                RelativeLayout rltWelcome = getActivity().findViewById(R.id.rltWelcome);

                rltWelcome.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frLayout, fragment).addToBackStack(null).commit();
            }
        });
        btnAddNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), QuanLyNhanVien.class);
                startActivity(intent);

            }
        });

        return view;
    }
}