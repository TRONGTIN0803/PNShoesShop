package com.example.duanmau_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QLSach_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsach);
        Button btntheloaisach=findViewById(R.id.btntheloaisach);
        Button btnnsx=findViewById(R.id.btnnsx);
        Button btntacgia=findViewById(R.id.btntacgia);
        Button btnsach=findViewById(R.id.btnsach);
        Button btnexit=findViewById(R.id.btnexit);

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btntheloaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QLSach_Activity.this,TheLoai_Activity.class));
            }
        });


        btnsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QLSach_Activity.this,Sach_Activity.class));
            }
        });
    }
}