package com.example.appdemo.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdemo.ManHinh.Trang_Chu;
import com.example.appdemo.R;
import com.example.appdemo.models.Account;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class FragmentProfile extends Fragment {

    private ImageView imgBackground, imgAvatar;
    private TextView tvName, tvEmail, tvPhone, tvId;
    private Button btnEditProfile, btnLogout, btnOkProfile;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri uri_Image;
    private String url_Image;

    Boolean check_Avatar_isClick = false;
    Boolean check_Background_isClick = false;
    String user;
    RelativeLayout parent_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_profile, null);

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

        Trang_Chu trang_chu = (Trang_Chu) getActivity();
        tvId.setText(String.valueOf(trang_chu.getIdOfAccount()));
        tvName.setText(trang_chu.getFullName());
        tvEmail.setText("");

        String url_avatar = trang_chu.getUrl_avatar();
        String url_background = trang_chu.getUrl_background();

        if(!url_avatar.isEmpty()){
            Picasso.get().load(url_avatar).into(imgAvatar);
        }
        if(!url_background.isEmpty()){
            Picasso.get().load(url_background).into(imgBackground);
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Account");
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.activity_up_image, container, false);
        builder.setView(v);

        Button btnChooseImage = v.findViewById(R.id.btnChooseImage);
        AlertDialog alertDialog = builder.create();


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
                         pickRef = storageReference.child("Image of "+trang_chu.getUserOfAccount());
                    }
                    if(check_Background_isClick == true){
                         pickRef = storageReference.child("Image Background of "+trang_chu.getUserOfAccount());
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

                                        Account account = new Account();
//                                        account.setEmail(mainActivity.getEmailUser());
                                        account.setFullName(trang_chu.getFullName());
                                        account.setUsername(trang_chu.getUserOfAccount());
                                        account.setPassword(trang_chu.getPassOfAccount());

                                        if(check_Avatar_isClick == true){
                                            account.setUrl_avatar(url_Image);
                                            Picasso.get().load(url_Image).into(imgAvatar);
                                            check_Avatar_isClick = false;
                                        }else {
                                            account.setUrl_avatar(trang_chu.getUrl_avatar());
                                        }

                                        if(check_Background_isClick == true){
                                            account.setUrl_background(url_Image);
                                            Picasso.get().load(url_Image).into(imgBackground);
                                            check_Background_isClick = false;
                                        }else {
                                            account.setUrl_background(trang_chu.getUrl_background());
                                        }

                                        databaseRef.child(trang_chu.getUserOfAccount()).setValue(account);

                                        Toast.makeText(getContext(), "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
//                                        requireActivity().getSupportFragmentManager()
//                                                        .beginTransaction().replace(
//                                                                R.id.frLayout, new FragmentProfile()
//                                                ).commit();
//                                        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
//                                        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
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
