package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_application.Adapter.GioHangAdapter;
import com.example.duan1_application.Adapter.SanPhamAdapter;
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

public class Fragment_GioHang extends Fragment {
    RecyclerView recyclerView;
    ServiceAPI requestInterface;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_giohang,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        DemoCallAPI();
        return view;
    }

    private void DemoCallAPI() {
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.getDSSanPham()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "Chưa kết nối internet", Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(ArrayList<SanPham> sanPhams) {
        ArrayList<SanPham> list = sanPhams;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        GioHangAdapter adapter = new GioHangAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}
