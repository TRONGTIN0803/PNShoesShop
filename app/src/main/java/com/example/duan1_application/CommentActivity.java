package com.example.duan1_application;

import static com.example.duan1_application.api.ServiceAPI.BASE_SERVICE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1_application.Adapter.CommentAdapter;
import com.example.duan1_application.api.ServiceAPI;
import com.example.duan1_application.model.Comment;
import com.example.duan1_application.model.Khachhang;
import com.example.duan1_application.model.SanPham;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {

    ImageView ivsanpham;
    ImageButton btnsendcmt;
    RecyclerView rcvComment;
    TextView txttensanpham;
    EditText edtcomment;
    private int masp,makh;
    ServiceAPI requestInterface;
    String hinhanh,tenkh;
    private ArrayList<Comment>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        txttensanpham=findViewById(R.id.txttensanpham);
        rcvComment=findViewById(R.id.rcvComment);
        edtcomment=findViewById(R.id.edtcomment);
        ivsanpham=findViewById(R.id.ivsanpham);
        btnsendcmt=findViewById(R.id.btnsendcmt);

        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        masp=bundle.getInt("masp");
        makh=bundle.getInt("makh");
        CallSanPham(masp);
        CallComment(masp);

        btnsendcmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String body=edtcomment.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String ngay= simpleDateFormat.format(currentTime);
                Comment comment=new Comment(makh,body,masp,ngay);
                new CompositeDisposable().add(requestInterface.themCmt(comment)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsegetspcmt, this::handleErrorgetspcmt)
                );
            }

            private void handleResponsegetspcmt(Integer integer) {
                Toast.makeText(CommentActivity.this, "Them THanh Cong", Toast.LENGTH_SHORT).show();
                edtcomment.setText("");
                CallComment(masp);
            }

            private void handleErrorgetspcmt(Throwable throwable) {
                Toast.makeText(CommentActivity.this, "Tin Ngu!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void CallSanPham(int masp){
        new CompositeDisposable().add(requestInterface.getsanpham(masp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsegetsp, this::handleErrorgetsp)
        );
    }

    private void handleResponsegetsp(SanPham sanPham) {
        txttensanpham.setText(sanPham.getTenSp());
        Glide.with(this)
                .load(sanPham.getHinhanh())
                .centerCrop()
                .into(ivsanpham);
    }
    private void handleErrorgetsp(Throwable throwable) {
    }

    public void CallComment(int masp){
        new CompositeDisposable().add(requestInterface.getdscomment(masp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsegetcmt, this::handleErrorgetsp)
        );
    }

    private void handleResponsegetcmt(ArrayList<Comment> comments) {
        list=comments;
        for (int i=0;i<list.size();i++){
            CallKH(list.get(i).getMaKh());
        }


    }

    public void CallKH(int makh){
        new CompositeDisposable().add(requestInterface.getKH(makh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsegetKH, this::handleErrorgetsp)
        );
    }

    private void handleResponsegetKH(Khachhang khachhang) {
        hinhanh=khachhang.getAvt();
        tenkh=khachhang.getTenKh();

        for (Comment item:list){
            if (item.getMaKh()==khachhang.getMaKh()){
                item.setTenkh(tenkh);
                item.setHinhanh(hinhanh);
            }
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcvComment.setLayoutManager(linearLayoutManager);
        CommentAdapter adapter=new CommentAdapter(this,list);
        rcvComment.setAdapter(adapter);

    }


}