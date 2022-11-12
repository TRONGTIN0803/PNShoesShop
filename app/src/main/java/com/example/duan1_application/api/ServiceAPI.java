package com.example.duan1_application.api;

import com.example.duan1_application.model.Khachhang;
import com.example.duan1_application.model.SanPham;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_SERVICE="https://tinlaptrinh.kynalab.com/";

    @GET("api/GetDSSanPham")
    Observable<ArrayList<SanPham>> getDSSanPham();
    @GET("api/checkLogin")
    Observable<Khachhang>checkLogin(@Query("sdt")String sdt);

    @POST("api/Register")
    Observable<Integer>Dangky(@Body Khachhang khachhang);
}
