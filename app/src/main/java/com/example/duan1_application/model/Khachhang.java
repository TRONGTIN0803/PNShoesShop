package com.example.duan1_application.model;

public class Khachhang {
    private int maKh;
    private String tenKh;
    private String sdt;
    private String avt;

    public Khachhang(int maKh, String tenKh, String sdt, String avt) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.sdt = sdt;
        this.avt = avt;
    }

    public Khachhang(String sdt) {
        this.sdt = sdt;
    }

    public int getMaKh() {
        return maKh;
    }

    public void setMaKh(int maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
