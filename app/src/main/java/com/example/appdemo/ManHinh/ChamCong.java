package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.appdemo.R;


public class ChamCong extends AppCompatActivity {

    private CalendarAdapter calendarAdapter;
    private RecyclerView recyclerView;


    // CalendarDay.java



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        recyclerView = findViewById(R.id.recyclerViewCalendar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        List<CalendarDay> calendarDays = createCalendarData(); // Replace this with your own calendar data
        calendarAdapter = new CalendarAdapter(calendarDays);
        recyclerView.setAdapter(calendarAdapter);

    }

    private List<CalendarDay> createCalendarData() {
        // Replace this with your logic to generate the calendar data
        List<CalendarDay> calendarDays = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            calendarDays.add(new CalendarDay(String.valueOf(i)));
        }
        return calendarDays;
    }

    public class CalendarDay {
        private String day;

        public CalendarDay(String day) {
            this.day = day;
        }

        public String getDay() {
            return day;
        }
    }

    public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

        private List<CalendarDay> calendarDays;

        public CalendarAdapter(List<CalendarDay> calendarDays) {
            this.calendarDays = calendarDays;
        }

        @NonNull
        @Override
        public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
            return new CalendarViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
            CalendarDay day = calendarDays.get(position);
            holder.textViewDay.setText(day.getDay());
        }

        @Override
        public int getItemCount() {
            return calendarDays.size();
        }

        public class CalendarViewHolder extends RecyclerView.ViewHolder {
            TextView textViewDay;

            public CalendarViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewDay = itemView.findViewById(R.id.textViewDay);
            }
        }
    }

}