package com.example.duanmau_application.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.duanmau_application.Adapter.LoaiSachAdapter;
import com.example.duanmau_application.DAO.LoaiSachDAO;
import com.example.duanmau_application.Model.ItemClick;
import com.example.duanmau_application.Model.LoaiSach;
import com.example.duanmau_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_LoaiSach extends Fragment {
    private LoaiSachDAO loaisachDAO;
    private ArrayList<LoaiSach>list;
    private RecyclerView recyclerViewtheloai;
    private int maloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_loaisach,container,false);
        recyclerViewtheloai=view.findViewById(R.id.recycleviewtheloai);
        FloatingActionButton btnshowdialog=view.findViewById(R.id.btnaddtheloai);


        loadData();
        btnshowdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;
    }
    private void suaThongTin(LoaiSach loaiSach){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_loaisach);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtdialogsuaname=dialog.findViewById(R.id.edtdialogsuaname);
        Button btnsualoaisach=dialog.findViewById(R.id.btnsualoaisach);
        Button btnhuysualoaisach=dialog.findViewById(R.id.btnhuysualoaisach);

        edtdialogsuaname.setText(loaiSach.getName());

        btnhuysualoaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnsualoaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloaisach=edtdialogsuaname.getText().toString();
                LoaiSach theloai=new LoaiSach(maloai,tenloaisach);
                boolean check=loaisachDAO.suaLoaiSach(theloai);
                if (check){
                    Toast.makeText(getContext(), "Sua Thanh Cong!", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Sua That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void loadData(){
        //data
        loaisachDAO=new LoaiSachDAO(getContext());
        list=loaisachDAO.getDSTheLoai();
        //adapter
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewtheloai.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter =new LoaiSachAdapter(getContext(), list, new ItemClick() {
            @Override
            public void OnClick(LoaiSach loaiSach) {
                maloai=loaiSach.getCode();
                suaThongTin(loaiSach);
            }
        });
        recyclerViewtheloai.setAdapter(loaiSachAdapter);
    }
    private void showDialog(){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_dulieu);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtdialogname=dialog.findViewById(R.id.edtdialogname);
        Button btnthemloaisach=dialog.findViewById(R.id.btnthemloaisach);
        Button btnhuythemloaisach=dialog.findViewById(R.id.btnhuythemloaisach);

        btnhuythemloaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnthemloaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentheloai=edtdialogname.getText().toString();
                LoaiSach theloai=new LoaiSach(tentheloai);
                boolean check=loaisachDAO.themLoaiSach(theloai);
                if (check){
                    Toast.makeText(getContext(), "Them Thanh Cong!", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Them That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
