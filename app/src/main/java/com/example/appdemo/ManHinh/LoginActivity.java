package com.example.appdemo.ManHinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.appdemo.R;
import com.example.appdemo.account.Account;
import com.example.appdemo.account.acountdao.AccountDao;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserLogin, edtPassLogin;
    private Button btnLogin, btnForgotPass;
    private ArrayList<Account> list;
    private AccountDao accountDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserLogin = findViewById(R.id.edtUserLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPass = findViewById(R.id.btnForgotPass);

        accountDao = new AccountDao(LoginActivity.this);
        list = accountDao.getListAcount();

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

                    for(int i = 0; i < list.size(); i++){

                        if(user.equals(list.get(i).getUsername())){
                            if(pass.equals(list.get(i).getPassword())){
                                tmpUser = list.get(i).getUsername();
                                tmpPass = list.get(i).getPassword();
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
}