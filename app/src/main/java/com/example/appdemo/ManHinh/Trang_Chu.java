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
import android.widget.TextView;

import com.example.appdemo.fragment.FragmentHome;
import com.example.appdemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trang_Chu extends AppCompatActivity {
    TextView btnAccount,btnThongBao,tvDate;

    FrameLayout frLayout;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trang_chu);
        btnAccount = findViewById(R.id.btnAccount);
        btnThongBao = findViewById(R.id.btnThongBao);
        tvDate = findViewById(R.id.tvDate);
        Date date = new Date();
        // Định dạng để lấy thứ, ngày và tháng
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");

        // Lấy thông tin về thứ, ngày và tháng
        String dateTimeInfo = sdf.format(date);
        tvDate.setText(dateTimeInfo);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Fragment fragment = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentBottom =null;
                if(item.getItemId() == R.id.bottom_home){
                    fragmentBottom = new FragmentHome();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragmentBottom).commit();
                return true;
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
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
}
//import java.util.Date;
//        import java.text.SimpleDateFormat;
//
//public class CurrentDateTimeExample {
//    public static void main(String[] args) {
//        // Lấy thời gian hiện tại
//        Date date = new Date();
//
//        // Định dạng để lấy thứ, ngày, tháng và năm
//        SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat("EEEE"); // Lấy thứ trong tuần
//        SimpleDateFormat sdfDayOfMonth = new SimpleDateFormat("dd"); // Lấy ngày trong tháng
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM"); // Lấy tháng
//        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy"); // Lấy năm
//
//        // Lấy thông tin về thứ, ngày, tháng và năm
//        String dayOfWeek = sdfDayOfWeek.format(date);
//        String dayOfMonth = sdfDayOfMonth.format(date);
//        String month = sdfMonth.format(date);
//        String year = sdfYear.format(date);
//
//        // In thông tin lên màn hình
//        System.out.println("Thứ: " + dayOfWeek);
//        System.out.println("Ngày: " + dayOfMonth);
//        System.out.println("Tháng: " + month);
//        System.out.println("Năm: " + year);
//    }
//}
