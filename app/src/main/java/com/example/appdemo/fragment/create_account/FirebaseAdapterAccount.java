package com.example.appdemo.fragment.create_account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;
import com.example.appdemo.models.Account;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class FirebaseAdapterAccount extends FirebaseRecyclerAdapter<Account, FirebaseAdapterAccount.ViewHolder> {

    public FirebaseAdapterAccount(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }


    @NonNull
    @Override
    public FirebaseAdapterAccount.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Account model) {

        holder.onBin(model);

        holder.ibtnDelete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = holder.user_account.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc chắn xóa tài khoản: " + userName);

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment_Create_Account.databaseRef.child(userName).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(v.getContext(), "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        
        holder.ibtnEdit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Create_Account.linear_create_account.setVisibility(View.VISIBLE);
                Fragment_Create_Account.showOrHide = true;
                Fragment_Create_Account.tv_showOrHide.setText("Hide");
                
                String name = holder.name_account.getText().toString();
                String user = holder.user_account.getText().toString();
                String pass = holder.pass_account.getText().toString();
                
                Fragment_Create_Account.edtTenNV.setText(name);
                Fragment_Create_Account.edtIdNV.setText(user);
                Fragment_Create_Account.edtMatKhau.setText(pass);
                
                Fragment_Create_Account.btnOkCreate.setText("Chỉnh sửa");
                Fragment_Create_Account.btnExit.setText("Hủy");

//                Fragment_Create_Account.btnExit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Fragment_Create_Account.setNullEdt();
//                        Fragment_Create_Account.btnOkCreate.setText("OK");
//                        Fragment_Create_Account.btnExit.setText("Back");
//
//                    }
//
//                });

                Fragment_Create_Account.btnOkCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        Account account = new Account();
                        
                        account.setUsername(Fragment_Create_Account.edtIdNV.getText().toString());
                        account.setPassword(Fragment_Create_Account.edtMatKhau.getText().toString());
                        account.setFullName(Fragment_Create_Account.edtTenNV.getText().toString());
                        
                        Fragment_Create_Account.databaseRef.child(user).setValue(account).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Fragment_Create_Account.setNullEdt();
                                Fragment_Create_Account.btnOkCreate.setText("OK");
                                Toast.makeText(v.getContext(), "Đã sửa đổi tài khoản", Toast.LENGTH_SHORT).show();
                            }
                        });
                        
                    }
                });
                
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_account, user_account, pass_account;
        ImageButton ibtnEdit_account, ibtnDelete_account;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_account = itemView.findViewById(R.id.name_account);
            user_account = itemView.findViewById(R.id.user_account);
            pass_account = itemView.findViewById(R.id.pass_account);
            ibtnEdit_account = itemView.findViewById(R.id.ibtnEdit_account);
            ibtnDelete_account = itemView.findViewById(R.id.ibtnDelete_account);
        }

        public void onBin(Account account) {
            name_account.setText(account.getFullName());
            user_account.setText(account.getUsername());
            pass_account.setText(account.getPassword());
        }
    }
}
