package com.example.duan1_application.Adapter;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_application.R;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.CTHD;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.ItemClickxoacthd;
import com.example.duan1_application.model.SanPham;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>{
    ImageView ivHinhSP;
    TextView txtTenSP, txtSoLuong;
    private Context context;
    private ArrayList<CTHD> list;
    ServiceAPI requestInterface;
    private ItemClickxoacthd itemClickxoacthd;
    public GioHangAdapter(ArrayList<CTHD> list,Context context,ItemClickxoacthd itemClickxoacthd) {
        this.context = context;
        this.list = list;
        this.itemClickxoacthd=itemClickxoacthd;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycel_giohang,parent,false);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int tien = list.get(position).getGia();
        Locale locale = new Locale("nv", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        String tienformat = nf.format(tien);
        holder.txtGiaSP.setText("Giá SP: "+tienformat);
        Glide.with(context).load(list.get(position).getHinhanh()).centerCrop().into(ivHinhSP);
        txtTenSP.setText(list.get(position).getTenSp());
        txtSoLuong.setText("Số Lượng: "+list.get(position).getSoluong());
        holder.ivxoaitemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickxoacthd.Itemclickxoacthd(list.get(holder.getAdapterPosition()));
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTrangThai,txtTongGia,txtGiaSP;
        Button btnHuy;
        ImageView ivxoaitemgiohang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhSP = itemView.findViewById(R.id.ivHinhSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
   //         txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
  //          btnHuy = itemView.findViewById(R.id.btnHuy);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
   //         txtTongGia = itemView.findViewById(R.id.txtTongGia);
            ivxoaitemgiohang=itemView.findViewById(R.id.ivxoaitemgiohang);

        }
    }
    private void load(){

    }
}
