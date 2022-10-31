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

import com.example.duanmau_application.Adapter.Main_frangment_adminqltt;
import com.example.duanmau_application.Adapter.PhieuMuonAdapter;
import com.example.duanmau_application.DAO.NguoiDungDAO;
import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.Model.ThanhVien;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class Fragment_Admin_QLTT extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    private RecyclerView recyclerviewdsthuthu;
    private ArrayList<ThanhVien> list;
    NguoiDungDAO nguoiDungDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_qltt,container,false);

        recyclerviewdsthuthu=view.findViewById(R.id.recyclerviewdsthuthu);
        nguoiDungDAO=new NguoiDungDAO(getContext());
        phieuMuonDAO=new PhieuMuonDAO(getContext());
        list=phieuMuonDAO.getDSTTAdmin();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerviewdsthuthu.setLayoutManager(linearLayoutManager);
        Main_frangment_adminqltt adapter=new Main_frangment_adminqltt(getContext(),list,nguoiDungDAO,phieuMuonDAO);
        recyclerviewdsthuthu.setAdapter(adapter);


        return view;
    }
}
