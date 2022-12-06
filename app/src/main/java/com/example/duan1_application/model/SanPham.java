package com.example.duan1_application.model;

public class SanPham {
    private int maSp;
    private String tenSp;
    private int gia;
    private String mahang;
    private String hinhanh;
    private String tenhang;
    private String maKm;
    private float giacu;
    private int giakm;

    public SanPham(int maSp, String tenSp, int gia, String mahang, String hinhanh) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.gia = gia;
        this.mahang = mahang;
        this.hinhanh = hinhanh;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getMaKm() {
        return maKm;
    }

    public void setMaKm(String maKm) {
        this.maKm = maKm;
    }

    public float getGiacu() {
        return giacu;
    }

    public void setGiacu(float giacu) {
        this.giacu = giacu;
    }

    public int getGiakm() {
        return giakm;
    }

    public void setGiakm(int giakm) {
        this.giakm = giakm;
    }
}
