package com.example.duanmau_application.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.Adapter.DoanhthuTTAdapter;
import com.example.duanmau_application.Adapter.ThongKeTop10Adapter;
import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class Fragment_DoanhthuTT extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_doanhthutt,container,false);
        RecyclerView recyclerViewdoanhthutt=view.findViewById(R.id.recyclerdoanhthutt);
        SachDAO sachDAO=new SachDAO(getContext());
        ArrayList<PhieuMuon>list=sachDAO.getDoanhThuThuThu();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewdoanhthutt.setLayoutManager(linearLayoutManager);
        DoanhthuTTAdapter doanhthuTTAdapter=new DoanhthuTTAdapter(getContext(),list);
        recyclerViewdoanhthutt.setAdapter(doanhthuTTAdapter);


        return view;
    }
}
