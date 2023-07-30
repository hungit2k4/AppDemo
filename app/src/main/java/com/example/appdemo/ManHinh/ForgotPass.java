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
import com.example.appdemo.models.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ForgotPass extends AppCompatActivity {

    private EditText edtStaffId, edtUserForgot;
    private Button btnSend;
    private String key = null;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edtStaffId = findViewById(R.id.edtStaffId);
        edtUserForgot = findViewById(R.id.edtUserForgot);
        btnSend = findViewById(R.id.btnSend);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getId = edtStaffId.getText().toString();
                String getUserName = edtUserForgot.getText().toString();

                if (getId.isEmpty()) {
                    edtStaffId.setError("Chưa nhập id");
                    edtStaffId.requestFocus();
                } else if (getUserName.isEmpty()) {
                    edtUserForgot.setError("Chưa nhập Username");
                    edtUserForgot.requestFocus();
                } else {

                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String checkKey = dataSnapshot.getRef().getKey();
                                if (getUserName.equals(checkKey)) {
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

                                        int id = account.getId();
                                        String fullName = account.getFullName();
                                        String user = account.getUsername();
                                        String url_avatar = account.getUrl_avatar();
                                        String url_background = account.getUrl_background();

                                        if (getId.equals(String.valueOf(id))) {

                                            Intent i_gotoInputCode = new Intent(ForgotPass.this, InputCodeActivity.class);
                                            i_gotoInputCode.putExtra("key", key);
                                            i_gotoInputCode.putExtra("id", id);
                                            i_gotoInputCode.putExtra("user", user);
                                            i_gotoInputCode.putExtra("fullName", fullName);
                                            i_gotoInputCode.putExtra("url_avatar", url_avatar);
                                            i_gotoInputCode.putExtra("url_background", url_background);

                                            startActivity(i_gotoInputCode);
                                            finish();

                                        } else {
                                            edtStaffId.setError("Mã id không đúng");
                                            edtStaffId.requestFocus();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }else {
                                edtUserForgot.setError("Tài khoản chưa được cấp");
                                edtUserForgot.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Error", "Error: " + error.getMessage());
                        }
                    });



                }


            }
        });
    }
}