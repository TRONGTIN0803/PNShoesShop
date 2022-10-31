package com.example.duanmau_application.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.DAO.ThanhVienDAO;
import com.example.duanmau_application.Model.LoaiSach;
import com.example.duanmau_application.Model.ThanhVien;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThanhVien>list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list,ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO=thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_recycler_thanhvien,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmathanhvien.setText("Ma TV: "+list.get(position).getMatv());
        holder.txttenthanhvien.setText("Ten TV: "+list.get(position).getHoten());
        holder.txtnamsinh.setText("Năm sinh: "+list.get(position).getNamsinh());

        holder.ivsuathongtinthanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCapNhatThongTin(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivxoathanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check=thanhVienDAO.xoaThanhVien(list.get(holder.getAdapterPosition()).getMatv());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoa That Bai", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Ton Tai Phieu Muon. Ko Dc phep xoa!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmathanhvien, txttenthanhvien, txtnamsinh;
        ImageView ivsuathongtinthanhvien, ivxoathanhvien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmathanhvien=itemView.findViewById(R.id.txtmathanhvien);
            txttenthanhvien=itemView.findViewById(R.id.txttenthanhvien);
            txtnamsinh=itemView.findViewById(R.id.txtnamsinh);
            ivsuathongtinthanhvien=itemView.findViewById(R.id.ivsuathongtinthanhvien);
            ivxoathanhvien=itemView.findViewById(R.id.ivxoathanhvien);
        }
    }

    private void showDialogCapNhatThongTin(ThanhVien thanhVien){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suathongtin_thanhvien);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView txtmatv=dialog.findViewById(R.id.txtmatv);
        EditText edtsuatentanhvien=dialog.findViewById(R.id.edtsuatenthanhvien);
        EditText edtsuanamsinh=dialog.findViewById(R.id.edtsuanamsinh);

        Button btnsuathanhvien=dialog.findViewById(R.id.btnsuathanhvien);
        Button btnhuysuathanhvien=dialog.findViewById(R.id.btnhuysuathanhvien);

        txtmatv.setText("Matv: "+thanhVien.getMatv());
        edtsuatentanhvien.setText(thanhVien.getHoten());
        edtsuanamsinh.setText(thanhVien.getNamsinh());

        btnhuysuathanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnsuathanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten=edtsuatentanhvien.getText().toString();
                String namsinh=edtsuanamsinh.getText().toString();
                int id=thanhVien.getMatv();
                boolean check=thanhVienDAO.suaThongTinThanhVien(id,hoten,namsinh);
                if (check){
                    Toast.makeText(context, "Cap Nhat Thanh Cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else{
                    Toast.makeText(context, "Cap Nhat That Bai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private  void loadData(){
        list.clear();
        list=thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }
}

