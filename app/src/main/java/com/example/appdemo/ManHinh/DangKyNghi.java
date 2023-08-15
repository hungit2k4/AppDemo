package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.models.ChamCong;
import com.example.appdemo.models.XinNghi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String dateStart, dateEnd;
    private Button btn_DangKy;
    private Boolean check;
    Integer years, months, dayOfMonths, yeare, monthe, dayOfMonthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangkynghi2);
        reasonEditText = findViewById(R.id.reasonEditText);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        btn_DangKy = findViewById(R.id.btn_DangKy);
        client = new OkHttpClient();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        DatabaseReference databaseRef_ThongBao = FirebaseDatabase.getInstance().getReference("Thông báo");
        DatabaseReference databaseRef_NgayCong = FirebaseDatabase.getInstance().getReference("Ngày công");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("đăng ký nghỉ");

        btn_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String liDo= reasonEditText.getText().toString();
                if (liDo.isEmpty()) {
                    reasonEditText.setError("Trống");
                    reasonEditText.requestFocus();
                } else if (dateStart == null) {
                    Toast.makeText(DangKyNghi.this, "Chưa chọn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                } else if (dateEnd == null)
                    Toast.makeText(DangKyNghi.this, "Chưa chọn ngày kết thúc", Toast.LENGTH_SHORT).show();
                else if (check==true) {
                    Toast.makeText(DangKyNghi.this, "Ngày bắt đầu or kết thúc không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    XinNghi xinNghi= new XinNghi(LoginActivity.idGui,liDo,dateStart,dateEnd);

                    databaseReference.child(LoginActivity.idGui).setValue(xinNghi);

                    databaseRef_NgayCong.child(LoginActivity.idGui).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            ChamCong chamCong = new ChamCong();

                            if(snapshot.hasChild("ngayNghi")){
                                chamCong = snapshot.getValue(ChamCong.class);

                                chamCong.setNgayLam(chamCong.getNgayLam());
                                if(chamCong.getNgayNghi() == 0){
                                    chamCong.setNgayNghi(1);
                                }else {
                                    chamCong.setNgayNghi(chamCong.getNgayNghi() + 1);
                                }
                            }

                            databaseRef_NgayCong.child(LoginActivity.idGui).setValue(chamCong);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void showStartDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();

        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH);
        dayOfMonths = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year1, monthOfYear, dayOfMonth1) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth1);
                    startDateTextView.setText(dateFormatter.format(calendar.getTime()));

                    dateStart = dayOfMonth1 + "-" + (monthOfYear + 1) + "-" + year1;

                }, years, months, dayOfMonths);

        datePickerDialog.show();
    }

    public void showEndDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        yeare = calendar.get(Calendar.YEAR);
        monthe = calendar.get(Calendar.MONTH);
        dayOfMonthe = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year1, monthOfYear, dayOfMonth1) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth1);
                    endDateTextView.setText(dateFormatter.format(calendar.getTime()));

                    if (year1<years || (year1 == years && monthOfYear <months) || (year1 == years && monthOfYear == months && dayOfMonth1 <=dayOfMonths)) {
                        check = true;
                    } else {
                        check = false;
                    }

                    dateEnd = dayOfMonth1 + "-" + (monthOfYear + 1) + "-" + year1;

                }, yeare, monthe, dayOfMonthe);

        datePickerDialog.show();
    }

}