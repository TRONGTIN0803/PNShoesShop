package com.example.duanmau_application.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class DoanhthuTTAdapter extends RecyclerView.Adapter<DoanhthuTTAdapter.ViewHolder>{
    private Context context;
    private ArrayList<PhieuMuon> list;

    public DoanhthuTTAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_recycler_doanhthutt,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmausertt.setText("Ma Thủ Thư: "+list.get(position).getMauser());
        holder.txttenusertt.setText("Ten Thủ Thư: "+list.get(position).getTenuser());
        holder.txttongdoanhthutt.setText("Tổng Doanh Thu: "+list.get(position).getTongdoanhthu());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmausertt, txttenusertt, txttongdoanhthutt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmausertt=itemView.findViewById(R.id.txtmausertt);
            txttenusertt=itemView.findViewById(R.id.txttenusertt);
            txttongdoanhthutt=itemView.findViewById(R.id.txttongdoanhthutt);
        }
    }
}
