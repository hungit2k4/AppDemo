package com.example.appdemo.Apdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Create_Account;
import com.example.appdemo.data.acountdao.ChucNang;
import com.example.appdemo.R;
import com.example.appdemo.models.Account;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChucNangAdapter extends RecyclerView.Adapter<ChucNangAdapter.ViewHolder> {
    ArrayList<ChucNang> list;
    Context context;

    public ChucNangAdapter(ArrayList<ChucNang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvCN;
        TextView tvCN1, tvCN2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvCN = itemView.findViewById(R.id.imvCN);
            tvCN1 = itemView.findViewById(R.id.tvCN1);
            tvCN2 = itemView.findViewById(R.id.tvCN2);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trang_chu_item, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChucNangAdapter.ViewHolder holder, int position) {
        ChucNang chucNang = list.get(position);

        holder.imvCN.setImageResource(chucNang.getIcon());
        holder.tvCN1.setText(chucNang.getTxt1());
        holder.tvCN2.setText(chucNang.getTxt2());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        holder.imvCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chucNang.getId() == 5) {

                    v = layoutInflater.inflate(R.layout.activity_create_account, null);
                    builder.setView(v);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                     EditText edtIdNV, edtTenNV, edtTenDangNhap, edtMatKhau;
                     Button btnOkCreate;

                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

                    edtIdNV = v.findViewById(R.id.edtIdNV);
                    edtTenNV = v.findViewById(R.id.edtTenNV);
                    edtTenDangNhap = v.findViewById(R.id.edtTenDangNhap);
                    edtMatKhau = v.findViewById(R.id.edtMatKhau);
                    btnOkCreate = v.findViewById(R.id.btnOkCreate);

                    btnOkCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String idNV = edtIdNV.getText().toString();
                            String tenNV = edtTenNV.getText().toString();
                            String tenDangNhap = edtTenDangNhap.getText().toString();
                            String matKhau = edtMatKhau.getText().toString();

                            if(idNV.isEmpty()){
                                edtIdNV.setError("Id trống");
                                edtIdNV.requestFocus();
                            } else if (tenNV.isEmpty()) {
                                edtTenNV.setError("Tên NV trống");
                                edtTenNV.requestFocus();
                            } else if (tenDangNhap.isEmpty()) {
                                edtTenDangNhap.setError("Tên đăng nhập trống");
                                edtTenDangNhap.requestFocus();
                            } else if (matKhau.isEmpty()) {
                                edtMatKhau.setError("Mật khẩu trống");
                                edtMatKhau.requestFocus();
                            }else {

                                Account account = new Account();
                                account.setId(Integer.parseInt(idNV));
                                account.setFullName(tenNV);
                                account.setUsername(tenDangNhap);
                                account.setPassword(matKhau);

                                databaseRef.child(tenDangNhap).setValue(account).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
