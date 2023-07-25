package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.UpDataToSever;
import com.example.appdemo.account.Account;
import com.example.appdemo.account.acountdao.AccountDao;

import java.util.ArrayList;

public class CreateNewPassword extends AppCompatActivity {

    private EditText edtNewPass, edtConfirmPass;
    private Button btnOk;
    private ArrayList<Account>  listAccount = new ArrayList<>();
    private AccountDao accountDao ;

    private UpDataToSever upDataToSever =new UpDataToSever();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnOk = findViewById(R.id.btnOk);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String newPass = edtNewPass.getText().toString();
                String confirmNewPass = edtConfirmPass.getText().toString();
                Boolean checkPass = newPass.equals(confirmNewPass);

                if(newPass.isEmpty()){
                    edtNewPass.setError("Không được bỏ trống");
                    edtNewPass.requestFocus();
                } else if (confirmNewPass.isEmpty()) {
                    edtConfirmPass.setError("Không được bỏ trống");
                    edtConfirmPass.requestFocus();
                } else if (!checkPass) {
                    edtConfirmPass.setError("Mật khẩu không trùng khớp");
                    edtConfirmPass.requestFocus();
                }else {
                        accountDao=new AccountDao(CreateNewPassword.this);
                    listAccount = accountDao.getListAcount();

                    for (Account account: listAccount) {
                        if(id == account.getId()){
                            account.setId(id);
                            account.setPassword(newPass);
                            accountDao.updateAccount(account);
                            upDataToSever.upAccount(CreateNewPassword.this);
                            Intent i_backToLogin = new Intent(CreateNewPassword.this, LoginActivity.class);
                            startActivity(i_backToLogin);
                            finish();
                           
                            break;

                        }
                    }

                }

            }
        });


    }
}