package com.example.duan1_application.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_application.R;
import com.example.duan1_application.model.CTHD;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DialogCTHD extends RecyclerView.Adapter<DialogCTHD.ViewHolder> {
    Context context;
    ArrayList<CTHD> list;

    public DialogCTHD(Context context, ArrayList<CTHD> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoadon_chitiet,parent,false);
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ten.setText(list.get(position).getTenSp());
        holder.soluong.setText(String.valueOf(list.get(position).getSoluong()));
        Glide.with(context)
                .load(String.valueOf(list.get(position).getHinhanh()))
                .centerCrop()
                .into(holder.hinhanh);
        int tien = list.get(position).getGia();
        Locale locale = new Locale("nv", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        String tienformat = nf.format(tien);
        holder.gia.setText(tienformat);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ten,soluong,gia;
        ImageView hinhanh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tvTenHDCT);
            soluong = itemView.findViewById(R.id.tvSoluongHDCT);
            gia = itemView.findViewById(R.id.tvGiaHDCT);
            hinhanh= itemView.findViewById(R.id.imgViewHDCT);
        }
    }
}
