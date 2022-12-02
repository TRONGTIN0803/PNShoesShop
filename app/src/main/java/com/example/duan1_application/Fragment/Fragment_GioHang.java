package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.duan1_application.ShowNotification;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.CTHD;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.ItemClickxoacthd;
import com.example.duan1_application.model.SanPham;
import com.example.duan1_application.model.Size;
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
    private TextView txtgiagiohang;
    private Button btnthanhtoan;
    String sdt = "";
    int giasp, soluongsp, mahoadon, mahd, giathanhtoan;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giohang, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        txtgiagiohang = view.findViewById(R.id.txtgiagiohang);
        btnthanhtoan = view.findViewById(R.id.btnthanhtoan);
        DemoCallAPI();
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNotification.showProgressDialog(getContext(), "Vui Lòng Chờ...");
                DiaLogDathang();
            }
        });
        return view;
    }

    private void DemoCallAPI() {
        ShowNotification.showProgressDialog(getContext(), "Vui Lòng Chờ...");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("KHACHHANG", Context.MODE_PRIVATE);
        int maKH = sharedPreferences.getInt("makh", -1);
        sdt = sharedPreferences.getString("sdt", "");
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
//        new CompositeDisposable().add(requestInterface.getDSHoaDontheoTrangThaiKH(maKH,0)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse, this::handleError)
//        );
        new CompositeDisposable().add(requestInterface.getGioHang(maKH)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(HoaDon hoaDon) {
        new CompositeDisposable().add(requestInterface.getCTHD(hoaDon.getMaHd())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsecthd, this::handleErrorcthd)
        );
        mahd = hoaDon.getMaHd();
        txtgiagiohang.setText(String.valueOf(hoaDon.getTriGia()));
        giathanhtoan = hoaDon.getTriGia();
//        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int mahd= hoaDon.getMaHd();
//                HoaDon hoaDon1=new HoaDon(mahd,0);
//                new CompositeDisposable().add(requestInterface.thayDoiTrangThai(hoaDon1)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(this::handleResponsetdtt, this::handleErrortdtt)
//                );
//            }
//
//            private void handleResponsetdtt(Integer integer) {
//                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
//            }
//
//            private void handleErrortdtt(Throwable throwable) {
//                Toast.makeText(getContext(), "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void handleResponsecthd(ArrayList<CTHD> cthds) {
        ArrayList<CTHD> list = cthds;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        GioHangAdapter adapter = new GioHangAdapter(list, getContext(), new ItemClickxoacthd() {
            @Override
            public void Itemclickxoacthd(CTHD cthd) {
                int macthd = cthd.getMacthd();
                giasp = cthd.getGia();
                soluongsp = cthd.getSoluong();
                mahoadon = cthd.getMahd();
                CTHD cthd1 = new CTHD(macthd);
                new CompositeDisposable().add(requestInterface.xoaCTHD(cthd)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsexoaitemgiohang, this::handleErrorxoaitemgiohang)
                );
            }

            private void handleResponsexoaitemgiohang(Integer integer) {
                int mahd = mahoadon;
                int gia = soluongsp * giasp;
                HoaDon hoaDon = new HoaDon(mahd, 1, gia);
                new CompositeDisposable().add(requestInterface.giamgiahoadon(hoaDon)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsegiamgiahd, this::handleErrorgiamgiahd)
                );
            }

            private void handleResponsegiamgiahd(Integer integer) {
                Toast.makeText(getContext(), "Xoa thanh cong!", Toast.LENGTH_SHORT).show();
                DemoCallAPI();
            }

            private void handleErrorgiamgiahd(Throwable throwable) {
                Toast.makeText(getContext(), "Tin Ngu", Toast.LENGTH_SHORT).show();
            }

            private void handleErrorxoaitemgiohang(Throwable throwable) {
                Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        ShowNotification.dismissProgressDialog();
    }

    private void handleErrorcthd(Throwable throwable) {

    }

    private void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "Gio hang rong", Toast.LENGTH_SHORT).show();
        ShowNotification.dismissProgressDialog();

    }

    private void DiaLogDathang() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dathang);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        EditText edtsdtdathang = dialog.findViewById(R.id.edtsdtdathang);
        EditText edtdiachidathang = dialog.findViewById(R.id.edtdiachidathang);
        Button btndathangthanhtoan = dialog.findViewById(R.id.btndathangthanhtoan);
        TextView txtgiadathang = dialog.findViewById(R.id.txtgiadathang);

        edtsdtdathang.setText(sdt);
        txtgiadathang.setText(String.valueOf(giathanhtoan));
        ShowNotification.dismissProgressDialog();
        btndathangthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sodt = edtsdtdathang.getText().toString();
                String diachi = edtdiachidathang.getText().toString();
                HoaDon hoaDon = new HoaDon(mahd, 0, sodt, diachi);
                new CompositeDisposable().add(requestInterface.thanhtoanGioHang(hoaDon)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsethanhtoangh, this::handleErrorthanhtoangh)
                );
            }

            private void handleResponsethanhtoangh(Integer integer) {
                goiCTHD(mahd);
                dialog.dismiss();
            }

            private void handleErrorthanhtoangh(Throwable throwable) {
                Toast.makeText(getContext(), "Tin Ngu!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }

    private void goiCTHD(int mahd) {
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
            new CompositeDisposable().add(requestInterface.suaSoLuong(size)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseSize, this::handleError)
            );
        }
    }

    private void handleResponseSize(Integer integer) {
        Toast.makeText(getContext(), "Đặt Hàng Thành Công!", Toast.LENGTH_SHORT).show();

    }

}
