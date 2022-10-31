package com.example.duanmau_application.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.Model.PhieuMuon;

import java.util.ArrayList;

public class Main_fragment_adapter extends RecyclerView.Adapter<Main_fragment_adapter.ViewHolder>{
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<PhieuMuon> list;
    private Context context;

    public Main_fragment_adapter(PhieuMuonDAO phieuMuonDAO, ArrayList<PhieuMuon> list, Context context) {
        this.phieuMuonDAO = phieuMuonDAO;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnmainfragmentloaisach, btnmainfragmentsach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
