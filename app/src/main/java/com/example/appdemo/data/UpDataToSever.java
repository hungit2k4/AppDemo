package com.example.appdemo.data;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdemo.data.acountdao.NhanVienDao;
import com.example.appdemo.data.model.Account;
import com.example.appdemo.data.acountdao.AccountDao;
import com.example.appdemo.data.model.NhanVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpDataToSever {

    private ArrayList<Account> listAccFromDatabase;
    private ArrayList<Account> listAcc;
    private Boolean check=false;
    private AccountDao accountDao ;
    private ArrayList<NhanVien> listNvFromDatabase;
    private ArrayList<NhanVien> listNV;
    private NhanVienDao nhanVienDao ;
    public void upAccount(Context c) {
        accountDao = new AccountDao(c);
        listAccFromDatabase = accountDao.getListAcount();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("account").setValue(listAccFromDatabase, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {

                } else {
                    // Xử lý lỗi nếu có khi gửi dữ liệu
                    Log.e(TAG, "Lỗi khi gửi dữ liệu lên Firebase: " + error.getMessage());
                }
            }
        });
    }

    public interface OnDataLoadedListenerAcc {
        void onDataLoaded(ArrayList<Account> listAcc);


        void onDataLoadFailed(String errorMessage);
    }


    // Các hàm và biến khác...
    // Hàm lấy dữ liệu từ Firebase
    public void getAccFromSever(Context c, final OnDataLoadedListenerAcc onDataLoadedListener) {
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
                accountDao= new AccountDao(c);
                listAccFromDatabase =accountDao.getListAcount();
                if (listAccFromDatabase.size()<=0)
                    check=true;
                for(Account x:listAcc){
                    if(check){
                        accountDao.updateDatabase();
                        for (Account y:listAcc){
                                accountDao.addAccount(y);
                        }
                        check=false;
                        break;
                    }
                    for (Account z:listAccFromDatabase){
                        if (!x.getUsername().equals(z.getUsername())||
                                x.getUsername().equals(z.getUsername())&&!x.getPassword().equals(z.getPassword())){
                            check=true;
                            break;
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
    public void upNV(Context c){
        nhanVienDao = new NhanVienDao(c);
        listNV=nhanVienDao.getListNV();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("nhanvien").setValue(listNV, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {

                } else {
                    // Xử lý lỗi nếu có khi gửi dữ liệu
                    Log.e(TAG, "Lỗi khi gửi dữ liệu lên Firebase: " + error.getMessage());
                }
            }
        });
    }
    public interface OnDataLoadedListenerNV {
        void onDataLoaded(ArrayList<NhanVien> listNV);

        void onDataLoadFailed(String errorMessage);
    }
    public void getNVFromSever(Context c,final OnDataLoadedListenerNV onDataLoadedListenerNV){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("nhanvien");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNV = new ArrayList<>();
                DataSnapshot snapshot1=dataSnapshot.child("nhanvien");
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                    if(nhanVien!=null)
                        listNV.add(nhanVien);
                }
                onDataLoadedListenerNV.onDataLoaded(listNV);
                nhanVienDao= new NhanVienDao(c);
                listNvFromDatabase =nhanVienDao.getListNV();
                if (listNvFromDatabase.size()<=0)
                    check=true;
                for(NhanVien x:listNV){
                    if(check){
                        nhanVienDao.updateDatabase();
                        for (NhanVien y:listNV){
                            nhanVienDao.addNV(y);
                        }
                        check=false;
                        break;
                    }
                    for (NhanVien z:listNvFromDatabase){
                        if (!x.getMaNV().equals(z.getMaNV())){
                            check=true;
                            break;
                        }else if(!x.getTen().equals(z.getTen())){
                            check=true;
                            break;
                        }else if(!x.getNgaySinh().equals(z.getNgaySinh())){
                            check=true;
                            break;
                        }else if(!x.getGioiTinh().equals(z.getGioiTinh())){
                            check=true;
                            break;
                        }else if(!x.getSoDT().equals(z.getSoDT())){
                            check=true;
                            break;
                        }else if(!x.getDiaChi().equals(z.getDiaChi())){
                            check=true;
                            break;
                        }else if(!x.getChucVu().equals(z.getChucVu())){
                            check=true;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onDataLoadedListenerNV.onDataLoadFailed(error.getMessage());
            }
        });
    }
}
