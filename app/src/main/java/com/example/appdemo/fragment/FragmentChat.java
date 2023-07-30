package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.R;
import com.example.appdemo.models.TinNhan;

import java.util.ArrayList;

public class FragmentChat extends Fragment {
    ArrayList<TinNhan> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_chat,null);
        list = new ArrayList<>();
        list.add(new TinNhan("NV1","helo","AD"));
        list.add(new TinNhan("AD","helo","NV1"));
        list.add(new TinNhan("AD","co gi khong","NV1"));
        return view;
    }
}