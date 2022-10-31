package com.example.duanmau_application.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.DAO.PhieuMuonDAO;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<PhieuMuon>list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_recycler_phieumuon,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmaphieumuon.setText("Ma PM: "+list.get(position).getMapm());
        holder.txtmatv.setText("Ma TV: "+list.get(position).getMatv());
        holder.txttentv.setText("Ten TV: "+list.get(position).getTentv());
        holder.txtmauser.setText("Ma TT: "+list.get(position).getMauser());
        holder.txttenuser.setText("Ten TT: "+list.get(position).getTenuser());
        holder.txtmasach.setText("Ma Sach: "+list.get(position).getMasach());
        holder.txttensach.setText("Ten Sach: "+list.get(position).getTensach());
        holder.txtngaymuon.setText("Ngay Muon: "+list.get(position).getNgay());
        String trangthai="";
        if (list.get(position).getTrangthai()==1){
            trangthai="Da Tra Sach";
            holder.btntrasach.setVisibility(View.GONE);
        }else{
            trangthai="Chua Tra Sach";
            holder.btntrasach.setVisibility(View.VISIBLE);
        }
        holder.txttrangthai.setText("Trang Thai: "+trangthai);
        holder.txtphimuon.setText("Gia: "+list.get(position).getGiathue());
        holder.btntrasach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phieuMuonDAO=new PhieuMuonDAO(context);
                boolean kiemtra=phieuMuonDAO.ThayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if (kiemtra){
                    list.clear();
                    list=phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Thay doi trang thai ko thanh cong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmaphieumuon, txtmatv, txttentv,txtmauser, txttenuser, txtmasach,txttensach,txtngaymuon,txttrangthai,txtphimuon;
        Button btntrasach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaphieumuon=itemView.findViewById(R.id.txtmaphieumuon);
            txtmatv=itemView.findViewById(R.id.txtmatv);
            txttentv=itemView.findViewById(R.id.txttentv);
            txtmauser=itemView.findViewById(R.id.txtmauser);
            txttenuser=itemView.findViewById(R.id.txtnameuser);
            txtmasach=itemView.findViewById(R.id.txtmasach);
            txttensach=itemView.findViewById(R.id.txttensach);
            txtngaymuon=itemView.findViewById(R.id.txtngaymuon);
            txttrangthai=itemView.findViewById(R.id.txttrangthai);
            txtphimuon=itemView.findViewById(R.id.txtphimuon);
            btntrasach=itemView.findViewById(R.id.btntrasach);
        }
    }
}
