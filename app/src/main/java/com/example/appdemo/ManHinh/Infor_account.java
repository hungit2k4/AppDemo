package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.models.NhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Infor_account extends AppCompatActivity {
    Button btnLuuAcc;
    ImageView btnBack,imgInfor;
    EditText edtNameAcc,edtSDT,edtEmailAcc;
    RadioButton rdoNamInfor,rdoNuInfor;
    NhanVien nhanVien= new NhanVien();
    Boolean check;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_account);
        btnLuuAcc=findViewById(R.id.btnLuuAcc);
        btnBack = findViewById(R.id.btnBack);
        rdoNamInfor = findViewById(R.id.rdoNamInfor);
        rdoNuInfor = findViewById(R.id.rdoNuInfor);
        edtNameAcc = findViewById(R.id.edtNameAcc);
        edtSDT = findViewById(R.id.edtSDT);
        edtEmailAcc = findViewById(R.id.edtEmailAcc);
        imgInfor = findViewById(R.id.imgInfor);
        String id= LoginActivity.idGui;
        if (Trang_Chu.key!=null){
            Picasso.get().load(Trang_Chu.old_url_avatar).into(imgInfor, new Callback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(Infor_account.this, "hfvsjdh", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    String key= dataSnapshot.getRef().getKey();
                    if (key.equals(id)){
                        nhanVien = dataSnapshot.getValue(NhanVien.class);
                        check=true;
                        break;
                    }
                    else {
                        check=false;
                    }
                }
                if (check){
                    if (nhanVien.getGioiTinh()==1){
                        rdoNamInfor.setChecked(true);
                    }else rdoNuInfor.setChecked(true);
                    edtNameAcc.setText(nhanVien.getTen());
                    edtSDT.setText(nhanVien.getSoDT());
                    edtEmailAcc.setText(nhanVien.getEmail());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnLuuAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfor();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void updateInfor(){
        String REGEX_EMAIL = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (edtNameAcc.getText().toString().isEmpty()){
            edtNameAcc.setError("Trống");
            edtNameAcc.requestFocus();
        }else if (edtEmailAcc.getText().toString().isEmpty()){
            edtEmailAcc.setError("Trống");
            edtEmailAcc.requestFocus();
        }else if (!edtEmailAcc.getText().toString().matches(REGEX_EMAIL)){
            edtEmailAcc.setError("Email không hợp lệ");
            edtEmailAcc.requestFocus();
        }else {
        nhanVien.setGioiTinh(rdoNamInfor.isChecked()?1:0);
        nhanVien.setTen(edtNameAcc.getText().toString());
        nhanVien.setEmail(edtEmailAcc.getText().toString());
        databaseReference.child(nhanVien.getMaNV()).setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               finish();
            }
        });
        }
    }

}