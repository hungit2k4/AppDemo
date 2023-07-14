package com.example.appdemo.ManHinh;

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

public class SignUpActivity extends AppCompatActivity {
    private EditText edtUserSignUp,edtPassSignUp;
   private Button btnSignUp;
    private AccountDao db ;
   private ArrayList<Account> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtUserSignUp = findViewById(R.id.edtUserSignUp);
        edtPassSignUp = findViewById(R.id.edtPassSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check=true;
                String user = edtUserSignUp.getText().toString();
                String pass= edtPassSignUp.getText().toString();
                if (user.equals("")){
                    edtUserSignUp.setError("Chưa nhập Username");
                    edtUserSignUp.requestFocus();
                    return;
                }
                if (pass.equals(""))
                {
                    edtPassSignUp.setError("Chưa nhập Password");
                    edtUserSignUp.requestFocus();
                    return;
                }
                list=db.getListAcount();
                for (Account x:list){
                    if (x.getUsername().equals(user)){
                        Toast.makeText(SignUpActivity.this,"Username đã ôồn tại",Toast.LENGTH_SHORT).show();
                        edtUserSignUp.setText("");
                        edtUserSignUp.requestFocus();
                        check=false;
                        break;

                    }
                }
                if (check){
                    db.addAccount(new Account(3,user,pass));
                }

            }
        });
    }
}