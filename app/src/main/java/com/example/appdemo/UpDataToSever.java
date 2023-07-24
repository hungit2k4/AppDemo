package com.example.appdemo;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdemo.account.Account;
import com.example.appdemo.account.acountdao.AccountDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class UpDataToSever {
    private Context c;

    public UpDataToSever(Context c) {
        this.c = c;
    }

    private ArrayList<Account> listAccFromDatabase;
    private ArrayList<Account> listAcc;
    private Boolean check=false;
    private AccountDao accountDao = new AccountDao(c);

    public void upAccount() {
        listAccFromDatabase = accountDao.getListAcount();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("account").setValue(listAccFromDatabase, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
    }

    public interface OnDataLoadedListener {
        void onDataLoaded(ArrayList<Account> listAcc);

        void onDataLoadFailed(String errorMessage);
    }


    // Các hàm và biến khác...
    // Hàm lấy dữ liệu từ Firebase
    public void getAccountToSever(Context c, final OnDataLoadedListener onDataLoadedListener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("account");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Dữ liệu đã thay đổi, xử lý tại đây
                // dataSnapshot chứa dữ liệu mới từ "data"
                listAcc = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     Account account = snapshot.getValue(Account.class);
                     if (account != null) {
                         listAcc.add(account);
                     }
                }
                // Gọi onDataLoadedListener để truyền danh sách người dùng về Activity hoặc Fragment
                 onDataLoadedListener.onDataLoaded(listAcc);
                for(Account x:listAcc){
                    if(check){
                        for (Account y:listAcc){
                                accountDao.addAccount(y);
                        }
                    }
                    for (Account z:listAccFromDatabase){
                        if (!x.getUsername().equals(z.getUsername())||
                                x.getUsername().equals(z.getUsername())&&!x.getPassword().equals(z.getPassword())){
                            check=true;
                        }
                    }
                }
                //update lại data from database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                onDataLoadedListener.onDataLoadFailed(databaseError.getMessage());
            }
        });

    }
}
