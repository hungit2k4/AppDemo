package com.example.appdemo.ManHinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.appdemo.R;
import com.example.appdemo.UpDataToSever;
import com.example.appdemo.account.Account;
import com.example.appdemo.account.acountdao.AccountDao;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserLogin, edtPassLogin;
    private Button btnLogin, btnForgotPass;
    public ArrayList<Account> list= new ArrayList<>();
    private AccountDao accountDao;
    private UpDataToSever upDataToSever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserLogin = findViewById(R.id.edtUserLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPass = findViewById(R.id.btnForgotPass);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
               btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUserLogin.getText().toString();
                String pass = edtPassLogin.getText().toString();

                if (user.isEmpty()) {
                    edtUserLogin.setError("Chưa nhập tài khoản");
                    edtUserLogin.requestFocus();
                } else if (pass.isEmpty()) {
                    edtPassLogin.setError("Chưa nhập mật khẩu");
                    edtPassLogin.requestFocus();
                } else {

                    String tmpUser = null;
                    String tmpPass = null;

                    for(Account x:list){
                        if(user.equals(x.getUsername())){
                            if(pass.equals(x.getPassword())){
                                tmpUser = x.getUsername();
                                tmpPass = x.getPassword();
                                break;
                            }
                        }

                    }

                    if(!user.equals(tmpUser) && !pass.equals(tmpPass)){
                        Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent=new Intent(LoginActivity.this, Trang_Chu.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_gotoForgotPass = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(i_gotoForgotPass);
                finish();
            }
        });




    }
    private void loadData(){
        upDataToSever= new UpDataToSever();
        upDataToSever.getAccountToSever(this, new UpDataToSever.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Account> listAcc) {
                list =listAcc;
                Toast.makeText(LoginActivity.this,"Dữ liệu đã được đồng bộ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDataLoadFailed(String errorMessage) {

            }
        });
    }
}