package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdemo.R;
import com.example.appdemo.data.UpDataToSever;
import com.example.appdemo.models.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateNewPassword extends AppCompatActivity {

    private EditText edtNewPass, edtConfirmPass;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnOk = findViewById(R.id.btnOk);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String key = intent.getStringExtra("key");
        String user = intent.getStringExtra("user");
        String fullName = intent.getStringExtra("fullName");


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

                    Account account = new Account();
                    account.setId(id);
                    account.setFullName(fullName);
                    account.setUsername(user);

                    account.setPassword(edtNewPass.getText().toString());
                    databaseRef.child(key).setValue(account);

                    Intent backToLogin = new Intent(CreateNewPassword.this, LoginActivity.class);
                    startActivity(backToLogin);
                    finish();
                }

            }
        });


    }
}