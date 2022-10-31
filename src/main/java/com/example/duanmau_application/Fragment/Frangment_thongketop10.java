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

import com.example.duanmau_application.Adapter.ThongKeTop10Adapter;
import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class Frangment_thongketop10 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thongketop10,container,false);
        RecyclerView recyclerViewtop10=view.findViewById(R.id.recyclertop10);
        SachDAO sachDAO=new SachDAO(getContext());
        ArrayList<Sach>list=sachDAO.getDSTop10();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewtop10.setLayoutManager(linearLayoutManager);
        ThongKeTop10Adapter thongKeTop10Adapter=new ThongKeTop10Adapter(getContext(),list);
        recyclerViewtop10.setAdapter(thongKeTop10Adapter);


        return view;
    }
}
