package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.Apdapter.ChucNangAdapter;
import com.example.appdemo.data.acountdao.ChucNang;
import com.example.appdemo.R;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    ArrayList<ChucNang> list;
    ChucNangAdapter adapter;
    RecyclerView rcvCN;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_fragment_home, null);

        rcvCN = view.findViewById(R.id.rcvCN);
        list= new ArrayList<>();
        list.add(new ChucNang(1,R.drawable.icon_thongtin,"Thông tin","Thông tin cá nhân"));
        list.add(new ChucNang(2, R.drawable.baseline_calendar_month_24,"Lịch họp","Thông tin lịch họp"));
        list.add(new ChucNang(3,R.drawable.baseline_calendar_month_24,"Chấm công","Bảng chấm công"));
        list.add(new ChucNang(4,R.drawable.baseline_query_builder_24,"Lịch sử nghỉ","Lịch sử nghỉ,đi muộn"));
        list.add(new ChucNang(5,R.drawable.baseline_switch_account_24, "Cấp tài khoản", "Cấp tài khoản mới"));

        adapter=new ChucNangAdapter(list,getContext());

        rcvCN.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        rcvCN.setLayoutManager(layoutManager);
        return view;
    }
}