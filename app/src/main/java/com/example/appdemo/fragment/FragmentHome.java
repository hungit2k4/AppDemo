package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.appdemo.ManHinh.DangKyNghi;
import com.example.appdemo.ManHinh.Infor_account;
import com.example.appdemo.ManHinh.InputCodeActivity;
import com.example.appdemo.ManHinh.LoginActivity;
import com.example.appdemo.ManHinh.QuanLyNhanVien;
import com.example.appdemo.R;
import com.example.appdemo.fragment.create_account.Fragment_Create_Account;
import com.example.appdemo.models.Account;

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
        btnAddNV = view.findViewById(R.id.btnAddNV);
        String check = "^admin\\w{0,}";
        if (LoginActivity.idGui.matches(check))
           btnDoiMK.setVisibility(View.GONE);
        else
            cNAdmin.setVisibility(View.GONE);
        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Infor_account.class);
                startActivity(intent);
            }
        });
        btnChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment_ChamCong();
                RelativeLayout rltWelcome = getActivity().findViewById(R.id.rltWelcome);

                rltWelcome.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frLayout,fragment).addToBackStack(null).commit();
            }
        });
        btnDKNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), DangKyNghi.class);
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