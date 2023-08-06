package com.example.appdemo.ManHinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appdemo.fragment.FragmentChat;
import com.example.appdemo.fragment.FragmentHome;
import com.example.appdemo.R;
import com.example.appdemo.fragment.FragmentProfile;
import com.example.appdemo.models.ImageAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trang_Chu extends AppCompatActivity {
    TextView btnThongBao, tvDate, tvHello;
    LottieAnimationView loading_home;
    ImageView imageAvatar;
    public static RelativeLayout rltWelcome;
    FrameLayout frLayout;
    BottomNavigationView bottomNavigationView;
    DatabaseReference databaseRef;
    StorageReference storageRef;
    public static String inFullName, user, pass, key, old_url_avatar, old_url_background;
    public static int id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);


        rltWelcome = findViewById(R.id.rltWelcome);
        imageAvatar = findViewById(R.id.imageAvatar);
        btnThongBao = findViewById(R.id.btnThongBao);
        tvDate = findViewById(R.id.tvDate);
        tvHello = findViewById(R.id.tvHello);
        loading_home = findViewById(R.id.loading_home);

        Date date = new Date();// Định dạng để lấy thứ, ngày và tháng

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");// Lấy thông tin về thứ, ngày và tháng
        String dateTimeInfo = sdf.format(date);
        tvDate.setText(dateTimeInfo);

        Intent intent = getIntent();

        id = intent.getIntExtra("id", -1);
        inFullName = intent.getStringExtra("fullName");
        user = intent.getStringExtra("user");
        pass = intent.getStringExtra("pass");
        key = intent.getStringExtra("key");

        storageRef = FirebaseStorage.getInstance().getReference("ImageAccount");
        databaseRef = FirebaseDatabase.getInstance().getReference("ImageAccount");

        if (key != null) {
            databaseRef.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    if (snapshot.getValue(ImageAccount.class) != null) {
                        ImageAccount imageAccount = snapshot.getValue(ImageAccount.class);

                        old_url_avatar = imageAccount.getUrlImage();
                        old_url_background = imageAccount.getUrl_Image_Background();

                        Picasso.get().load(old_url_avatar).into(imageAvatar, new Callback() {
                            @Override
                            public void onSuccess() {
                                loading_home.cancelAnimation();
                                loading_home.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                        tvHello.setText("Xin chào, "+inFullName);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        tvHello.setText("Xin chào, " + inFullName);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Fragment fragment = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentBottom = null;
                    if (item.getItemId() == R.id.bottom_home) {
                        item.setTitle("Home");
                        rltWelcome.setVisibility(View.VISIBLE);
                        fragmentBottom = new FragmentHome();
                } else if (item.getItemId() == R.id.mChat) {
                        item.setTitle("Chat");
                        rltWelcome.setVisibility(View.GONE);
                    fragmentBottom = new FragmentChat();
                } else if (item.getItemId() == R.id.bottom_profile) {
                        item.setTitle("Profile");
                        rltWelcome.setVisibility(View.GONE);
                    fragmentBottom = new FragmentProfile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragmentBottom).commit();
                return true;
            }
        });

        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Chu.this, Infor_account.class);
                startActivity(intent);
            }
        });

        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_Chu.this, Infor_account.class);
                startActivity(intent);
            }
        });
    }

}

