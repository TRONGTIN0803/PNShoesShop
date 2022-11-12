package com.example.duan1_application.api;

import com.example.duan1_application.model.SanPham;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServiceAPI {
    String BASE_SERVICE="https://tinlaptrinh.kynalab.com/";

    @GET("api/GetDSSanPham")
    Observable<ArrayList<SanPham>> getDSSanPham();
}
