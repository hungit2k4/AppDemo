package com.example.appdemo.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdemo.ManHinh.Trang_Chu;
import com.example.appdemo.R;
import com.example.appdemo.models.Account;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Account extends Fragment {

    EditText edtIdNV, edtTenNV, edtMatKhau;
    Button btnOkCreate, btnExit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_create_account, container, false);

        edtIdNV = view.findViewById(R.id.edtIdNV);
        edtTenNV = view.findViewById(R.id.edtTenNV);
        edtMatKhau = view.findViewById(R.id.edtMatKhau);
        btnOkCreate = view.findViewById(R.id.btnOkCreate);
        btnExit = view.findViewById(R.id.btnExit);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment =new FragmentHome();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frLayout,fragment).commit();
                Trang_Chu.rltWelcome.setVisibility(View.VISIBLE);
            }
        });
        btnOkCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtIdNV.getText().toString();
                String tenNV = edtTenNV.getText().toString();
                String pass = edtMatKhau.getText().toString();

                if(id.isEmpty()){
                    edtIdNV.setError("Chưa nhập Id");
                    edtIdNV.requestFocus();
                }else if (tenNV.isEmpty()){
                    edtTenNV.setError("Hãy nhập tên NV");
                    edtTenNV.requestFocus();
                } else if (pass.isEmpty()) {
                    edtMatKhau.setError("Mật khẩu trống");
                    edtMatKhau.requestFocus();
                }else {
                    Account account = new Account();

                    account.setUsername(id);
                    account.setFullName(tenNV);
                    account.setPassword(pass);
                    databaseRef.child(id).setValue(account).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            edtIdNV.setText("");
                            edtTenNV.setText("");
                            edtMatKhau.setText("");
                            Toast.makeText(getContext(), "Cấp tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}