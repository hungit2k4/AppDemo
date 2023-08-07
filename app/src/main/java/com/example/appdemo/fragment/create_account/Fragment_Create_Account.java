package com.example.appdemo.fragment.create_account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.ManHinh.Trang_Chu;
import com.example.appdemo.R;
import com.example.appdemo.fragment.FragmentHome;
import com.example.appdemo.models.Account;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Create_Account extends Fragment {

    protected static EditText edtIdNV, edtTenNV, edtMatKhau;
    protected static TextView tv_showOrHide;
    protected static LinearLayout linear_create_account;
    protected static Button btnOkCreate, btnExit;
    private RecyclerView rcView_Account;
    private FirebaseAdapterAccount adapterAccount;
    protected static DatabaseReference databaseRef;
    protected static Boolean showOrHide = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        edtIdNV = view.findViewById(R.id.edtIdNV);
        edtTenNV = view.findViewById(R.id.edtTenNV);
        edtMatKhau = view.findViewById(R.id.edtMatKhau);

        btnOkCreate = view.findViewById(R.id.btnOkCreate);
        btnExit = view.findViewById(R.id.btnExit);

        rcView_Account = view.findViewById(R.id.rcView_Account);
        tv_showOrHide = view.findViewById(R.id.tv_showOrHide);
        linear_create_account = view.findViewById(R.id.linear_create_account);

        rcView_Account.setHasFixedSize(true);
        rcView_Account.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseRef = FirebaseDatabase.getInstance().getReference("Account");

        tv_showOrHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showOrHide == false){
                    tv_showOrHide.setText("Hide");
                    linear_create_account.setVisibility(View.VISIBLE);
                    showOrHide = true;
                }else {
                    tv_showOrHide.setText("Show");
                    linear_create_account.setVisibility(View.GONE);
                    showOrHide = false;
                }
            }
        });




        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentHome();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragment).commit();
                Trang_Chu.rltWelcome.setVisibility(View.VISIBLE);
            }
        });
        btnOkCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtIdNV.getText().toString();
                String tenNV = edtTenNV.getText().toString();
                String pass = edtMatKhau.getText().toString();

                if (id.isEmpty()) {
                    edtIdNV.setError("Chưa nhập Id");
                    edtIdNV.requestFocus();
                } else if (tenNV.isEmpty()) {
                    edtTenNV.setError("Hãy nhập tên NV");
                    edtTenNV.requestFocus();
                } else if (pass.isEmpty()) {
                    edtMatKhau.setError("Mật khẩu trống");
                    edtMatKhau.requestFocus();
                } else {
                    Account account = new Account();

                    account.setUsername(id);
                    account.setFullName(tenNV);
                    account.setPassword(pass);
                    databaseRef.child(id).setValue(account).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            setNullEdt();
                            Toast.makeText(getContext(), "Cấp tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    public static void setNullEdt(){
        edtIdNV.setText("");
        edtTenNV.setText("");
        edtMatKhau.setText("");
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Account> options = new FirebaseRecyclerOptions.Builder<Account>().setQuery(databaseRef, Account.class).build();
        adapterAccount = new FirebaseAdapterAccount(options);
        rcView_Account.setAdapter(adapterAccount);
        adapterAccount.startListening();
    }
}