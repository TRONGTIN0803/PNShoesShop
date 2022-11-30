package com.example.duan1_application;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.duan1_application.Adapter.ChoDuyetAdapter;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.ItemClickHuycthd;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChoDuyetActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ServiceAPI requestInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_duyet);
        recyclerView = findViewById(R.id.recyclerViewChoDuyet);
        DemoCallAPI();
    }
    private void DemoCallAPI() {
        SharedPreferences sharedPreferences = getSharedPreferences("KHACHHANG", Context.MODE_PRIVATE);
        int maKH = sharedPreferences.getInt("makh", -1);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.getDSHoaDontheoTrangThaiKH(maKH, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResponse(ArrayList<HoaDon> hoaDons) {
        ArrayList<HoaDon> listHoaDon = hoaDons;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ChoDuyetAdapter adapter = new ChoDuyetAdapter(listHoaDon, this, new ItemClickHuycthd() {
            @Override
            public void Itemclickxoacthd(HoaDon hoaDon) {
                int maHd = hoaDon.getMaHd();
                int trangthai = -1;
                HoaDon hoaDon1 = new HoaDon(maHd,trangthai);
                new CompositeDisposable().add(requestInterface.thayDoiTrangThai(hoaDon1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError)
                );
            }

            private void handleError(Throwable throwable) {
            }

            private void handleResponse(Integer integer) {
                Toast.makeText(ChoDuyetActivity.this, "Hủy đơn thành công", Toast.LENGTH_SHORT).show();
                DemoCallAPI();
            }
        });
        recyclerView.setAdapter(adapter);
    }

}