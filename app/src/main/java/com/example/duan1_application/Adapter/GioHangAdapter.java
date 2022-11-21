package com.example.duan1_application.Adapter;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

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
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.SanPham;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>{

    private ArrayList<SanPham> arrayList;
    private Context context;

    public GioHangAdapter(ArrayList<SanPham> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycel_giohang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(arrayList.get(position).getHinhanh())
                .centerCrop()
                .into(holder.ivHinhSP);
        holder.txtTenSP.setText(arrayList.get(position).getTenSp());
        holder.txtGiaSP.setText(String.valueOf(arrayList.get(position).getGia()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinhSP,ivTrash;
        TextView txtTenSP,txtGiaSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhSP = itemView.findViewById(R.id.ivHinhSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            ivTrash = itemView.findViewById(R.id.ivTrash);
        }
    }

}
