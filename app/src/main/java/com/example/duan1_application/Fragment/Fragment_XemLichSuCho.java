package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_application.Adapter.ChoDuyetAdapter;
import com.example.duan1_application.R;
import com.example.duan1_application.ShowNotification;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.CTHD;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.ItemClickHuycthd;
import com.example.duan1_application.model.Size;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_XemLichSuCho extends Fragment {
    RecyclerView recyclerView;
    ServiceAPI requestInterface;
    int maHd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_xemlichsucho,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewChoDuyet);
        DemoCallAPI();
        return view;
    }
    private void DemoCallAPI() {
        ShowNotification.showProgressDialog(getContext(),"Vui Lòng Chờ...");
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("KHACHHANG", Context.MODE_PRIVATE);
        int maKH =sharedPreferences.getInt("makh",-1);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.getDSHoaDontheoTrangThaiKH(maKH,0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResponse(ArrayList<HoaDon> hoaDons) {
        ArrayList<HoaDon> listHoaDon = hoaDons;
        Collections.sort(listHoaDon,new Comparator<HoaDon>(){
            @Override
            public int compare(HoaDon hoaDon, HoaDon t1) {

                return hoaDon.getNgayHd().compareTo(t1.getNgayHd());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ChoDuyetAdapter adapter = new ChoDuyetAdapter(listHoaDon, getContext(), new ItemClickHuycthd() {
            @Override
            public void Itemclickxoacthd(HoaDon hoaDon) {
                maHd = hoaDon.getMaHd();
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
                goiCTHD(maHd);
            }
        });

        recyclerView.setAdapter(adapter);
        ShowNotification.dismissProgressDialog();
    }
    private void goiCTHD(int mahd){
        new CompositeDisposable().add(requestInterface.getCTHD(mahd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCTHD, this::handleError)
        );
    }

    private void handleResponseCTHD(ArrayList<CTHD> cthds) {

        for(CTHD cthd: cthds){
            String masize = cthd.getMasize();
            int soLuong = cthd.getSoluong();
            int masp = cthd.getMasp();
            Size size = new Size(masp,masize,soLuong);
            new CompositeDisposable().add(requestInterface.suaSoluongkhiHoanDon(size)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseSize, this::handleError)
            );
        }
    }

    private void handleResponseSize(Integer integer) {
        Toast.makeText(getContext(), "Hủy đơn thành công", Toast.LENGTH_SHORT).show();
        DemoCallAPI();
    }


}
