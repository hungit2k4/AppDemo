package com.example.appdemo.ManHinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdemo.R;

public class InputCodeActivity extends AppCompatActivity {

    EditText edtNumOne, edtNumTwo, edtNumThree, edtNumFour;
    Button btnSubmit, btnResendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);

        edtNumOne = findViewById(R.id.edtNumOne);
        edtNumTwo = findViewById(R.id.edtNumTwo);
        edtNumThree = findViewById(R.id.edtNumThree);
        edtNumFour = findViewById(R.id.edtNumFour);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnResendCode = findViewById(R.id.btnResendCode);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);


        randomCode();

        btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomCode();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i_gotoCreateNewPass = new Intent(InputCodeActivity.this, CreateNewPassword.class);
                i_gotoCreateNewPass.putExtra("id", id);
                startActivity(i_gotoCreateNewPass);

                Toast.makeText(InputCodeActivity.this, "Đã nhập code thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void randomCode() {
        Long rdNumOne = Math.round(Math.random() * 9);
        Long rdNumTwo = Math.round(Math.random() * 9);
        Long rdNumThree = Math.round(Math.random() * 9);
        Long rdNumFour = Math.round(Math.random() * 9);

        edtNumOne.setText(String.valueOf(rdNumOne));
        edtNumTwo.setText(String.valueOf(rdNumTwo));
        edtNumThree.setText(String.valueOf(rdNumThree));
        edtNumFour.setText(String.valueOf(rdNumFour));
    }
}