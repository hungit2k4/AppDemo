package com.example.appdemo.ManHinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdemo.R;
import com.example.appdemo.data.model.Account;
import com.example.appdemo.data.acountdao.AccountDao;

import java.util.ArrayList;

public class ForgotPass extends AppCompatActivity {

    private EditText edtStaffId, edtPhoneNumber;
    private Button btnSend;
    private ArrayList<Account> listAccount = new ArrayList<>();
    private AccountDao accountDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edtStaffId = findViewById(R.id.edtStaffId);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = edtStaffId.getText().toString();
                String phoneNumber = edtPhoneNumber.getText().toString();

                if(id.isEmpty()){
                    edtStaffId.setError("Chưa nhập id");
                    edtStaffId.requestFocus();
                }else if (phoneNumber.isEmpty()){
                    edtPhoneNumber.setError("Chưa nhập số điện thoại");
                    edtPhoneNumber.requestFocus();
                }else {
                    accountDao = new AccountDao(ForgotPass.this);
                    listAccount=accountDao.getListAcount();
                    int tmpId = -1;

                    for (int i = 0; i < listAccount.size(); i++){

                        tmpId = listAccount.get(i).getId();

                        if(Integer.valueOf(id) == listAccount.get(i).getId()){
                        }
                    }

                    if(id.equals(String.valueOf(tmpId))){

                        Intent i_gotoInputCode = new Intent(ForgotPass.this, InputCodeActivity.class);
                        i_gotoInputCode.putExtra("id", Integer.valueOf(id));
                        startActivity(i_gotoInputCode);
                        finish();

                    }else {
                        edtStaffId.setError("Mã id không đúng");
                        edtStaffId.requestFocus();
                    }

                }
            }
        });



    }
}