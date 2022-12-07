package com.example.duan1_application.Adapter;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder>{
    ServiceAPI requestInterface;
    private Context context;
    private ArrayList<HoaDon> list;
    TextView ten,soluong,gia;
    ImageView hinhanh;
    RecyclerView rcldialog;
    Dialog dialog;
    ArrayList<CTHD> listct;
    int trangthai;
    public LichSuAdapter(ArrayList<HoaDon> list, Context context,int trangthai) {
        this.context = context;
        this.list = list;
        this.trangthai=trangthai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_lichsu,parent,false);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list.get(position).getTrangThai() == 0) {
            holder.txtTrangThai.setText("Đang sử lý");
        } else if (list.get(position).getTrangThai() == 1) {
            holder.txtTrangThai.setText("Đã duyệt");
        } else if (list.get(position).getTrangThai() == -1) {
            holder.txtTrangThai.setText("Đã hủy đơn ");
        }
        holder.txtSdt.setText(list.get(position).getSdt());
        holder.txtDiaChi.setText(list.get(position).getDiachi());
        holder.txtNgay.setText(list.get(position).getNgayHd());
        int tien = list.get(position).getTriGia();
        Locale locale = new Locale("nv", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        String tienformat = nf.format(tien);
        holder.txtGiaTri.setText(tienformat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mahd = list.get(holder.getAdapterPosition()).getMaHd();
                trangthai=list.get(holder.getAdapterPosition()).getTrangThai();
                showdialog(mahd,trangthai);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTrangThai,txtSdt,txtDiaChi,txtNgay,txtGiaTri;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtNgay= itemView.findViewById(R.id.txtNgay);
            txtGiaTri = itemView.findViewById(R.id.txtGiaTri);
        }
    }
    public void showdialog(int mahd,int trangthai){
        dialog =new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_hdct);
        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        new CompositeDisposable().add(requestInterface.getCTHD(mahd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseshow, this::handleErrorshow)
        );
        ten=dialog.findViewById(R.id.tvTenHDCT);
        hinhanh=dialog.findViewById(R.id.imgViewHDCT);
        gia=dialog.findViewById(R.id.tvGiaHDCT);
        soluong=dialog.findViewById(R.id.tvSoluongHDCT);
        rcldialog=dialog.findViewById(R.id.rcldialoghdct);
        dialog.show();
    }



    private void handleResponseshow(ArrayList<CTHD> hdcts) {
        listct = hdcts;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        rcldialog.setLayoutManager(linearLayoutManager);
        DialogCTHD dialogAdapter=new DialogCTHD(context,listct,trangthai);
        rcldialog.setAdapter(dialogAdapter);
    }

    private void handleErrorshow(Throwable throwable) {

    }
}
