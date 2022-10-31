package com.example.duanmau_application.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.Adapter.ThanhVienAdapter;
import com.example.duanmau_application.DAO.ThanhVienDAO;
import com.example.duanmau_application.Model.ThanhVien;
import com.example.duanmau_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_QLThanhVien extends Fragment {
    private ThanhVienDAO thanhVienDAO;
    private RecyclerView recyclerViewthanhvien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_qlthanhvien,container,false);
        FloatingActionButton btnaddthanhvien=view.findViewById(R.id.btnaddthanhvien);
        recyclerViewthanhvien=view.findViewById(R.id.recycleviewthanhvien);

        thanhVienDAO=new ThanhVienDAO(getContext());
        loadData();

        btnaddthanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLog();
            }
        });

        return view;
    }

    private void showDiaLog(){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_themthanhvien);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edttenthanhvien=dialog.findViewById(R.id.edttenthanhvien);
        EditText edtnamsinh=dialog.findViewById(R.id.edtnamsinh);
        Button btnthemtv=dialog.findViewById(R.id.btnthemtv);
        Button btnhuythemtv=dialog.findViewById(R.id.btnhuythemtv);

        btnthemtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten=edttenthanhvien.getText().toString();
                String namsinh=edtnamsinh.getText().toString();

                boolean check=thanhVienDAO.themTHanhVien(hoten,namsinh);
                if (check) {
                    Toast.makeText(getContext(), "THem Thanh Cong!", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Them That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnhuythemtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadData(){
        ArrayList<ThanhVien>list=thanhVienDAO.getDSThanhVien();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewthanhvien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter=new ThanhVienAdapter(getContext(),list,thanhVienDAO);
        recyclerViewthanhvien.setAdapter(adapter);
    }
}
