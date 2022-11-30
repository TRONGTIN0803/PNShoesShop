package com.example.duan1_application.api;

import com.example.duan1_application.model.CTHD;
import com.example.duan1_application.model.Comment;
import com.example.duan1_application.model.HangSP;
import com.example.duan1_application.model.HoaDon;
import com.example.duan1_application.model.Khachhang;
import com.example.duan1_application.model.KhuyenMai;
import com.example.duan1_application.model.SanPham;
import com.example.duan1_application.model.Size;

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

    @GET("api/getThongtinKH")
    Observable<Khachhang> getKH(@Query("makh")int makh);

    @GET("api/checkLogin")
    Observable<Khachhang>checkLogin(@Query("sdt")String sdt);

    @GET("api/getDSHoaDontheoTrangThaiKH")
    Observable<ArrayList<HoaDon>> getDSHoaDontheoTrangThaiKH(@Query("makh") int makh,@Query("trangthai") int trangthai);

    @GET("api/getCTHD")
    Observable<ArrayList<CTHD>> getCTHD(@Query("mahd")int mahd);

    @GET("api/getDSSizetheoSanPham")
    Observable<ArrayList<Size>> getDSSizetheoMaSp(@Query("masp")int masp);

    @GET("api/getDSHangSP")
    Observable<ArrayList<HangSP>> getDSHangSP();

    @GET("api/getGioHang")
    Observable<HoaDon> getGioHang(@Query("makh")int makh);

    @GET("api/getSoluongSizeSP")
    Observable<ArrayList<Size>>getdssizesp(@Query("masp")int masp);

    @GET("api/getDSKhuyenMai")
    Observable<ArrayList<KhuyenMai>>getDSKhuyenMai();

    @GET("api/getDSComment")
    Observable<ArrayList<Comment>>getdscomment(@Query("masp")int masp);

    @GET("api/getSanPhamtheoMa")
    Observable<SanPham>getsanpham(@Query("masp")int masp);




    @POST("api/Register")
    Observable<Integer>Dangky(@Body Khachhang khachhang);

    @POST("api/CapNhatTenKhachHang")
    Observable<Integer>UpdateName(@Body Khachhang khachhang);

    @POST("api/CapNhatAvtKhachHang")
    Observable<Integer>updateAvt(@Body Khachhang khachHang);

    @POST("api/themHoaDon")
    Observable<Integer> themHoaDon(@Body HoaDon hoaDon);

    @POST("api/ThayDoiTrangThai")
    Observable<Integer> thayDoiTrangThai(@Body HoaDon hoaDon);

    @POST("api/themCTHDGioHang")
    Observable<Integer> themCTHD(@Body CTHD cthd);

    @POST("api/xoaGioHang")
    Observable<Integer> xoaCTHD(@Body CTHD cthd);

    @POST("api/capnhatGiaHDthemCTHD")
    Observable<Integer>tanggiahoadon(@Body HoaDon hoaDon);

    @POST("api/capnhatGiaHDxoaCTHD")
    Observable<Integer>giamgiahoadon(@Body HoaDon hoaDon);

    @POST("api/themvaogiohang")
    Observable<Integer>themvaoGioHang(@Body HoaDon hoaDon);

    @POST("api/thanhtoangiohang")
    Observable<Integer>thanhtoanGioHang(@Body HoaDon hoaDon);

    @POST("api/ThemComment")
    Observable<Integer>themCmt(@Body Comment comment);
}
