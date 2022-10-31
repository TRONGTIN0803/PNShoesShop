package com.example.duanmau_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau_application.DAO.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {
    EditText edtusername,edtmattqmk,edttenttqmk;
    EditText edtpassword;
    private CheckBox chxremember;
    private NguoiDungDAO nguoiDungDAO;
    private String matkhau,mathuthu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnlogin=findViewById(R.id.btnlogin);
        edtusername=findViewById(R.id.edtusername);
        edtpassword=findViewById(R.id.edtpassword);
        chxremember=findViewById(R.id.cbxremember);
        nguoiDungDAO=new NguoiDungDAO(this);


        onResume();




        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                edtusername.setText("tt01");
//                edtpassword.setText("123");
                String username=edtusername.getText().toString();
                String password=edtpassword.getText().toString();
                boolean check=nguoiDungDAO.KiemTraDangNhap(username,password);
                if (check){
//gdfgfd

<<<<<<< HEAD
=======
//hahaconga

                String hahaconga;

>>>>>>> 1d87d0ba02ea1da247804b1b94ec80b597f15c99
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }else{
                    Toast.makeText(LoginActivity.this, "Dang Nhap That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void loadregister(View view) {

    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.anim_enter1, R.anim.anim_exit1);
        super.onResume();
        if (chxremember.isChecked()){
            SharedPreferences sharedPreferences=getSharedPreferences("THONGTIN",MODE_PRIVATE);
            String matt=sharedPreferences.getString("mauser","");
            String password=sharedPreferences.getString("password","");
            edtusername.setText(matt);
            edtpassword.setText(password);
        }else if(!chxremember.isChecked()){
            edtusername.setText("");
            edtpassword.setText("");
        }
    }

    public void showdialogqmk(View view) {
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quenmatkhau);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button btnquenmatkhau=dialog.findViewById(R.id.btnquenmatkhau);
        Button btnhuyqmk= dialog.findViewById(R.id.btnhuyquenmatkhau);
        edtmattqmk=dialog.findViewById(R.id.edtmattqmk);
        edttenttqmk=dialog.findViewById(R.id.edttenttqmk);

        btnhuyqmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mathuthu=edtmattqmk.getText().toString();
                dialogshowmatkhau(mathuthu);
                dialog.dismiss();


            }
        });

        dialog.show();

    }

    private void dialogshowmatkhau(String s){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_showmatkhau);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button btnok=dialog.findViewById(R.id.btnok);
        TextView txtshowmatkhau=dialog.findViewById(R.id.txtshowmatkhau);
        matkhau=nguoiDungDAO.QuenMatKhau(mathuthu);
        txtshowmatkhau.setText(matkhau);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}