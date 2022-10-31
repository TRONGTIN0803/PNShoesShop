package com.example.duanmau_application.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.DAO.LoaiSachDAO;
import com.example.duanmau_application.Model.ItemClick;
import com.example.duanmau_application.Model.LoaiSach;
import com.example.duanmau_application.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<LoaiSach>list;
    private ItemClick itemClick;


    public LoaiSachAdapter(Context context, ArrayList<LoaiSach>list, ItemClick itemClick){
        this.context=context;
        this.list=list;
        this.itemClick=itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_lv_qlsach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmaloaisach.setText(String.valueOf(list.get(position).getCode()));
        holder.txttenloaisach.setText(String.valueOf(list.get(position).getName()));

        holder.ivdeltheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSachDAO loaisachDAO=new LoaiSachDAO(context);
                int check=loaisachDAO.deleteTheLoai(list.get(holder.getAdapterPosition()).getCode());
                switch (check){
                    case 1:
                        list.clear();
                        list=loaisachDAO.getDSTheLoai();
                        notifyDataSetChanged();
                        break;
                    case -1:
                        Toast.makeText(context, "Ko the xoa", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        holder.ivsualoaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.OnClick(list.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtmaloaisach, txttenloaisach;
        private ImageView ivdeltheloai, ivsualoaisach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaloaisach=itemView.findViewById(R.id.txtmaloaisach);
            txttenloaisach=itemView.findViewById(R.id.txttenloaisach);
            ivdeltheloai=itemView.findViewById(R.id.ivdeltheloai);
            ivsualoaisach=itemView.findViewById(R.id.ivsuathongtin);
        }
    }


}
