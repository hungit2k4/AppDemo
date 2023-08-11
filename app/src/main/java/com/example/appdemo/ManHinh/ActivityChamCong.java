package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;


import com.example.appdemo.R;
import com.example.appdemo.models.ChamCong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ActivityChamCong extends AppCompatActivity {

    private CalendarView calendarView;


    private ArrayList<String> dates;
    private Button btnAddChamCong;
    private RecyclerView rcChamCong;
    private EditText edtAddMaNvChamCong;
    private ArrayList<String> listKey;

    private int ngay, thang, nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        calendarView = findViewById(R.id.calendarView);
        btnAddChamCong = findViewById(R.id.btnAddChamCong);
        rcChamCong = findViewById(R.id.rcChamCong);
        edtAddMaNvChamCong = findViewById(R.id.edtAddMaNvChamCong);
        listKey = new ArrayList<>();

        DatabaseReference databaseRef_chamCong = FirebaseDatabase.getInstance().getReference("Bảng chấm công");
        DatabaseReference databaseRef_getId = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                ngay = dayOfMonth;
                thang = month + 1;
                nam = year;

            }
        });

        databaseRef_getId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                     ) {
                            listKey.add(dataSnapshot.getRef().getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edtAddMaNvChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key[] = new String[listKey.size()];
                for(int i = 0; i<listKey.size(); i++){

                    key[i] = listKey.get(i);
                }


                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityChamCong.this);
                builder.setTitle("Chọn Id Nhận Viên");
                builder.setItems(key, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        edtAddMaNvChamCong.setText(key[i]);
                    }
                });

                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnAddChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String maNV = edtAddMaNvChamCong.getText().toString();
                if(maNV.isEmpty()){
                    edtAddMaNvChamCong.setError("Mã nhân viên trống");
                    edtAddMaNvChamCong.requestFocus();
                }else {

                    if(ngay == 0 && thang == 0 && nam == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityChamCong.this);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Vui lòng chọn ngày");
                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else {
                        String chilkey = ngay+"-"+thang+"-"+nam;

                        ChamCong chamCong = new ChamCong();
                        chamCong.setManv(maNV);


                        databaseRef_chamCong.child(chilkey+"/"+maNV).setValue(chamCong);
                    }



                }
            }
        });

    }

}

