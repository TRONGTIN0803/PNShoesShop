package com.example.duanmau_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau_application.DAO.NguoiDungDAO;

public class RegisterActivity extends AppCompatActivity {

    EditText edtdangkytendangnhap, edtdangkypassword, edthovaten, edtnamsinh,edtdiachi;
    Button btnregister, btnbacktologin;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edthovaten=findViewById(R.id.edthoten);
        edtdangkytendangnhap=findViewById(R.id.edtdangkytendangnhap);
        edtdangkypassword=findViewById(R.id.edtdangkypassword);
        edtnamsinh=findViewById(R.id.edtnamsinh);
        edtdiachi=findViewById(R.id.edtdiachi);
        btnregister=findViewById(R.id.btnregister);
        btnbacktologin=findViewById(R.id.btnbacktologin);
        nguoiDungDAO=new NguoiDungDAO(this);

        btnbacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                finish();
                overridePendingTransition(R.anim.anim_enter1, R.anim.anim_exit1);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mauser=edtdangkytendangnhap.getText().toString();
                String password=edtdangkypassword.getText().toString();
                String hoten=edthovaten.getText().toString();
                String namsinh=edtnamsinh.getText().toString();
                String diachi=edtdiachi.getText().toString();
                boolean check=nguoiDungDAO.DangKy(mauser,password,hoten,"thuthu",namsinh,diachi);
                if (check) {
                    Toast.makeText(RegisterActivity.this, "Dang ky thanh cong!", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }else{
                    Toast.makeText(RegisterActivity.this, "Dang ky that bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}