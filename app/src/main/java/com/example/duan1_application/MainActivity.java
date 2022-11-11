package com.example.duan1_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.duan1_application.Fragment.Fragment_GioHang;
import com.example.duan1_application.Fragment.Fragment_ThongTinCaNhan;
import com.example.duan1_application.Fragment.Fragment_XemLichSu;
import com.example.duan1_application.Fragment.Frangment_MuaSanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomnavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Frangment_MuaSanPham()).commit();
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.Shop) {
            selectedFragment = new Frangment_MuaSanPham();
        } else if (itemId == R.id.History) {
            selectedFragment = new Fragment_XemLichSu();
        } else if (itemId == R.id.Account) {
            selectedFragment = new Fragment_ThongTinCaNhan();
        }else if (itemId == R.id.Cart){
            selectedFragment = new Fragment_GioHang();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        }
        return true;
    };

}