package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appdemo.R;
import com.example.appdemo.fragment.chamcong.ChamCong_Adapter;
import com.example.appdemo.models.ChamCong;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String datekey, maNV;
    private DatabaseReference databaseRef_chamCong, databaseRef_ngayCong, databaseRef_getId;
    public TextView tvNgayLam, tvNgayNghi;
    ChamCong_Adapter.ViewHolder viewHolder;

    private int ngay, thang, nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        calendarView = findViewById(R.id.calendarView);
        btnAddChamCong = findViewById(R.id.btnAddChamCong);
        rcChamCong = findViewById(R.id.rcChamCong);
        edtAddMaNvChamCong = findViewById(R.id.edtAddMaNvChamCong);
        tvNgayLam = findViewById(R.id.tvNgayLam);
        tvNgayNghi = findViewById(R.id.tvNgayNghi);

        rcChamCong.setHasFixedSize(true);
        rcChamCong.setLayoutManager(new LinearLayoutManager(ActivityChamCong.this));

        listKey = new ArrayList<>();


        databaseRef_ngayCong = FirebaseDatabase.getInstance().getReference("Ngày công");
        databaseRef_getId = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                tvNgayLam.setText("0 ngày");
                tvNgayNghi.setText("0 ngày");

                ngay = dayOfMonth;
                thang = month + 1;
                nam = year;
                datekey = ngay + "-" + thang + "-" + nam;

                databaseRef_chamCong = FirebaseDatabase.getInstance().getReference("Bảng chấm công/" + datekey);
                FirebaseRecyclerOptions<ChamCong> options = new FirebaseRecyclerOptions.Builder<ChamCong>().setQuery(databaseRef_chamCong, ChamCong.class).build();
                FirebaseRecyclerAdapter<ChamCong, ChamCong_Adapter.ViewHolder> adapter = new FirebaseRecyclerAdapter<ChamCong, ChamCong_Adapter.ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ChamCong_Adapter.ViewHolder holder, int position, @NonNull ChamCong model) {
                        String key = getRef(position).getKey();

                        databaseRef_chamCong.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.hasChild("manv")) {
                                    maNV = snapshot.child("manv").getValue().toString();

                                    databaseRef_getId = FirebaseDatabase.getInstance().getReference("thong tin nhan vien/" + maNV);
                                    databaseRef_getId.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.hasChild("ten")) {
                                                holder.tvTenNV.setText(snapshot.child("ten").getValue().toString());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    holder.tvMaNV.setText(maNV);

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            databaseRef_ngayCong.child(holder.tvMaNV.getText().toString()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.hasChild("ngayLam")) {

                                                        tvNgayLam.setText(snapshot.child("ngayLam").getValue().toString() + " ngày");
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    });

                                    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            databaseRef_chamCong.child(maNV).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    databaseRef_ngayCong.child(maNV).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            ChamCong chamCong = new ChamCong();
                                                            if (snapshot.hasChild("ngayLam")) {
                                                                chamCong = snapshot.getValue(ChamCong.class);

                                                                if (chamCong.getNgayLam() == 0) {
                                                                    chamCong.setNgayLam(0);
                                                                } else {
                                                                    chamCong.setNgayLam(chamCong.getNgayLam() - 1);
                                                                }

                                                            } else {
                                                                chamCong.setNgayLam(0);
                                                            }
                                                            databaseRef_ngayCong.child(maNV).setValue(chamCong);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ChamCong_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamcong, parent, false);
                        return new ChamCong_Adapter.ViewHolder(view);
                    }
                };


                rcChamCong.setAdapter(adapter);
                adapter.startListening();

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
                for (int i = 0; i < listKey.size(); i++) {

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

                maNV = edtAddMaNvChamCong.getText().toString();

                if (maNV.isEmpty() || maNV.equals("")) {
                    edtAddMaNvChamCong.setError("Mã nhân viên trống");
                    edtAddMaNvChamCong.requestFocus();
                } else {

                    if (ngay == 0 && thang == 0 && nam == 0) {
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
                    } else {

                        ChamCong chamCong = new ChamCong();
                        chamCong.setManv(maNV);

                        databaseRef_chamCong.child(maNV).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(snapshot.hasChild("manv")){
                                    String manv = snapshot.child("manv").getValue().toString();

                                    if(maNV.equals(manv)){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityChamCong.this);
                                        builder.setTitle("Thông báo");
                                        builder.setMessage("Nhân viên đã được chấm công cho ngày hôm nay từ trước đó!");
                                        builder.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }

                                }else {
                                    databaseRef_chamCong.child(maNV).setValue(chamCong);
                                    databaseRef_ngayCong.child(maNV).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ChamCong chamCong = new ChamCong();

                                            if (snapshot.hasChild("ngayLam")) {
                                                chamCong = snapshot.getValue(ChamCong.class);

                                                if (chamCong.getNgayLam() == 0) {
                                                    chamCong.setNgayLam(1);
                                                } else {
                                                    chamCong.setNgayLam(chamCong.getNgayLam() + 1);
                                                }

                                            } else {
                                                chamCong.setNgayLam(1);
                                            }

                                            databaseRef_ngayCong.child(maNV).setValue(chamCong);

                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                }
            }
        });


    }


}

