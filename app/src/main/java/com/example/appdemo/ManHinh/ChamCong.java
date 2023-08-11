package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.example.appdemo.R;


public class ChamCong extends AppCompatActivity {

    private CalendarView calendarView;
    private Button btnAddChamCong;
    private RecyclerView rcChamCong;
    private long selectedDateMillis = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        calendarView = findViewById(R.id.calendarView);
        btnAddChamCong = findViewById(R.id.btnAddChamCong);
        rcChamCong = findViewById(R.id.rcChamCong);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                Toast.makeText(ChamCong.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

                // Lưu lại ngày được chọn
                selectedDateMillis = view.getDate();
                // Gọi hàm để thiết lập màu nền cho ngày được chọn
                updateSelectedDateBackground();
            }
        });

        btnAddChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm mã xử lý khi nhấn nút "Thêm Chấm Công" ở đây
            }
        });
    }

    private void updateSelectedDateBackground() {
        // Xóa màu nền của ngày trước đó (nếu có)
        if (selectedDateMillis != -1) {
            calendarView.setDate(selectedDateMillis);
        }

        // Thiết lập màu nền cho ngày được chọn
        calendarView.setDate(selectedDateMillis, true, true);
        calendarView.setBackgroundColor(Color.BLUE); // Thay đổi màu nền tại đây
    }
}

