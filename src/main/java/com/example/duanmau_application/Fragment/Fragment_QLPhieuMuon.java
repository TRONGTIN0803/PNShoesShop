package com.example.duanmau_application.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.Adapter.PhieuMuonAdapter;
import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.DAO.ThanhVienDAO;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.Model.ThanhVien;
import com.example.duanmau_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Fragment_QLPhieuMuon extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    private RecyclerView recyclerViewphieumuon;
    private ArrayList<PhieuMuon>list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_fragment_qlphieumuon,container,false);
        recyclerViewphieumuon=view.findViewById(R.id.recycleviewphieumuon);
        FloatingActionButton btnaddphieumuon=view.findViewById(R.id.btnaddphieumuon);

        loadData();
        btnaddphieumuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLog();
            }
        });
        return view;
    }
    public void showDiaLog(){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_themphieumuon);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Spinner spinerthanhvien=dialog.findViewById(R.id.spinerthanhvien);
        Spinner spinersach=dialog.findViewById(R.id.spinersach);
        Button btnhuythempm=dialog.findViewById(R.id.btnhuythempm);
        Button btnthempm=dialog.findViewById(R.id.btnthempm);

        getDataThanhVien(spinerthanhvien);
        getDataSach(spinersach);

        btnhuythempm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnthempm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object>hstv=(HashMap<String, Object>) spinerthanhvien.getSelectedItem();
                int matv=(int)hstv.get("matv");
                HashMap<String, Object>hssach=(HashMap<String, Object>)spinersach.getSelectedItem();
                int masach=(int)hssach.get("masach");
                int tien=(int)hssach.get("giathue");
                boolean kiemtra=themphieumuon(matv,masach,tien);
                if (kiemtra=true){
                    Toast.makeText(getContext(), "Them Phieu Muon Thanh Cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Them That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        builder.setPositiveButton("Them", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {

//            }
//        });
//        builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
        dialog.show();
    }
    private void getDataThanhVien(Spinner spinerthanhvien){
        ThanhVienDAO thanhVienDAO=new ThanhVienDAO(getContext());
        ArrayList<ThanhVien>list=thanhVienDAO.getDSThanhVien();

        ArrayList<HashMap<String, Object>>listhashmap=new ArrayList<>();
        for (ThanhVien item:list){
            HashMap<String, Object>hs=new HashMap<>();
            hs.put("matv",item.getMatv());
            hs.put("hoten",item.getHoten());
            listhashmap.add(hs);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),listhashmap,R.layout.my_selected_item,new String[]{"hoten"},new int[]{R.id.spinneritemselected});
//        simpleAdapter.setDropDownViewResource(R.layout.my_dropdown_item);
//        ArrayAdapter adapter=new ArrayAdapter(getContext(),R.layout.my_selected_item,listhashmap);
//        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        spinerthanhvien.setAdapter(simpleAdapter);
    }
    private void getDataSach(Spinner spinersach){
        SachDAO sachDAO=new SachDAO(getContext());
        ArrayList<Sach>list=sachDAO.getDSDauSach();

        ArrayList<HashMap<String, Object>>listhashmap=new ArrayList<>();
        for (Sach item:list){
            HashMap<String, Object>hs=new HashMap<>();
            hs.put("masach",item.getMasach());
            hs.put("tensach",item.getTensach());
            hs.put("giathue",item.getGiathue());
            listhashmap.add(hs);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),listhashmap, R.layout.my_selected_item,new String[]{"tensach"},new int[]{R.id.spinneritemselected});
        spinersach.setAdapter(simpleAdapter);
    }
    private boolean themphieumuon(int matv, int masach,int tien){
        //ma ma user
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String mauser=sharedPreferences.getString("mauser","");

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay= simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon=new PhieuMuon(matv,mauser,masach,ngay,0,tien);
        boolean check=phieuMuonDAO.themPhieuMuon(phieuMuon);
        if (check){

            return true;
        }else{

            return false;
        }

    }
    private void loadData(){
        //data
        phieuMuonDAO=new PhieuMuonDAO(getContext());
        list=phieuMuonDAO.getDSPhieuMuon();
        //adapter
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewphieumuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter phieuMuonAdapter=new PhieuMuonAdapter(list,getContext());
        recyclerViewphieumuon.setAdapter(phieuMuonAdapter);

    }
}
