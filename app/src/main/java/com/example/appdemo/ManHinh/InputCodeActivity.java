package com.example.appdemo.ManHinh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ImageButton btnBackForgot;
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
        btnBackForgot = findViewById(R.id.btnBackForgot);
        btnBackForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String key = intent.getStringExtra("key");
        String user = intent.getStringExtra("user");
        String fullName = intent.getStringExtra("fullName");
        String url_avatar = intent.getStringExtra("url_avatar");
        String url_background = intent.getStringExtra("url_background");

        Long rdNumOne = Math.round(Math.random() * 9);
        Long rdNumTwo = Math.round(Math.random() * 9);
        Long rdNumThree = Math.round(Math.random() * 9);
        Long rdNumFour = Math.round(Math.random() * 9);

        otp1 = String.valueOf(rdNumOne);
        otp2 = String.valueOf(rdNumTwo);
        otp3 = String.valueOf(rdNumThree);
        otp4 = String.valueOf(rdNumFour);

        // di chuyển sang ô nhập kế tiếp
        edtNumOne.addTextChangedListener(new OtpTextWatcher(edtNumOne, edtNumTwo));
        edtNumTwo.addTextChangedListener(new OtpTextWatcher(edtNumTwo, edtNumThree));
        edtNumThree.addTextChangedListener(new OtpTextWatcher(edtNumThree, edtNumFour));
        edtNumFour.addTextChangedListener(new OtpTextWatcher(edtNumFour, null));

        sendNotification(otp1, otp2, otp3, otp4);

        btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long rdNumOne = Math.round(Math.random() * 9);
                Long rdNumTwo = Math.round(Math.random() * 9);
                Long rdNumThree = Math.round(Math.random() * 9);
                Long rdNumFour = Math.round(Math.random() * 9);

                otp1 = String.valueOf(rdNumOne);
                otp2 = String.valueOf(rdNumTwo);
                otp3 = String.valueOf(rdNumThree);
                otp4 = String.valueOf(rdNumFour);
                sendNotification(otp1, otp2, otp3, otp4);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inPutOtp1 = edtNumOne.getText().toString();
                String inPutOtp2 = edtNumTwo.getText().toString();
                String inPutOtp3 = edtNumThree.getText().toString();
                String inPutOtp4 = edtNumFour.getText().toString();

                Boolean check1 = inPutOtp1.equals(otp1);
                Boolean check2 = inPutOtp2.equals(otp2);
                Boolean check3 = inPutOtp3.equals(otp3);
                Boolean check4 = inPutOtp4.equals(otp4);

                if (inPutOtp1.isEmpty()) {
                    edtNumOne.setError("Trống");
                    edtNumOne.requestFocus();
                } else if (inPutOtp2.isEmpty()) {
                    edtNumTwo.setError("Trống");
                    edtNumTwo.requestFocus();
                } else if (inPutOtp3.isEmpty()) {
                    edtNumThree.setError("Trống");
                    edtNumThree.requestFocus();
                } else if (inPutOtp4.isEmpty()) {
                    edtNumFour.setError("Trống");
                    edtNumFour.requestFocus();
                } else {

                    if (check1 && check2 && check3 && check4) {
                        Intent i_gotoCreateNewPass = new Intent(InputCodeActivity.this, CreateNewPassword.class);
                        i_gotoCreateNewPass.putExtra("key", key);
                        i_gotoCreateNewPass.putExtra("user", user);
                        i_gotoCreateNewPass.putExtra("fullName", fullName);
                        i_gotoCreateNewPass.putExtra("url_avatar", url_avatar);
                        i_gotoCreateNewPass.putExtra("url_background", url_background);
                        i_gotoCreateNewPass.putExtra("id", id);
                        startActivity(i_gotoCreateNewPass);
                        finish();
                        Toast.makeText(InputCodeActivity.this, "Đã nhập code thành công", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InputCodeActivity.this, "Mã OPT không đúng", Toast.LENGTH_LONG).show();
                        return;
                    }
                }


            }
        });
    }

    public void sendNotification(String otp1, String otp2, String otp3, String otp4) {

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

    private class OtpTextWatcher implements TextWatcher {

        private EditText currentEditText;
        private EditText nextEditText;

        public OtpTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String otp = editable.toString().trim();
            if (otp.length() == 1) {
                // Nếu đã nhập một số, di chuyển tới ô EditText tiếp theo (nếu có)
                if (nextEditText != null) {
                    nextEditText.requestFocus();
                }
            }
        }
    }
}