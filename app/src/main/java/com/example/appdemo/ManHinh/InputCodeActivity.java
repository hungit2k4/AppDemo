package com.example.appdemo.ManHinh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.appdemo.R;
import com.example.appdemo.config.ConfigNotification;

import java.util.Date;

public class InputCodeActivity extends AppCompatActivity {

    EditText edtNumOne, edtNumTwo, edtNumThree, edtNumFour;
    Button btnSubmit, btnResendCode;

    String otp1;
    String otp2;
    String otp3;
    String otp4;

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


        sendNotification(otp1, otp2, otp3, otp4);

        btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(otp1, otp2, otp3, otp4);
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

    public void sendNotification(String otp1, String otp2, String otp3, String otp4) {

        Long rdNumOne = Math.round(Math.random() * 9);
        Long rdNumTwo = Math.round(Math.random() * 9);
        Long rdNumThree = Math.round(Math.random() * 9);
        Long rdNumFour = Math.round(Math.random() * 9);

        otp1 = String.valueOf(rdNumOne);
        otp2 = String.valueOf(rdNumTwo);
        otp3 = String.valueOf(rdNumThree);
        otp4 = String.valueOf(rdNumFour);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ConfigNotification.CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Mã OPT CỦA BẠN")
                .setContentText("Mã OPT của bạn là: " + otp1 + otp2 + otp3 + otp4 +
                        " .Vui lòng không chia sẻ mã này cho người khác");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            managerCompat.notify((int) new Date().getTime(), builder.build());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 7979);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7979) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    sendNotification(otp1, otp2, otp3, otp4);
                }
            }
        }
    }
}