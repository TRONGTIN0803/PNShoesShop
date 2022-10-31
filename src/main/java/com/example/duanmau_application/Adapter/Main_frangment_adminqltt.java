package com.example.duanmau_application.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.DAO.NguoiDungDAO;
import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.Model.ThanhVien;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class Main_frangment_adminqltt extends RecyclerView.Adapter<Main_frangment_adminqltt.ViewHolder>{

    private Context context;
    private ArrayList<ThanhVien> list;
    private NguoiDungDAO nguoiDungDAO;
    private PhieuMuonDAO phieuMuonDAO;

    public Main_frangment_adminqltt(Context context, ArrayList<ThanhVien> list,NguoiDungDAO nguoiDungDAO ,PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.list = list;
        this.nguoiDungDAO=nguoiDungDAO;
        this.phieuMuonDAO=phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_recycler_adminqltt,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtmauserttadmin.setText("Ma Thủ Thư: "+list.get(position).getMatt());
        holder.txttenuserttadmin.setText("Ten Thủ Thư: "+list.get(position).getTentt());
        holder.txttongdoanhthuttadmin.setText("Địa chỉ: "+list.get(position).getDiachitt());
        holder.txtsolanchomuon.setText("Năm sinh: "+list.get(position).getNamsinhtt());

        holder.ivxoatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check= nguoiDungDAO.xoaTT(list.get(position).getMatt());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoa KO Thanh cong", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Ko the xoa Thủ Thư nay. Thủ Thư này đang cho mượn sách", Toast.LENGTH_SHORT).show();
                        break;
                    default:
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
        ImageView ivxoatt;
        TextView txtmauserttadmin, txttenuserttadmin, txttongdoanhthuttadmin,txtsolanchomuon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmauserttadmin=itemView.findViewById(R.id.txtmauserttadmin);
            txttenuserttadmin=itemView.findViewById(R.id.txttenuserttadmin);
            txttongdoanhthuttadmin=itemView.findViewById(R.id.txttongdoanhthuttadmin);
            txtsolanchomuon=itemView.findViewById(R.id.txtsolanchomuon);
            ivxoatt=itemView.findViewById(R.id.ivxoatt);
        }
    }
    private void loadData(){
        list.clear();
        list=phieuMuonDAO.getDSTTAdmin();
        notifyDataSetChanged();
    }
}
