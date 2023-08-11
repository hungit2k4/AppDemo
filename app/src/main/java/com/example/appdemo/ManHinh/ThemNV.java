package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.models.Account;
import com.example.appdemo.models.NhanVien;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThemNV extends AppCompatActivity {
    EditText edtMaNV, edtTenNV2, edtNgaySinh, edtSoDT, edtDiaChi, edtEmail;
    RadioButton rdoNam, rdoNu;
    Button btnHuy, btnLuuNV, btnChinhSuaNV;
    Boolean accept = false;
    private String maNv, tenNv, ngaySinh, sDT, diaChi, email, neww;
    private Integer gioiTinh;
    public Boolean kt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nv);

        edtMaNV = findViewById(R.id.edtMaNV);
        edtTenNV2 = findViewById(R.id.edtTenNV2);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtSoDT = findViewById(R.id.edtSoDT);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtEmail = findViewById(R.id.edtEmail);

        rdoNam = findViewById(R.id.rdoNam);
        rdoNu = findViewById(R.id.rdoNu);

        btnHuy = findViewById(R.id.btnHuy);
        btnLuuNV = findViewById(R.id.btnLuuNV);
        btnChinhSuaNV = findViewById(R.id.btnChinhSuaNV);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
        DatabaseReference databaseRefAcount = FirebaseDatabase.getInstance().getReference("Account");


        btnLuuNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maNv = edtMaNV.getText().toString();
                tenNv = edtTenNV2.getText().toString();
                ngaySinh = edtNgaySinh.getText().toString();
                sDT = edtSoDT.getText().toString();
                diaChi = edtDiaChi.getText().toString();
                email = edtEmail.getText().toString();
                gioiTinh = (rdoNam.isChecked()) ? 1 : 0;

                String REGEX_MA_NV = "^nv\\d+";
                String REGEX_BIRTDAY = "^(0[1-9]|[1-2]\\d|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$";
                String REGEX_PHONE_NUMBER = "(\\+?84|0)\\d{9,10}";
                String REGEX_EMAIL = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";


                databaseRefAcount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Boolean checkAccount = false;

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()
                        ) {

                            String key = dataSnapshot.getRef().getKey();

                            if (key.equals(maNv)) {
                                checkAccount = true;
                                break;

                            }

                        }

                        if (checkAccount == false) {
                            edtMaNV.setError("Nhân viên chưa có tài khoản");
                            edtMaNV.requestFocus();
                        } else {
                            if (maNv.isEmpty()) {
                                edtMaNV.setError("Trống");
                                edtMaNV.requestFocus();
                            } else if (tenNv.isEmpty()) {
                                edtTenNV2.setError("Trống");
                                edtTenNV2.requestFocus();
                            } else if (ngaySinh.isEmpty()) {
                                edtNgaySinh.setError("Trống");
                                edtNgaySinh.requestFocus();
                            } else if (sDT.isEmpty()) {
                                edtSoDT.setError("Trống");
                                edtSoDT.requestFocus();
                            } else if (diaChi.isEmpty()) {
                                edtDiaChi.setError("Trống");
                                edtDiaChi.requestFocus();
                            } else if (email.isEmpty()) {
                                edtEmail.setError("Trống");
                                edtEmail.requestFocus();
                            } else if (!maNv.matches(REGEX_MA_NV)) {
                                edtMaNV.setError("Mã NV không hợp lệ");
                                edtMaNV.requestFocus();
                            } else if (!ngaySinh.matches(REGEX_BIRTDAY)) {
                                edtNgaySinh.setError("Ngày sinh không đúng định dạng");
                                edtNgaySinh.requestFocus();
                            } else if (!sDT.matches(REGEX_PHONE_NUMBER)) {
                                edtSoDT.setError("Số điện thoại không đúng");
                                edtSoDT.requestFocus();
                            } else if (!email.matches(REGEX_EMAIL)) {
                                edtEmail.setError("Email không đúng định dạng");
                                edtEmail.requestFocus();
                            } else {



                                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Boolean checkTrungMaNV = false;
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            String key = dataSnapshot.getRef().getKey();

                                            if (key.equals(maNv)) {
                                                checkTrungMaNV = true;
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ThemNV.this);
                                                builder.setTitle("Thông báo");
                                                builder.setMessage("Nhân viên đã tồn tại nên chúng tôi sẽ chuyển sang dạng chỉnh sửa");
                                                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        accept = true;
                                                        btnLuuNV.setVisibility(View.GONE);
                                                        btnChinhSuaNV.setVisibility(View.VISIBLE);
                                                        dialog.dismiss();
                                                    }
                                                });
                                                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                                AlertDialog alertDialog = builder.create();
                                                alertDialog.show();

                                                edtMaNV.setError("Mã nv đã tồn tại");
                                                edtMaNV.requestFocus();
                                                break;
                                            }
                                            if(checkTrungMaNV == false) {
                                                NhanVien nhanVien = new NhanVien();

                                                nhanVien.setTen(tenNv);
                                                nhanVien.setEmail(email);
                                                nhanVien.setDiaChi(diaChi);
                                                nhanVien.setMaNV(maNv);
                                                nhanVien.setGioiTinh(gioiTinh);
                                                nhanVien.setNgaySinh(ngaySinh);
                                                nhanVien.setSoDT(sDT);

                                                databaseRef.child(maNv).setValue(nhanVien).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        Toast.makeText(ThemNV.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                                                        finish();
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
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        btnChinhSuaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept = true) {

                    NhanVien nhanVien = new NhanVien();

                    nhanVien.setTen(tenNv);
                    nhanVien.setEmail(email);
                    nhanVien.setDiaChi(diaChi);
                    nhanVien.setMaNV(maNv);
                    nhanVien.setGioiTinh(gioiTinh);
                    nhanVien.setNgaySinh(ngaySinh);
                    nhanVien.setSoDT(sDT);

                    btnChinhSuaNV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseRef.child(maNv).setValue(nhanVien).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    btnLuuNV.setVisibility(View.VISIBLE);
                                    btnChinhSuaNV.setVisibility(View.GONE);
                                    Toast.makeText(ThemNV.this, "Đã chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    });

                }
            }
        });

    }

    public Boolean checkDate(String date) {
        Boolean checkYear = false;
        String[] parts = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            checkYear = true;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day > 31) {
                    kt = true;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 30) {
                    kt = true;
                }
                break;
            case 2:
                if (checkYear == true && day > 29) {
                    kt = true;
                }
                if (checkYear == false && day > 28) {
                    kt = true;
                }
                break;
        }
        kt = false;
        return kt;
    }

    public interface checkCallBack {
        void onResult(Boolean result);
    }

    public void checkMaNVForAccount(String maNV, checkCallBack checkCallBack) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Account");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getRef().getKey();
                    if (key.equals(maNV)) {
                        kt = false;
                        break;
                    } else kt = true;
                }
                checkCallBack.onResult(kt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkMaNVForThongTinNV(String maNV, checkCallBack checkCallBack) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getRef().getKey();
                    if (key.equals(maNV)) {
                        kt = true;
                        break;
                    } else kt = false;
                }
                checkCallBack.onResult(kt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}