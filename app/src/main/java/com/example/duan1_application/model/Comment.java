package com.example.duan1_application.model;

public class Comment {
    private int maCmt;
    private int maKh;
    private String comments;
    private int maSp;
    private String ngayCmt;
    private String hinhanh;
    private String tenKh;

    public Comment(int maKh, String comments, int maSp, String ngayCmt) {
        this.maKh = maKh;
        this.comments = comments;
        this.maSp = maSp;
        this.ngayCmt = ngayCmt;
    }

    public int getMaCmt() {
        return maCmt;
    }

    public void setMaCmt(int maCmt) {
        this.maCmt = maCmt;
    }

    public int getMaKh() {
        return maKh;
    }

    public void setMaKh(int maKh) {
        this.maKh = maKh;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getNgayCmt() {
        return ngayCmt;
    }

    public void setNgayCmt(String ngayCmt) {
        this.ngayCmt = ngayCmt;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenkh() {
        return tenKh;
    }

    public void setTenkh(String tenkh) {
        this.tenKh = tenkh;
    }
}
