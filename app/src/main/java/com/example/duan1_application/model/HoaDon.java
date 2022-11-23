package com.example.duan1_application.model;

public class HoaDon {
    private int maHd;
    private int maKh;
    private int trangThai;
    private String sdt;
    private String diachi;
    private int triGia;
    private String ngayHd;
    private int maSp;
    private int SoLuong;

    public HoaDon(int maKh, int trangThai, String sdt, String diachi, int triGia, String ngayHd, int maSp, int soLuong) {
        this.maKh = maKh;
        this.trangThai = trangThai;
        this.sdt = sdt;
        this.diachi = diachi;
        this.triGia = triGia;
        this.ngayHd = ngayHd;
        this.maSp = maSp;
        SoLuong = soLuong;
    }

    public int getMaHd() {
        return maHd;
    }

    public void setMaHd(int maHd) {
        this.maHd = maHd;
    }

    public int getMaKh() {
        return maKh;
    }

    public void setMaKh(int maKh) {
        this.maKh = maKh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getTriGia() {
        return triGia;
    }

    public void setTriGia(int triGia) {
        this.triGia = triGia;
    }

    public String getNgayHd() {
        return ngayHd;
    }

    public void setNgayHd(String ngayHd) {
        this.ngayHd = ngayHd;
    }
}
