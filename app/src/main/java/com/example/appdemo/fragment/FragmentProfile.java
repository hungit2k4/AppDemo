package com.example.appdemo.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appdemo.ManHinh.LoginActivity;
import com.example.appdemo.ManHinh.ThemNV;
import com.example.appdemo.ManHinh.Trang_Chu;
import com.example.appdemo.R;

import com.example.appdemo.models.ImageAccount;
import com.example.appdemo.models.NhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FragmentProfile extends Fragment {

    private ImageView imgBackground, imgAvatar;
    private TextView tvName, tvEmail, tvPhone, tvId;
    private LottieAnimationView loading_avatar, loading_background;
    private Button btnEditProfile, btnLogout, btnOkProfile;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri uri_Image;
    private String url_Image;
    String maNv, tenNv, ngaySinh, sDT, diaChi, email;
    private Integer gioiTinh;
    private DatabaseReference databaseReference;

    Boolean check_Avatar_isClick = false;
    Boolean check_Background_isClick = false;
    Boolean check=true;
    RelativeLayout parent_layout;
    NhanVien nhanVien = new NhanVien();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        imgBackground = view.findViewById(R.id.imgBackground);
        imgAvatar = view.findViewById(R.id.imgAvatar);

        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnOkProfile = view.findViewById(R.id.btnOkProfile);
        parent_layout = view.findViewById(R.id.parent_layout);
        loading_avatar = view.findViewById(R.id.loading_avatar);
        loading_background = view.findViewById(R.id.loading_background);

        String id=LoginActivity.idGui;
        databaseReference = FirebaseDatabase.getInstance().getReference("thong tin nhan vien");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    String key= dataSnapshot.getRef().getKey();
                    if (key.equals(id)){
                       nhanVien = dataSnapshot.getValue(NhanVien.class);
                       check=true;
                       break;
                    }
                    else {
                        check=false;
                    }
                }

                if (check){

                    maNv = nhanVien.getMaNV();
                    tenNv = nhanVien.getTen();
                    ngaySinh = nhanVien.getNgaySinh();
                    sDT = nhanVien.getSoDT();
                    diaChi = nhanVien.getDiaChi();
                    email = nhanVien.getEmail();
                    gioiTinh = nhanVien.getGioiTinh();

                    tvId.setText(nhanVien.getMaNV());
                    tvName.setText(nhanVien.getTen());
                    tvEmail.setText(nhanVien.getEmail());
                    tvPhone.setText(nhanVien.getSoDT());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if(Trang_Chu.key != null){


            Picasso.get().load(Trang_Chu.old_url_avatar).into(imgAvatar, new Callback() {
                @Override
                public void onSuccess() {
                    loading_avatar.cancelAnimation();
                    loading_avatar.setVisibility(View.GONE);

                    loading_background.cancelAnimation();
                    loading_background.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

            Picasso.get().load(Trang_Chu.old_url_background).into(imgBackground, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }


        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ImageAccount");
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("ImageAccount");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_up_image, container, false);
        builder.setView(v);

        Button btnChooseImage = v.findViewById(R.id.btnChooseImage);
        Button btnViewImage = v.findViewById(R.id.btnViewImage);

        AlertDialog alertDialog = builder.create();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Đăng xuất");
                builder1.setMessage("Bạn có muốn đăng xuất?");
                builder1.setIcon(R.drawable.baseline_warning_amber_24);

                builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        alertDialog.dismiss();
                    }
                });

                builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();


            }
        });


        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                alertDialog.dismiss();
            }
        });

        btnOkProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(uri_Image != null){

                    StorageReference pickRef = null;


                    if(check_Avatar_isClick == true){
                         pickRef = storageReference.child("Image of "+Trang_Chu.user);
                    }
                    if(check_Background_isClick == true){
                         pickRef = storageReference.child("Image Background of "+Trang_Chu.user);
                    }

                    if(pickRef != null){
                        pickRef.putFile(uri_Image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uriTask.isSuccessful());
                                        Uri downloadUrl = uriTask.getResult();
                                        url_Image = downloadUrl.toString();

                                        ImageAccount imageAccount = new ImageAccount();


                                        if(check_Avatar_isClick == true){
                                            imageAccount.setUrlImage(url_Image);
                                            Picasso.get().load(url_Image).into(imgAvatar);
                                            check_Avatar_isClick = false;
                                        }else {
                                            imageAccount.setUrlImage(Trang_Chu.old_url_avatar);
                                        }

                                        if(check_Background_isClick == true){
                                            imageAccount.setUrl_Image_Background(url_Image);
                                            Picasso.get().load(url_Image).into(imgBackground);
                                            check_Background_isClick = false;
                                        }else {
                                            imageAccount.setUrl_Image_Background(Trang_Chu.old_url_background);
                                        }

                                        databaseRef.child(Trang_Chu.user).setValue(imageAccount);

                                        Toast.makeText(getContext(), "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
                                        btnOkProfile.setVisibility(View.GONE);
                                        btnEditProfile.setVisibility(View.VISIBLE);
                                        btnLogout.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error", "Error: " +e.getMessage());
                            }
                        });

                    }

                }

            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getActivity(), ThemNV.class);
               intent.putExtra("check", 1);
               intent.putExtra("manv", maNv);
               startActivity(intent);

            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialog.show();

                check_Avatar_isClick = true;
                btnOkProfile.setVisibility(View.VISIBLE);
                btnEditProfile.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);

            }
        });

        imgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialog.show();

                check_Background_isClick = true;
                btnOkProfile.setVisibility(View.VISIBLE);
                btnEditProfile.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);
            }
        });


        btnViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                v = LayoutInflater.from(getContext()).inflate(R.layout.layout_view_image, null);
                builder.setView(v);

                ImageView imageView = v.findViewById(R.id.imageView);
                TextView tvClose = v.findViewById(R.id.tvClose);

                if(check_Avatar_isClick ){
                    Picasso.get().load(Trang_Chu.old_url_avatar).into(imageView);
                } else if (check_Background_isClick) {
                    Picasso.get().load(Trang_Chu.old_url_background).into(imageView);
                }
                AlertDialog alertDialogViewImg = builder.create();
                alertDialogViewImg.show();
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogViewImg.dismiss();
                        alertDialog.dismiss();
                        check_Avatar_isClick = false;
                        check_Background_isClick = false;
                        btnOkProfile.setVisibility(View.GONE);
                        btnLogout.setVisibility(View.VISIBLE);
                        btnEditProfile.setVisibility(View.VISIBLE);
                    }
                });


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri_Image = data.getData();
            if(check_Avatar_isClick == true){
                imgAvatar.setImageURI(uri_Image);
            }
            if(check_Background_isClick == true){
                imgBackground.setImageURI(uri_Image);
            }
            }
        }
    }
