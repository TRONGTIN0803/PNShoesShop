package com.example.duanmau_application.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.R;

import java.util.ArrayList;

import kotlinx.coroutines.flow.internal.SafeCollectorKt;

public class ThongKeTop10Adapter extends RecyclerView.Adapter<ThongKeTop10Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach>list;

    public ThongKeTop10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_recycler_top10,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtmasach.setText("Ma Sach: "+String.valueOf(list.get(position).getMasach()));
        holder.txttensach.setText(list.get(position).getTensach());
        holder.txtsoluongdamuon.setText("So Luong Da Muon: "+String.valueOf(list.get(position).getSoluongmuon()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtmasach,txttensach,txtsoluongdamuon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmasach=itemView.findViewById(R.id.txtmasach);
            txttensach=itemView.findViewById(R.id.txttensach);
            txtsoluongdamuon=itemView.findViewById(R.id.txtsoluongdamuon);
        }
    }
}
