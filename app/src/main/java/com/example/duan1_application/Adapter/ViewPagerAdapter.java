package com.example.duan1_application.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan1_application.Fragment.Fragment_ThongTinCaNhan;
import com.example.duan1_application.Fragment.Fragment_XemLichSu;
import com.example.duan1_application.Fragment.Frangment_MuaSanPham;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Frangment_MuaSanPham();
            case 1:
                return new Fragment_XemLichSu();
            case 2:
                return new Fragment_ThongTinCaNhan();
            default:
                return new Frangment_MuaSanPham();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Shop";
                break;
            case 1:
                title="Lich Su Giao Dich";
                break;
            case 2:
                title="Thong Tin Ca Nhan";
                break;
            default:
                break;
        }

        return title;
    }
}
