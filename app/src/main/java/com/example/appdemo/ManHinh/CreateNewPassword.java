package com.example.appdemo.ManHinh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdemo.R;

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

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String newPass = edtNewPass.getText().toString();
                String confirmNewPass = edtConfirmPass.getText().toString();



            }
        });


    }
}