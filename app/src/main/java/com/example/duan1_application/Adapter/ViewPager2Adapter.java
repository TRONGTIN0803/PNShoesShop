package com.example.duan1_application.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_application.Fragment.Fragment_XemLichSu;
import com.example.duan1_application.Fragment.Fragment_XemLichSu1;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0)
            return new Fragment_XemLichSu();
        return new Fragment_XemLichSu1();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
