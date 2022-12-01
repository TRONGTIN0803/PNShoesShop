package com.example.duan1_application.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_application.Fragment.Fragment_ThongTinCaNhan;
import com.example.duan1_application.Fragment.Fragment_XemLichSuCho;
import com.example.duan1_application.Fragment.Fragment_XemLichSuHuy;
import com.example.duan1_application.Fragment.Fragment_XemLichSuDuyet;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment ;
        switch (position){
            case 0:
                fragment = new Fragment_XemLichSuCho();
                break;
            case 1:
                fragment = new Fragment_XemLichSuDuyet();
                break;
            case 2:
                fragment = new Fragment_XemLichSuHuy();
                break;
            default:
                fragment = new Fragment_ThongTinCaNhan();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
