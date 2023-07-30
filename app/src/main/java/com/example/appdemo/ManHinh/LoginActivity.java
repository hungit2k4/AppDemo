package com.example.appdemo.ManHinh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.appdemo.R;
import com.example.appdemo.data.UpDataToSever;
import com.example.appdemo.data.acountdao.NhanVienDao;
import com.example.appdemo.models.Account;
import com.example.appdemo.models.NhanVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public static String id;
    private EditText edtUserLogin, edtPassLogin;
    private Button btnLogin, btnForgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserLogin = findViewById(R.id.edtUserLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPass = findViewById(R.id.btnForgotPass);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

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

                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String key = null;

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                String checkKey = dataSnapshot.getRef().getKey();
                                if (checkKey.equals(user)) {
                                    key = checkKey;
                                    break;
                                }
                            }

                            if (key != null) {
                                databaseRef.child(key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Account account = new Account();
                                        account = snapshot.getValue(Account.class);

                                        if (pass.equals(account.getPassword())) {

                                            Intent i_goToHome = new Intent(LoginActivity.this, Trang_Chu.class);

                                            i_goToHome.putExtra("id", account.getId());
                                            i_goToHome.putExtra("fullName", account.getFullName());
                                            i_goToHome.putExtra("user", account.getUsername());
                                            i_goToHome.putExtra("pass", account.getPassword());

                                            if(account.getUrl_avatar() == null){
                                                i_goToHome.putExtra("urlAvatar","");
                                            }else {
                                                i_goToHome.putExtra("urlAvatar",account.getUrl_avatar());
                                            }
                                            if(account.getUrl_background() == null){
                                                i_goToHome.putExtra("urlBackground","");
                                            }else {
                                                i_goToHome.putExtra("urlBackground",account.getUrl_background());
                                            }
                                            startActivity(i_goToHome);
                                            finish();
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                        } else {
                                            edtPassLogin.setError("Sai mật khẩu");
                                            edtPassLogin.requestFocus();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("Database Error", "Error reading data: " + error.getMessage());
                                    }
                                });
                            }else {
                                edtUserLogin.setError("Tài khoản chưa đăng ký");
                                edtUserLogin.requestFocus();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountDao = new AccountDao(LoginActivity.this);
                accountDao.deleteAccount(3);
                accountDao.deleteAccount(4);
                accountDao.addAccount(new Account("NV01","123"));
                upDataToSever = new UpDataToSever();
                upDataToSever.upAccount(LoginActivity.this);
                Intent i_gotoForgotPass = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(i_gotoForgotPass);
            }
        });


    }

}