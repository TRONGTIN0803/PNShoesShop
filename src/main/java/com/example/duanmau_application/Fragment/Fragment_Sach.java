package com.example.duanmau_application.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau_application.Adapter.SachAdapter;
import com.example.duanmau_application.DAO.LoaiSachDAO;
import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.LoginActivity;
import com.example.duanmau_application.Model.LoaiSach;
import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class Fragment_Sach extends Fragment {
    private SachDAO sachDAO;
    private RecyclerView recyclerViewsach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__sach,container,false);
        FloatingActionButton btnaddsach=view.findViewById(R.id.btnaddsach);
        recyclerViewsach=view.findViewById(R.id.recycleviewsach);

        sachDAO=new SachDAO(getContext());
        loadData();
        btnaddsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThemSach();
            }
        });


        return view;
    }

    private void showDialogThemSach(){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themsach);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtthemtensach=dialog.findViewById(R.id.edtthemtensach);
        EditText edtthemgiathue=dialog.findViewById(R.id.edtthemgiathue);
        Spinner spnloaisach=dialog.findViewById(R.id.spnthemsach);
        Button btnthemsach=dialog.findViewById(R.id.btnthemsach);
        Button btnhuythemsach=dialog.findViewById(R.id.btnhuythemsach);

        SimpleAdapter simpleAdapter=new SimpleAdapter(
                getContext(),getDSLoaisach(),  R.layout.my_selected_item,
                new String[]{"tenloai"},new int[]{R.id.spinneritemselected});
        spnloaisach.setAdapter(simpleAdapter);

        btnthemsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach=edtthemtensach.getText().toString();

                int giathue=Integer.parseInt(edtthemgiathue.getText().toString());

                HashMap<String, Object>hs=(HashMap<String, Object>) spnloaisach.getSelectedItem();
                int maloai=(int) hs.get("maloai");

                boolean check=sachDAO.themSach(tensach,giathue,maloai);
                if (check){
                    Toast.makeText(getContext(), "Them Thanh Cong!", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Them That Bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnhuythemsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private ArrayList<HashMap<String, Object>>getDSLoaisach(){
        LoaiSachDAO loaiSachDAO=new LoaiSachDAO(getContext());
        ArrayList<LoaiSach>list=loaiSachDAO.getDSTheLoai();
        ArrayList<HashMap<String, Object>>listHM=new ArrayList<>();

        for (LoaiSach loai:list){
            HashMap<String, Object> hs=new HashMap<>();
            hs.put("maloai",loai.getCode());
            hs.put("tenloai",loai.getName());
            listHM.add(hs);
        }
        return listHM;
    }

    private void loadData(){
        ArrayList<Sach>list=sachDAO.getDSDauSach();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewsach.setLayoutManager(linearLayoutManager);
        SachAdapter sachAdapter=new SachAdapter(getContext(),list,getDSLoaisach(),sachDAO);
        recyclerViewsach.setAdapter(sachAdapter);
    }
}