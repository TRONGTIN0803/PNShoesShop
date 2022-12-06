package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_application.Adapter.SanPhamAdapter;
import com.example.duan1_application.R;
import com.example.duan1_application.ShowNotification;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.CTHD;
import com.example.duan1_application.model.HangSP;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.ItenClick;
import com.example.duan1_application.model.KhuyenMai;
import com.example.duan1_application.model.SanPham;
import com.example.duan1_application.model.Size;
import com.example.duan1_application.model.SuaSoLuong;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Delayed;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frangment_MuaSanPham extends Fragment {
    RecyclerView recyclerView;
    ServiceAPI requestInterface;
    private SanPhamAdapter adapter;
    private SearchView searchView;
    private ArrayList<HangSP>listhangsp;
    private ArrayList<SanPham>list;
    private ArrayList<KhuyenMai>listKM;
    private int soluong;
    int So = 1;
    private Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_muasanpham,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        DemoCallAPI();
        return view;
    }
    private void DemoCallAPI() {
        ShowNotification.showProgressDialog(getContext(),"Vui Lòng Chờ...");
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
        setHasOptionsMenu(true);
        list = sanPhams;
        callAPIKM();

    }
    private void getDSHANGSP(){
        new CompositeDisposable().add(requestInterface.getDSHangSP()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsehangsp, this::handleErrorhangsp)
        );
    }

    private void handleResponsehangsp(ArrayList<HangSP> hangSPS) {
        listhangsp=hangSPS;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new SanPhamAdapter(getContext(), list, new ItenClick() {
            @Override
            public void ItemClick(SanPham sanPham, HoaDon hoaDon) {
                DialogGioHang(sanPham, hoaDon);
            }
        }, new SuaSoLuong() {
            @Override
            public void ItemclickSuaSl(Size size) {
                new CompositeDisposable().add(requestInterface.suaSoLuong(size)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseSize, this::handleError)
                );
            }

            private void handleError(Throwable throwable) {
            }

            private void handleResponseSize(Integer integer) {
                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        for (HangSP item:listhangsp){
            for (SanPham sp:list){
                if (item.getMahang().equals(sp.getMahang())){
                    sp.setTenhang(item.getTenhang());
                }
            }
        }
        ShowNotification.dismissProgressDialog();
        for (SanPham item:list){
            for (KhuyenMai km:listKM){
                if (item.getMaKm()!=null && item.getMaKm().equals(km.getMaKm())){
                    item.setGiacu(item.getGia());
                    float kmne=(item.getGia()*km.getGiaKm())/100;
                    int gia= (int) (item.getGia()-kmne);
                    item.setGia(gia);
                    int giakm=(int) km.getGiaKm();
                    item.setGiakm(giakm);
                }
            }

        }

    }

    private void handleErrorhangsp(Throwable throwable) {
 //       Toast.makeText(getContext(), "Tin Ngu", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);

        SearchManager searchManager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)menu.findItem(R.id.action_seach).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void DialogGioHang(SanPham sanPham,HoaDon hoaDon){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_giohang);
        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView txtTengiohang = dialog.findViewById(R.id.txtTenDialoggiohang);
        TextView txtgiagiohang = dialog.findViewById(R.id.txtGiaDialoggiohang);
        EditText edtSoLuong = dialog.findViewById(R.id.edtsoLuonggiohang);
        ImageView ivHinhgiohang = dialog.findViewById(R.id.ivHinhDiaLoggiohang);
        Button btnthemgiohang = dialog.findViewById(R.id.btnthemgiohangDiaLog);
        spinner = dialog.findViewById(R.id.spinnergiohang);
        Button btnSoTru = dialog.findViewById(R.id.btnSoTru);
        Button btnSoCong = dialog.findViewById(R.id.btnSoCong);
        Button btnhuy=dialog.findViewById(R.id.btnHuyGioHangDiaLog);
        TextView txtsoluongtheosizegiohnag=dialog.findViewById(R.id.txtsoluongtheosizegiohang);

        CallAPISize(sanPham.getMaSp());
        txtTengiohang.setText(sanPham.getTenSp());
        txtgiagiohang.setText(String.valueOf(sanPham.getGia()));
        Glide.with(getContext())
                .load(sanPham.getHinhanh())
                .centerCrop()
                .into(ivHinhgiohang);
        btnSoTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(So > 1){
                    int sotru = So--;
                    edtSoLuong.setText(""+(sotru-1));
                } else {
                    edtSoLuong.setText(""+So);
                }
            }
        });
        btnSoCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soCong = So++;
                edtSoLuong.setText(""+(soCong+1));
            }
        });
        btnthemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mahd=hoaDon.getMaHd();
                int masp=hoaDon.getMaSp();
                soluong=Integer.parseInt(edtSoLuong.getText().toString());
                HashMap<String,Object> hsm= (HashMap<String, Object>) spinner.getSelectedItem();
                String masize = (String) hsm.get("masize");
                int soluongsp=(int)hsm.get("soluong"); 
                CTHD cthd=new CTHD(mahd,masp,soluong,masize);
                if (soluong>soluongsp){
                    Toast.makeText(getContext(), "Số lượng sản phẩm không đủ!", Toast.LENGTH_SHORT).show();
                }else{
                    new CompositeDisposable().add(requestInterface.themCTHD(cthd)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponsegetgiohangadd, this::handleErrorgetgiohangadd)
                    );
                }

            }

            private void handleResponsegetgiohangadd(Integer integer) {
                int mahd=hoaDon.getMaHd();
                int gia=soluong*sanPham.getGia();
                HoaDon hoaDon1=new HoaDon(mahd,1,gia);
                new CompositeDisposable().add(requestInterface.tanggiahoadon(hoaDon1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsetanggiahd, this::handleErrortanggiahd)
                );
            }
            private void handleResponsetanggiahd(Integer integer) {
                Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            private void handleErrortanggiahd(Throwable throwable) {
                Toast.makeText(getContext(), "Tang gia that bai", Toast.LENGTH_SHORT).show();
            }

            private void handleErrorgetgiohangadd(Throwable throwable) {
                Toast.makeText(getContext(), "Tin Ngu!", Toast.LENGTH_SHORT).show();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,Object> hsm= (HashMap<String, Object>) spinner.getSelectedItem();
                soluong= (int) hsm.get("soluong");
                txtsoluongtheosizegiohnag.setText(String.valueOf(soluong));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void CallAPISize(int masp) {

        new CompositeDisposable().add(requestInterface.getdssizesp(masp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsesize, this::handleErrorsize)
        );
    }

    private void handleResponsesize(ArrayList<Size> sizes) {
        ArrayList<Size>list=sizes;
        SimpleAdapter simpleAdapter=new SimpleAdapter(
                getContext(),getDSLoaisach(list),  R.layout.my_selected_item,
                new String[]{"sosize"},new int[]{R.id.spinneritemselected});
        spinner.setAdapter(simpleAdapter);

    }

    private ArrayList<HashMap<String, Object>>getDSLoaisach(ArrayList<Size>list){

        ArrayList<HashMap<String, Object>>listHM=new ArrayList<>();

        for (Size size:list){
            HashMap<String, Object> hs=new HashMap<>();
            hs.put("masize",size.getMasize());
            hs.put("sosize",size.getSosize());
            hs.put("soluong",size.getSoluong());
            listHM.add(hs);
        }
        return listHM;
    }


    private void handleErrorsize(Throwable throwable) {
        Toast.makeText(getContext(), "nganh l", Toast.LENGTH_SHORT).show();
    }

    private void callAPIKM(){
        new CompositeDisposable().add(requestInterface.getDSKhuyenMai()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsekm, this::handleErrorkm)
        );
    }

    private void handleResponsekm(ArrayList<KhuyenMai> khuyenMais) {
        listKM=khuyenMais;
        getDSHANGSP();
    }

    private void handleErrorkm(Throwable throwable) {
        //       Toast.makeText(this, "call km error", Toast.LENGTH_SHORT).show();
    }
}
