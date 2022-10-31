package com.example.duanmau_application.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.Adapter.PhieuMuonAdapter;
import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Fragment_main extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    private RecyclerView recyclerviewpmthuthu;
    private ArrayList<PhieuMuon> list;
    private Button btnmainfragmentloaisach, btnmainfragmentsach;



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        recyclerviewpmthuthu=view.findViewById(R.id.recyclerviewpmthuthu);


        SharedPreferences sharedPreferences=getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maTT=sharedPreferences.getString("mauser","");

        phieuMuonDAO=new PhieuMuonDAO(getContext());
        list=phieuMuonDAO.getDSPhieuMuonTT(maTT);
        //adapter
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerviewpmthuthu.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter phieuMuonAdapter=new PhieuMuonAdapter(list,getContext());
        recyclerviewpmthuthu.setAdapter(phieuMuonAdapter);

        return view;
    }
}
