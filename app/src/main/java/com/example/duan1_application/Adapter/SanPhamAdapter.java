package com.example.duan1_application.Adapter;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_application.R;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.HangSP;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.SanPham;
import com.example.duan1_application.model.Size;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<SanPham> list;
    private ArrayList<SanPham> listsearch;
    ServiceAPI requestInterface;
    private ArrayList<HashMap<String,Object>> listHM;



    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        this.listsearch=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sanpham,parent,false);
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getHinhanh())
                .centerCrop()
                .into(holder.ivHinh);
        holder.txtTen.setText(list.get(position).getTenSp());
        holder.txtGia.setText(String.valueOf(list.get(position).getGia()));
        holder.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search=charSequence.toString();
                if (search.isEmpty()){
                    list=listsearch;
                }else {
                    ArrayList<SanPham>listsp=new ArrayList<>();
                    for (SanPham sp:listsearch){
                        String giasearch=String.valueOf(sp.getGia());
                        if(sp.getTenSp().toLowerCase().contains(search.toLowerCase())||giasearch.contains(search)||sp.getTenhang().toLowerCase().contains(search.toLowerCase())){
                            listsp.add(sp);
                        }
                    }
                    list=listsp;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list=(ArrayList<SanPham>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinh;
        TextView txtTen,txtGia;
        Button btnDatHang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh = itemView.findViewById(R.id.ivHinh);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtGia = itemView.findViewById(R.id.txtGia);
            btnDatHang = itemView.findViewById(R.id.btnDatHang);
        }
    }
    private void showDiaLog(SanPham sanPham){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sanpham);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView txtTen = dialog.findViewById(R.id.txtTenDialog);
        TextView txtgia = dialog.findViewById(R.id.txtGiaDialog);
        EditText edtSoLuong = dialog.findViewById(R.id.edtsoLuong);
        EditText edtsdt = dialog.findViewById(R.id.edtsdt);
        EditText edtdiaChi = dialog.findViewById(R.id.edtDiachi);
        ImageView ivHinh = dialog.findViewById(R.id.ivHinhDiaLog);
        Button btnDatHang = dialog.findViewById(R.id.btnDatHangDiaLog);
        Spinner spinner = dialog.findViewById(R.id.spinner);


        txtTen.setText(sanPham.getTenSp());
        txtgia.setText("Giá sản phẩm: "+sanPham.getGia());

        Glide.with(context)
                .load(sanPham.getHinhanh())
                .centerCrop()
                .into(ivHinh);
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= context.getSharedPreferences("KHACHHANG",Context.MODE_PRIVATE);
                int makh=sharedPreferences.getInt("makh",-1);
                String SDT = edtsdt.getText().toString();
                String DiaChi = edtdiaChi.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String ngay= simpleDateFormat.format(currentTime);
                int sol = Integer.parseInt(edtSoLuong.getText().toString());
                int gia = sanPham.getGia()*sol;
                HashMap<String,Object> hsm= (HashMap<String, Object>) spinner.getSelectedItem();
                String masize = (String) hsm.get("masize");
                HoaDon hoaDon = new HoaDon(makh,0,SDT,DiaChi,gia,ngay,sanPham.getMaSp(),sol,masize);
                new CompositeDisposable().add(requestInterface.themHoaDon(hoaDon)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError)
                );
                int masp = sanPham.getMaSp();
                new CompositeDisposable().add(requestInterface.getDSSizetheoMaSp(masp)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponseSize, this::handleError)
                );
                dialog.dismiss();
            }

            private void handleResponseSize(ArrayList<Size> list) {
                ArrayList<Size> list1 = list;
                ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();
                for (Size size: list1){
                    HashMap<String,Object> hs = new HashMap<>();
                    hs.put("masize",size.getMasize());
                    listHM.add(hs);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(
                        context,
                        listHM,
                        android.R.layout.simple_list_item_1,
                        new String[]{"masize"},
                        new int[]{android.R.id.text1}
                );
                spinner.setAdapter(simpleAdapter);
            }

            private void handleError(Throwable throwable) {
                Toast.makeText(context, "thất bại", Toast.LENGTH_SHORT).show();
            }

            private void handleResponse(Integer integer) {
                Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }
    private void size(){

    }



}
