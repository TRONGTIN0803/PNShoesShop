package com.example.duan1_application.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_application.Adapter.SanPhamAdapter;
import com.example.duan1_application.R;

public class Frangment_MuaSanPham extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_muasanpham,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        loadData();
        return view;
    }
    private void loadData(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        SanPhamAdapter adapter = new SanPhamAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }
}
