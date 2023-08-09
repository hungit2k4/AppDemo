package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThemNV extends AppCompatActivity {
    EditText edtMaNV,edtTenNV2,edtNgaySinh,edtSoDT,edtDiaChi,edtEmail;
    RadioButton rdoNam,rdoNu;
    Button btnHuy,btnLuuNV;
   public Boolean kt=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nv);
        edtMaNV=findViewById(R.id.edtMaNV);
        edtTenNV2=findViewById(R.id.edtTenNV2);
        edtNgaySinh=findViewById(R.id.edtNgaySinh);
        edtSoDT=findViewById(R.id.edtSoDT);
        edtDiaChi=findViewById(R.id.edtDiaChi);
        edtEmail=findViewById(R.id.edtEmail);
        rdoNam=findViewById(R.id.rdoNam);
        rdoNu=findViewById(R.id.rdoNu);
        btnHuy=findViewById(R.id.btnHuy);
        btnLuuNV=findViewById(R.id.btnLuuNV);

        btnLuuNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMaNV.getText().toString();
                String ten = edtTenNV2.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();
                String sDT = edtSoDT.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String email = edtEmail.getText().toString();
                Integer gioiTinh=(rdoNam.isChecked())?1:0;
                ktNhapTT(ma,ten,ngaySinh,sDT,diaChi,email);
                if (!kt){
                    NhanVien nv =new NhanVien(ma,ten,ngaySinh,gioiTinh,Integer.parseInt(sDT),diaChi,email);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
                    databaseReference.child(ma).setValue(nv, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            finish();
                        }
                    });
                }


            }
        });

    }
    public Boolean ktNhapTT(String ma,String ten,String ngaySinh,String sDT,String diaChi,String email){
        String check;
        if (ma.isEmpty()) {
            edtMaNV.setError("Chưa nhập mã nhân viên");
            edtMaNV.requestFocus();
            kt=true;
            return kt;
        }
        check="^nv\\d+";
        if (!ma.matches(check)){
            edtMaNV.setError("Mã nhân viên bắt đầu bằng nv");
            edtMaNV.requestFocus();
            kt=true;
            return kt;
        }
        checkMaNVForAccount(ma, new checkCallBack() {
            @Override
            public void onResult(Boolean result) {
                if (result){
                    edtMaNV.setError("Mã "+ma+" chưa tồn tại,cấp tài khoản trước khi nhập thông tin");
                    edtMaNV.requestFocus();
                    kt=true;
                }
            }
        });
        checkMaNVForThongTinNV(ma, new checkCallBack() {
            @Override
            public void onResult(Boolean result) {
                if (result){
                    edtMaNV.setError("Mã "+ma+" đã có thông tin, hãy chuyển sang chỉnh sửa");
                    edtMaNV.requestFocus();
                    kt=true;
                }
            }
        });
        if (ten.isEmpty()) {
            edtTenNV2.setError("Chưa nhập tên nhân viên");
            edtTenNV2.requestFocus();
            kt=true;
            return kt;
        }
        if (ngaySinh.isEmpty()){
            edtNgaySinh.setError("Chưa nhập ngày sinh nhân viên");
            edtNgaySinh.requestFocus();
            kt=true;
            return kt;
        }
        check="^(0[1-9]|[1-2]\\d|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$";
        if (!ngaySinh.matches(check)){
            edtNgaySinh.setError("Ngày sinh có định dạng dd/mm/yyyy");
            edtNgaySinh.requestFocus();
            kt=true;
            return kt;
        }
        if (checkDate(ngaySinh)){
            edtNgaySinh.setError("Ngày sinh không hợp lệ");
            edtNgaySinh.requestFocus();
            return kt;
        }
        if (edtSoDT.getText().toString().length()==0){
            edtSoDT.setError("Chưa nhập số điện thoại nhân viên");
            edtSoDT.requestFocus();
            kt=true;
            return kt;
        }
        check="(\\+?84|0)\\d{9,10}";
        if (!sDT.matches(check)){
            edtSoDT.setError("Số điện thoại không hợp lệ");
            edtSoDT.requestFocus();
            kt=true;
            return kt;
        }
       if (diaChi.isEmpty()){
           edtDiaChi.setError("Chưa nhập địa chỉ");
           edtDiaChi.requestFocus();
           kt=true;
           return kt;
       }
       if (email.isEmpty()){
           edtEmail.setError("Chưa nhập email");
           edtEmail.requestFocus();
           kt=true;
           return kt;
       }
       check="^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(check)){
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            kt=true;
            return kt;
        }
        return kt;
    }
    public Boolean checkDate(String date){
        Boolean checkYear=false;
        String[] parts = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (year%4==0&& year%100!=0||year%400==0)
            checkYear=true;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if(day>31){
                    kt=true;
                }
                break;
            case 4: case 6: case 9:case 11:
                if (day>30){
                    kt=true;
                }
                break;
            case 2:
            if (checkYear==true && day>29){
                kt=true;
            }
            if (checkYear==false&&day>28){
                kt=true;
            }
            break;
        }
        kt=false;
        return kt;
    }
    public interface checkCallBack{
        void  onResult(Boolean result);
    }
    public void checkMaNVForAccount(String maNV,checkCallBack checkCallBack){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Account");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                String key = dataSnapshot.getRef().getKey();
                if (key.equals(maNV)){
                    kt=false;
                    break;
                }
                else kt=true;
             }
             checkCallBack.onResult(kt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void checkMaNVForThongTinNV(String maNV , checkCallBack checkCallBack){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String key = dataSnapshot.getRef().getKey();
                    if (key.equals(maNV)){
                        kt=true;
                        break;
                    }
                    else kt=false;
                }
                checkCallBack.onResult(kt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}