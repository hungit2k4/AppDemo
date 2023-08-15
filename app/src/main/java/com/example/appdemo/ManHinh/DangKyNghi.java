package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class DangKyNghi extends AppCompatActivity {
    private EditText reasonEditText;
    private TextView startDateTextView, endDateTextView;
    private OkHttpClient client;
    private WebSocket webSocket;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangkynghi2);
        reasonEditText = findViewById(R.id.reasonEditText);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);

        client = new OkHttpClient();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    }
    public void showStartDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year1, monthOfYear, dayOfMonth1) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth1);
                    startDateTextView.setText(dateFormatter.format(calendar.getTime()));
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    public void showEndDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year1, monthOfYear, dayOfMonth1) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth1);
                    endDateTextView.setText(dateFormatter.format(calendar.getTime()));
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
    }