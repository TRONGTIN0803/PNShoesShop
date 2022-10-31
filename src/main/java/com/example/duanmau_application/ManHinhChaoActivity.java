package com.example.duanmau_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        ImageView ivloading=findViewById(R.id.ivloading);

        Glide.with(this).load(R.drawable.loading).into(ivloading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ManHinhChaoActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        },3000);
    }
}