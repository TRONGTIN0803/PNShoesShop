package com.example.duanmau_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau_application.Adapter.Main_frangment_adminqltt;
import com.example.duanmau_application.DAO.NguoiDungDAO;
import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.Fragment.Fragment_Admin_QLTT;
import com.example.duanmau_application.Fragment.Fragment_DoanhthuTT;
import com.example.duanmau_application.Fragment.Fragment_LoaiSach;
import com.example.duanmau_application.Fragment.Fragment_QLPhieuMuon;
import com.example.duanmau_application.Fragment.Fragment_QLThanhVien;
import com.example.duanmau_application.Fragment.Fragment_Sach;
import com.example.duanmau_application.Fragment.Fragment_Thongke_DoanhThu;
import com.example.duanmau_application.Fragment.Fragment_main;
import com.example.duanmau_application.Fragment.Frangment_thongketop10;
import com.example.duanmau_application.Model.ThanhVien;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private FrameLayout frameLayout;
    EditText edtmkcu,edtmkmoi,edtmkmoilan2,edthotenttadmin,edtnamsinhttadmin,edtdiachittadmin;
    private Fragment fragment;
    int check=0;
    private TextView txttenuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nvView);
        frameLayout=findViewById(R.id.flContent);
        View hreadlayuot=navigationView.getHeaderView(0);
        txttenuser=hreadlayuot.findViewById(R.id.txttenuser);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PhieuMuonDAO phieuMuonDAO=new PhieuMuonDAO(this);
        phieuMuonDAO.getDSTTAdmin();

        drawerToggle=setupDrawerToggle();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellow)));
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
//        PhieuMuonDAO phieuMuonDAO=new PhieuMuonDAO(this);
//        phieuMuonDAO.getDSPhieuMuon();
//        SachDAO sachDAO=new SachDAO(MainActivity.this);
//        sachDAO.getDoanhThu("03102022","03102022");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment=null;
                switch (item.getItemId()){
                    case R.id.menuitemqlloaisach:
                        fragment=new Fragment_LoaiSach();
                        break;
                    case R.id.menuitemthoat:
                        finish();
                        break;
                    case R.id.menuitemqlpm:
                        fragment=new Fragment_QLPhieuMuon();
                        break;
                    case R.id.menuitemdmk:
                        showdialogdoimatkhau();
                        break;
                    case R.id.menuitemtop10:
                        fragment=new Frangment_thongketop10();
                        break;
                    case R.id.menuitemdoanhthu:
                        fragment=new Fragment_Thongke_DoanhThu();
                        break;
                    case R.id.menuitemqltv:
                        fragment=new Fragment_QLThanhVien();
                        break;
                    case R.id.menuitemqlsach:
                        fragment=new Fragment_Sach();
                        break;
                    case R.id.menuitemdoanhthuthuthu:
                        fragment=new Fragment_DoanhthuTT();
                        break;
                    case R.id.menuitemhome:
                        if (check==0){
                            fragment=new Fragment_Admin_QLTT();
                        }else{
                            fragment=new Fragment_main();
                        }
                        break;
                    case R.id.menuitemttcanhan:
                        showdialogTTcanhan();
                        break;
                    case R.id.menuitemtaotaikhoan:
                        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        break;
                    default:

                        break;
                }
                if (fragment!=null){
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }
                drawerLayout.close();

                return false;
            }
        });
        //hien thi chuc nang admin-thuthu
        SharedPreferences sharedPreferences=getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaitaikhoan= sharedPreferences.getString("loaitaikhoan","");
        if (!loaitaikhoan.equals("admin")){
            check=1;
        }
        String tenuser= sharedPreferences.getString("hoten","");
        txttenuser.setText("Xin Chao "+tenuser);
        if (savedInstanceState==null && check==0){
            fragment=new Fragment_Admin_QLTT();
        }else{
            fragment=new Fragment_main();
            Menu menu=navigationView.getMenu();
            menu.findItem(R.id.menuitemtop10).setVisible(false);
            menu.findItem(R.id.menuitemdoanhthu).setVisible(false);
            menu.findItem(R.id.menuitemdoanhthuthuthu).setVisible(false);
            menu.findItem(R.id.menuitemtaotaikhoan).setVisible(false);

        }
        if(fragment!=null){
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        }



    }

    private void reload(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences=getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaitaikhoan= sharedPreferences.getString("loaitaikhoan","");
        if (loaitaikhoan.equals("admin")){
            fragment=new Fragment_Admin_QLTT();
        }else{
            fragment=new Fragment_main();
        }

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,  R.string.close);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
    private void showdialogdoimatkhau(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_doimatkhau);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        edtmkcu=dialog.findViewById(R.id.edtmkcu);
        edtmkmoi=dialog.findViewById(R.id.edtmkmoi);
        edtmkmoilan2=dialog.findViewById(R.id.edtmkmoilan2);
        Button btndoimatkhau=dialog.findViewById(R.id.btndoimatkhau);
        Button btnhuydoimatkhau=dialog.findViewById(R.id.btnhuydoimatkhau);

        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThongBao();
            }
        });
        btnhuydoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    private void DoiMatKhau(){
        String oldpass=edtmkcu.getText().toString();
        String newpass=edtmkmoi.getText().toString();
        String renewpass=edtmkmoilan2.getText().toString();
        if (newpass.equals(renewpass)){
            SharedPreferences sharedPreferences=getSharedPreferences("THONGTIN",MODE_PRIVATE);
            String mauser=sharedPreferences.getString("mauser","");
            NguoiDungDAO nguoiDungDAO=new NguoiDungDAO(MainActivity.this);
            boolean check=nguoiDungDAO.capnhatmatkhau(mauser,oldpass,newpass);
            if (check){
                Toast.makeText(MainActivity.this, "Cap Nhat thanh cong!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter1, R.anim.anim_exit1);
            }else{
                Toast.makeText(MainActivity.this, "Cap Nhat That Bai!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Nhap mk ko trung nhau!", Toast.LENGTH_SHORT).show();
        }
    }
    private void dialogThongBao(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thongbao_doimatkhau);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button btndongy=dialog.findViewById(R.id.btndongy);
        Button btnhuy=dialog.findViewById(R.id.btnhuy);
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoiMatKhau();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void showdialogTTcanhan(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_capnhatthongtintt);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        edthotenttadmin=dialog.findViewById(R.id.edthotenttadmin);
        edtnamsinhttadmin=dialog.findViewById(R.id.edtnamsinhttadmin);
        edtdiachittadmin=dialog.findViewById(R.id.edtdiachittadmin);
        Button btncapnhatttcanhan=dialog.findViewById(R.id.btncapnhatttcanhan);
        Button btnhuycapnhatttcanhan=dialog.findViewById(R.id.btnhuycapnhatttcanhan);
        SharedPreferences sharedPreferences=getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String hotennow=sharedPreferences.getString("hoten","");
        String namsinhnow=sharedPreferences.getString("namsinh","");
        String diachinow=sharedPreferences.getString("diachi","");
        edthotenttadmin.setText(hotennow);
        edtnamsinhttadmin.setText(namsinhnow);
        edtdiachittadmin.setText(diachinow);
        btnhuycapnhatttcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btncapnhatttcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogthongbaott();
            }
        });
        dialog.show();
    }
    private void dialogthongbaott(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thongbao_capnhat_tt);

        Window window=dialog.getWindow();

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button btndongycapnhattt=dialog.findViewById(R.id.btndongycapnhattt);
        Button btnhuycapnhattt=dialog.findViewById(R.id.btnhuycapnhattt);

        btnhuycapnhattt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btndongycapnhattt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ht=edthotenttadmin.getText().toString();
                String ns=edtnamsinhttadmin.getText().toString();
                String dc=edtdiachittadmin.getText().toString();
                NguoiDungDAO nguoiDungDAO=new NguoiDungDAO(MainActivity.this);
                SharedPreferences sharedPreferences=getSharedPreferences("THONGTIN",MODE_PRIVATE);
                String mausercheck=sharedPreferences.getString("mauser","");
                boolean checkCN=nguoiDungDAO.capnhatThongTinTT(mausercheck,ht,ns,dc);
                if (checkCN){
                    Toast.makeText(MainActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter1, R.anim.anim_exit1);
                }else{
                    Toast.makeText(MainActivity.this, "Cập nhật Thất Bại!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }
}