package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private String maNv, tenNv, ngaySinh, sDT, diaChi, email;
    private Integer gioiTinh;
    private NhanVien nhanVien= new NhanVien();
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
        Intent intent = getIntent();
        Integer c = intent.getIntExtra("check", 0);
        String maNVFromAdapter= intent.getStringExtra("manv");
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
        DatabaseReference databaseRefAcount = FirebaseDatabase.getInstance().getReference("Account");
        if (c == 1) {
        //sua thong tin
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key= dataSnapshot.getRef().getKey();
                        if (maNVFromAdapter.equals(key)){
                            nhanVien = dataSnapshot.getValue(NhanVien.class);
                            break;
                        }
                    }
                    edtMaNV.setEnabled(false);
                    edtMaNV.setText(maNVFromAdapter);
                    edtTenNV2.setText(nhanVien.getTen());
                    edtNgaySinh.setText(nhanVien.getNgaySinh());
                    if (nhanVien.getGioiTinh()==1) rdoNam.isChecked();
                    else rdoNu.isChecked();
                    edtSoDT.setText(nhanVien.getSoDT());
                    edtDiaChi.setText(nhanVien.getDiaChi());
                    edtEmail.setText(nhanVien.getEmail());
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
                            nhanVien = new NhanVien(maNv,tenNv,ngaySinh,gioiTinh,sDT,diaChi,email);
                            databaseRef.child(maNVFromAdapter).setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    finish();
                                }
                            });
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            //them thong tin
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

                            Boolean checkAccount = true;

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {

                                String key = dataSnapshot.getRef().getKey();

                                if (key.equals(maNv)) {
                                    checkAccount = true;
                                    break;

                                } else {
                                    checkAccount = false;
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
                                        Boolean checkTrungManvforThongTin = true;

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                String key = dataSnapshot.getRef().getKey();

                                                if (key.equals(maNv)) {
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
                                                    checkTrungManvforThongTin = true;
                                                    break;
                                                } else checkTrungManvforThongTin = false;
                                            }
                                            if (checkTrungManvforThongTin == false) {
                                                NhanVien nhanVien = new NhanVien(maNv, tenNv, ngaySinh, gioiTinh, sDT, diaChi, email);
                                                databaseRef.child(maNv).setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        finish();
                                                    }
                                                });
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
    }
}