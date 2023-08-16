package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.Apdapter.XinNghi_Adapter;
import com.example.appdemo.R;
import com.example.appdemo.models.ChamCong;
import com.example.appdemo.models.Notify;
import com.example.appdemo.models.XinNghi;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    private Boolean checkDate;
    private ImageView backButton;
    private RecyclerView rcDangKiNghi;
    private LinearLayout lyOffNV;
    Integer years, months, dayOfMonths, yeare, monthe, dayOfMonthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangkynghi2);
        reasonEditText = findViewById(R.id.reasonEditText);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        btn_DangKy = findViewById(R.id.btn_DangKy);
        backButton = findViewById(R.id.backButton);
        rcDangKiNghi = findViewById(R.id.rcDangKiNghi);
        lyOffNV = findViewById(R.id.lyOffNV);

        rcDangKiNghi.setHasFixedSize(true);
        rcDangKiNghi.setLayoutManager(new LinearLayoutManager(DangKyNghi.this));

        client = new OkHttpClient();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        DatabaseReference databaseRef_ThongBao = FirebaseDatabase.getInstance().getReference("Thông báo");
        DatabaseReference databaseRef_NgayCong = FirebaseDatabase.getInstance().getReference("Ngày công");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("đăng ký nghỉ");

        String check = "^admin\\w{0,}";
        boolean checkAccount = LoginActivity.idGui.matches(check);

        if (checkAccount) {
            lyOffNV.setVisibility(View.GONE);
        }

        FirebaseRecyclerOptions<XinNghi> options = new FirebaseRecyclerOptions.Builder<XinNghi>().setQuery(databaseReference, XinNghi.class).build();
        DatabaseReference finalDatabaseReference = databaseReference;
        FirebaseRecyclerAdapter<XinNghi, XinNghi_Adapter.ViewHolder> adapter = new FirebaseRecyclerAdapter<XinNghi, XinNghi_Adapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull XinNghi_Adapter.ViewHolder holder, int position, @NonNull XinNghi model) {

                String key = getRef(position).getKey();
                finalDatabaseReference.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild("maNV")) {


                            String maNV = snapshot.child("maNV").getValue().toString();
                            String ngayStart = snapshot.child("dateStart").getValue().toString();
                            String ngayEnd = snapshot.child("dateEnd").getValue().toString();
                            String liDo = snapshot.child("liDo").getValue().toString();

                            holder.tvMaNvOff.setText(maNV);
                            holder.tvNgayStart.setText(ngayStart);
                            holder.tvNgayEnd.setText(ngayEnd);
                            holder.tvLiDo.setText(liDo);

                            if (!checkAccount) {
                                holder.ibtnAccept.setVisibility(View.GONE);
                                holder.ibtnCancel.setVisibility(View.GONE);

                                int status = Integer.parseInt(snapshot.child("status").getValue().toString());
                                if (status == 0) {
                                    holder.tvStatus.setText("Đang đợi duyệt");
                                    holder.tvStatus.setTextColor(Color.BLUE);
                                } else if (status == 1) {
                                    holder.tvStatus.setText("Đã được duyệt");
                                    holder.tvStatus.setTextColor(Color.GREEN);
                                } else if (status == 2) {
                                    holder.tvStatus.setText("Không được duyệt");
                                    holder.tvStatus.setTextColor(Color.RED);
                                }
                            } else {


                                int status = Integer.parseInt(snapshot.child("status").getValue().toString());
                                if (status == 0) {
                                    holder.ibtnAccept.setVisibility(View.VISIBLE);
                                    holder.ibtnCancel.setVisibility(View.VISIBLE);
                                    holder.tvStatus.setVisibility(View.GONE);
                                } else {
                                    holder.ibtnAccept.setVisibility(View.GONE);
                                    holder.ibtnCancel.setVisibility(View.GONE);
                                    holder.tvStatus.setVisibility(View.VISIBLE);
                                    holder.tvStatus.setText("Đã phản hồi đơn");
                                }

                                XinNghi xinNghi = new XinNghi();
                                holder.ibtnAccept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        xinNghi.setMaNV(maNV);
                                        xinNghi.setDateStart(ngayStart);
                                        xinNghi.setDateEnd(ngayEnd);
                                        xinNghi.setLiDo(liDo);
                                        xinNghi.setStatus("1");

                                        holder.ibtnAccept.setVisibility(View.GONE);
                                        holder.ibtnCancel.setVisibility(View.GONE);
                                        holder.tvStatus.setVisibility(View.VISIBLE);
                                        holder.tvStatus.setText("Đã phản hồi đơn");
                                        databaseReference.child(maNV).setValue(xinNghi);

                                        Notify notify = new Notify();

                                        notify.setContentNotify("Đơn xin phép của bạn đã được duyệt");
                                        databaseRef_ThongBao.child(maNV).setValue(notify);

                                    }
                                });

                                holder.ibtnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        xinNghi.setMaNV(maNV);
                                        xinNghi.setDateStart(ngayStart);
                                        xinNghi.setDateEnd(ngayEnd);
                                        xinNghi.setLiDo(liDo);
                                        xinNghi.setStatus("2");

                                        holder.ibtnAccept.setVisibility(View.GONE);
                                        holder.ibtnCancel.setVisibility(View.GONE);
                                        holder.tvStatus.setVisibility(View.VISIBLE);
                                        holder.tvStatus.setText("Đã phản hồi đơn");
                                        databaseReference.child(maNV).setValue(xinNghi);

                                        Notify notify = new Notify();

                                        notify.setContentNotify("Đơn xin phép của bạn không được duyệt");
                                        databaseRef_ThongBao.child(maNV).setValue(notify);
                                    }
                                });

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @NonNull
            @Override
            public XinNghi_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xinnghi, parent, false);
                return new XinNghi_Adapter.ViewHolder(view);
            }

        };

        rcDangKiNghi.setAdapter(adapter);
        adapter.startListening();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String liDo = reasonEditText.getText().toString();

                if (liDo.isEmpty()) {
                    reasonEditText.setError("Trống");
                    reasonEditText.requestFocus();
                } else if (dateStart == null) {
                    Toast.makeText(DangKyNghi.this, "Chưa chọn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                } else if (dateEnd == null)
                    Toast.makeText(DangKyNghi.this, "Chưa chọn ngày kết thúc", Toast.LENGTH_SHORT).show();
                else if (checkDate) {
                    Toast.makeText(DangKyNghi.this, "Ngày bắt đầu or kết thúc không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {

                    XinNghi xinNghi = new XinNghi();
                    xinNghi.setMaNV(LoginActivity.idGui);
                    xinNghi.setDateStart(dateStart);
                    xinNghi.setDateEnd(dateEnd);
                    xinNghi.setLiDo(liDo);
                    xinNghi.setStatus("0");
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("đăng ký nghỉ");
                    databaseRef.child(LoginActivity.idGui).setValue(xinNghi);

                    databaseRef_NgayCong.child(LoginActivity.idGui).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            ChamCong chamCong = new ChamCong();

                            int total = dayOfMonthe - dayOfMonths;

                            if (snapshot.hasChild("ngayLam")) {

                                chamCong = snapshot.getValue(ChamCong.class);

                                chamCong.setNgayLam(chamCong.getNgayLam());
                                if (chamCong.getNgayNghi() == 0) {
                                    chamCong.setNgayNghi(total);
                                } else {
                                    chamCong.setNgayNghi(chamCong.getNgayNghi() + total);
                                }
                            }

                            databaseRef_NgayCong.child(LoginActivity.idGui).setValue(chamCong).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    databaseRef_ThongBao.child(LoginActivity.idGui).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Notify notify = new Notify();

                                            notify.setContentNotify("Đơn xin đăng ký nghỉ phép từ: " + LoginActivity.idGui);
                                            databaseRef_ThongBao.child(LoginActivity.idGui).setValue(notify).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    reasonEditText.setText("");
                                                    startDateTextView.setText("");
                                                    endDateTextView.setText("");
                                                    Toast.makeText(DangKyNghi.this, "Đã nộp đơn đăng ký", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            });

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
                    dayOfMonths = dayOfMonth1;
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
                    dayOfMonthe = dayOfMonth1;
                    calendar.set(year1, monthOfYear, dayOfMonth1);

                    endDateTextView.setText(dateFormatter.format(calendar.getTime()));

                    if (year1 < years || (year1 == years && monthOfYear < months) || (year1 == years && monthOfYear == months && dayOfMonth1 <= dayOfMonths)) {
                        checkDate = true;
                    } else {
                        checkDate = false;
                    }

                    dateEnd = dayOfMonth1 + "-" + (monthOfYear + 1) + "-" + year1;

                }, yeare, monthe, dayOfMonthe);

        datePickerDialog.show();
    }

}