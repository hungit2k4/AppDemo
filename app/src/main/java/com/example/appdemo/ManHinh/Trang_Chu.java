package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appdemo.Apdapter.ChucNangAdapter;
import com.example.appdemo.fragment.FragmentChat;
import com.example.appdemo.fragment.FragmentHome;
import com.example.appdemo.R;
import com.example.appdemo.fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trang_Chu extends AppCompatActivity {
    TextView btnThongBao, tvDate, tvHello;
    ImageView imageAvatar;
    RelativeLayout rltWelcome;
    FrameLayout frLayout;
    BottomNavigationView bottomNavigationView;
    private String inFullName, user, pass, url_avatar, url_background;
    private int id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);


        rltWelcome = findViewById(R.id.rltWelcome);
        imageAvatar = findViewById(R.id.imageAvatar);
        btnThongBao = findViewById(R.id.btnThongBao);
        tvDate = findViewById(R.id.tvDate);
        tvHello = findViewById(R.id.tvHello);

        Date date = new Date();// Định dạng để lấy thứ, ngày và tháng

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");// Lấy thông tin về thứ, ngày và tháng
        String dateTimeInfo = sdf.format(date);
        tvDate.setText(dateTimeInfo);

        Intent intent = getIntent();

        id = intent.getIntExtra("id", -1);
        inFullName = intent.getStringExtra("fullName");
        user = intent.getStringExtra("user");
        pass = intent.getStringExtra("pass");
        url_avatar = intent.getStringExtra("urlAvatar");
        url_background = intent.getStringExtra("urlBackground");

        if (!url_avatar.isEmpty()) {
            Picasso.get().load(url_avatar).into(imageAvatar);
        }

        tvHello.setText("Xin chào, " + inFullName);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Fragment fragment = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentBottom = null;
                if (item.getItemId() == R.id.bottom_home) {
                    rltWelcome.setVisibility(View.VISIBLE);
                    fragmentBottom = new FragmentHome();
                } else if (item.getItemId() == R.id.mChat) {
                    rltWelcome.setVisibility(View.VISIBLE);
                    fragmentBottom = new FragmentChat();
                } else if (item.getItemId() == R.id.bottom_profile) {
                    rltWelcome.setVisibility(View.GONE);
                    fragmentBottom = new FragmentProfile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragmentBottom).commit();
                return true;
            }
        });

        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Chu.this, Infor_account.class);
                startActivity(intent);
            }
        });

        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Chu.this, Infor_account.class);
                startActivity(intent);
            }
        });
    }

    public String getFullName() {
        String fullName = inFullName;
        return fullName;
    }

    public int getIdOfAccount() {
        int putId = id;
        return putId;
    }

    public String getUserOfAccount() {
        String putUser = user;
        return putUser;
    }

    public String getPassOfAccount() {
        String putPass = pass;
        return putPass;
    }

    public String getUrl_avatar() {
        String putUrl_Avatar = url_avatar;
        return putUrl_Avatar;
    }

    public String getUrl_background() {
        String putUrl_Background = url_background;
        return putUrl_Background;
    }

}

