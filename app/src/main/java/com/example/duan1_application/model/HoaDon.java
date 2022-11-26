package com.example.duan1_application.model;

public class HoaDon {
    private int maHd;
    private int maKh;
    private int trangthai;
    private String sdt;
    private String diachi;
    private int trigia;
    private String ngayHd;
    private int maSp;
    private int SoLuong;
    private String masize;
    private String HinhAnh;

    public HoaDon(int maKh, int trangthai, String sdt, String diachi, int trigia, String ngayHd, int maSp, int soLuong,String masize) {
        this.maKh = maKh;
        this.trangthai = trangthai;
        this.sdt = sdt;
        this.diachi = diachi;
        this.trigia = trigia;
        this.ngayHd = ngayHd;
        this.maSp = maSp;
        this.SoLuong = soLuong;
        this.masize=masize;
    }

    public HoaDon(int maHd, int trangthai) {
        this.maHd = maHd;
        this.trangthai = trangthai;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
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
        return trangthai;
    }

    public void setTrangThai(int trangThai) {
        this.trangthai = trangThai;
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
        return trigia;
    }

    public void setTriGia(int triGia) {
        this.trigia = trigia;
    }

    public String getNgayHd() {
        return ngayHd;
    }

    public void setNgayHd(String ngayHd) {
        this.ngayHd = ngayHd;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getMasize() {
        return masize;
    }

    public void setMasize(String masize) {
        this.masize = masize;
    }
}
