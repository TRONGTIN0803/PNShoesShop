package com.example.duan1_application.Fragment;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.duan1_application.ChoDuyetActivity;
import com.example.duan1_application.R;
import com.example.duan1_application.ShowNotification;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.Khachhang;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_ThongTinCaNhan extends Fragment {
    ServiceAPI requestInterface;
    ImageView ivAVT,ivsuaname,ivshowavt,ivchonanh;
    TextView txtTen,txtSDT;
    private Button btncapnhatavt,btnhuycapnhatavt,btnChoDuyet;
    private Uri imagePath;
    private HashMap config = new HashMap();
    private String link;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thongtincanhan,container,false);
        ivAVT = view.findViewById(R.id.ivAvt);
        txtTen = view.findViewById(R.id.txtTen);
        txtSDT = view.findViewById(R.id.txtSdt);
        ivsuaname=view.findViewById(R.id.ivsuaname);
        btnChoDuyet = view.findViewById(R.id.btnChoDuyet);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Loading, please wait...");
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        DemoCallAPI();
        ivsuaname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDiaLog();
            }
        });

        ivAVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDiaLogAvt();
            }
        });
        btnChoDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChoDuyetActivity.class));
            }
        });
        return view;
    }
    private void DemoCallAPI() {
        ShowNotification.showProgressDialog(getContext(),"Vui Lòng Chờ...");
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

        txtSDT.setText(khachHang.getSdt().substring(0,4)+"******");
        ShowNotification.dismissProgressDialog();
    }

    private void ShowDiaLog(){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_name);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtsuaten=dialog.findViewById(R.id.edtsuaten);
        Button btnsuaten=dialog.findViewById(R.id.btnsuaten);
        Button btnhuysuaten=dialog.findViewById(R.id.btnhuysuaten);

        btnhuysuaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnsuaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= getContext().getSharedPreferences("KHACHHANG",Context.MODE_PRIVATE);
                int ma=sharedPreferences.getInt("makh",-1);
                String ten=edtsuaten.getText().toString();
                Khachhang khachhang=new Khachhang(ma,ten);
                new CompositeDisposable().add(requestInterface.UpdateName(khachhang)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsesuaten, this::handleErrorsuaten)
                );
            }

            private void handleResponsesuaten(Integer integer) {
                Toast.makeText(dialog.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                DemoCallAPI();
            }

            private void handleErrorsuaten(Throwable throwable) {
                Toast.makeText(dialog.getContext(), "Ngành L", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void ShowDiaLogAvt(){
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_capnhat_avt);
        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ivshowavt=dialog.findViewById(R.id.ivshowavt);
        ivchonanh=dialog.findViewById(R.id.ivchonanh);
        btncapnhatavt=dialog.findViewById(R.id.btncapnhatavt);
        btnhuycapnhatavt=dialog.findViewById(R.id.btnhuycapnhatavt);
        btnhuycapnhatavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ivchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImages();
            }
        });
        btncapnhatavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImages();
                dialog.dismiss();
            }
        });
        configCloudinary();

        dialog.show();
    }
    private void chooseImages(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");// if you want to you can use pdf/gif/video
            intent.setAction(Intent.ACTION_GET_CONTENT);
            someActivityResultLauncher.launch(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();
                imagePath = data.getData();
                Glide.with(getActivity()).load(imagePath).into(ivshowavt);

            }
        }
    });
    private void uploadImages(){
        ShowNotification.showProgressDialog(getContext(),"Vui Lòng Chờ...");
        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("CHECK", "onStart");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("CHECK", "onProgress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d("CHECK", "onSuccess");
                //               showlink.setText(resultData.get("url").toString());
                link=resultData.get("url").toString();
                updateAvt(link);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("CHECK", "onError: " + error);
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d("CHECK", "onReschedule: " + error);
            }
        }).dispatch();
    }
    private void configCloudinary() {
        config.put("cloud_name", "tinlaptrinh");
        config.put("api_key", "834638854337368");
        config.put("api_secret", "Yf_os7WWb2y0-ng5EiCDfClzOHw");
        MediaManager.init(getContext(), config);
    }
    private void updateAvt(String s){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("KHACHHANG",Context.MODE_PRIVATE);
        int maKH=sharedPreferences.getInt("makh",-1);
        Khachhang khachhang=new Khachhang(maKH,s,"haha");
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);



        new CompositeDisposable().add(requestInterface.updateAvt(khachhang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseavt, this::handleErroravt)
        );

    }

    private void handleResponseavt(Integer integer) {
        if (integer==1){
            Toast.makeText(getContext(), "Update Thanh cong", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(link).into(ivAVT);
            ShowNotification.dismissProgressDialog();
        }
    }

    private void handleErroravt(Throwable throwable) {
        Toast.makeText(getContext(), "Update Error", Toast.LENGTH_SHORT).show();
        mProgressDialog.dismiss();
    }
}
