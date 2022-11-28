package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_application.Adapter.LichSuAdapter;
import com.example.duan1_application.R;
import com.example.duan1_application.ShowNotification;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.HoaDon;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_XemLichSu1 extends Fragment {
    RecyclerView recyclerView;
    ServiceAPI requestInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_xemlichsu1,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewLichSu);
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
        new CompositeDisposable().add(requestInterface.getDSHoaDontheoTrangThaiKH(maKH,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResponse(ArrayList<HoaDon> hoaDons) {
        ArrayList<HoaDon> listHoaDon = hoaDons;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        LichSuAdapter adapter = new LichSuAdapter(listHoaDon,getContext());
        recyclerView.setAdapter(adapter);
        ShowNotification.dismissProgressDialog();
    }

}
