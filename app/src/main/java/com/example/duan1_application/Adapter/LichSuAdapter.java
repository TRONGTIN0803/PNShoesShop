package com.example.duan1_application.Adapter;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Activity;
import android.content.Context;
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
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder>{
    ServiceAPI requestInterface;
    private Context context;
    private ArrayList<HoaDon> list;
    ImageView ivHinhSP;
    TextView txtTenSP,txtGiaSP, txtSoLuong;
    public LichSuAdapter(ArrayList<HoaDon> list, Context context) {
        this.context = context;
        this.list = list;
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

        if (list.get(position).getTrangThai()==0){
            holder.txtTrangThai.setText("Đang sử lý");
        }else if(list.get(position).getTrangThai()==1){
            holder.txtTrangThai.setText("Đã duyệt");
        }else if(list.get(position).getTrangThai()==-1){
            holder.txtTrangThai.setText("Đã hủy đơn hàng");
        }
        holder.txtTongGia.setText("Tổng giá: "+list.get(position).getTriGia());
        new CompositeDisposable().add(requestInterface.getCTHD(list.get(position).getMaHd())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
    }
    private void handleResponse(CTHD cthd) {
        Glide.with(context).load(String.valueOf(cthd.getHinhanh())).centerCrop().into(ivHinhSP);
        txtTenSP.setText(cthd.getTenSp());
        txtGiaSP.setText("Giá: "+cthd.getGia());
        txtSoLuong.setText("Số Lượng: "+cthd.getSoluong());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTrangThai, txtTongGia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhSP = itemView.findViewById(R.id.ivHinhSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtTongGia = itemView.findViewById(R.id.txtTongGia);
        }
    }

}
