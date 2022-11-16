package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.duan1_application.R;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.Khachhang;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_ThongTinCaNhan extends Fragment {
    ServiceAPI requestInterface;
    ImageView ivAVT;
    TextView txtTen,txtSDT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thongtincanhan,container,false);
        ivAVT = view.findViewById(R.id.ivAvt);
        txtTen = view.findViewById(R.id.txtTen);
        txtSDT = view.findViewById(R.id.txtSdt);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        DemoCallAPI();
        return view;
    }
    private void DemoCallAPI() {
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("KHACHHANG",Context.MODE_PRIVATE);
        int makh=sharedPreferences.getInt("makh",-1);
        new CompositeDisposable().add(requestInterface.getKH(makh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResponse(Khachhang khachHang) {
        if (khachHang.getTenKh()==null){
            txtTen.setText("Chưa Xác Định");
        }else{
            txtTen.setText(khachHang.getTenKh());
        }
        if (khachHang.getAvt()==null){
            Glide.with(getContext())
                    .load(R.mipmap.profile)
                    .into(ivAVT);
        }else{
            Glide.with(getContext())
                    .load(khachHang.getAvt())
                    .into(ivAVT);
        }

        txtSDT.setText(khachHang.getSdt());
    }
}
