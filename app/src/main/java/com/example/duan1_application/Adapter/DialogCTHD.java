package com.example.duan1_application.Adapter;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_application.CommentActivity;
import com.example.duan1_application.R;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.CTHD;
import com.example.duan1_application.model.HoaDon;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogCTHD extends RecyclerView.Adapter<DialogCTHD.ViewHolder> {
    Context context;
    ArrayList<CTHD> list;
    private SharedPreferences sharedPreferences;
    private int makh;
    ServiceAPI requestInterface;
    int mahd,masp;
    TextView txtdanhgia;

    public DialogCTHD(Context context, ArrayList<CTHD> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoadon_chitiet,parent,false);
        sharedPreferences= context.getSharedPreferences("KHACHHANG",Context.MODE_PRIVATE);
        makh=sharedPreferences.getInt("makh",-1);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
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
        mahd=list.get(position).getMahd();
        masp=list.get(position).getMasp();
       GetHoaDon();
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
            txtdanhgia=itemView.findViewById(R.id.txtdanhgia);
        }
    }

    public void GetHoaDon(){
        new CompositeDisposable().add(requestInterface.getDSHoaDontheoTrangThaiKH(makh,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsegettrangthai, this::handleErrorgettrangthai)
        );
    }

    private void handleResponsegettrangthai(ArrayList<HoaDon> hoaDons) {
        ArrayList<HoaDon>listhd=hoaDons;
        for (HoaDon itemhd:listhd){
            if (itemhd.getMaHd()==mahd){
                txtdanhgia.setVisibility(View.VISIBLE);
                String x="Đánh giá";
                String html="<a href=\\\"...\\\">"+ x +"</a>";
                txtdanhgia.setText(android.text.Html.fromHtml(html));

                txtdanhgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, CommentActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("masp",masp);
                        bundle.putInt("makh",makh);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }else {
                txtdanhgia.setVisibility(View.GONE);
            }
        }
    }

    private void handleErrorgettrangthai(Throwable throwable) {

    }
}
