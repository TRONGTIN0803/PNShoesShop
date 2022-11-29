package com.example.duan1_application.model;

public class KhuyenMai {
    private String maKm;
    private float giaKm;

    public KhuyenMai(String maKm, float giaKm) {
        this.maKm = maKm;
        this.giaKm = giaKm;
    }

    public String getMaKm() {
        return maKm;
    }

    public void setMaKm(String maKm) {
        this.maKm = maKm;
    }

    public float getGiaKm() {
        return giaKm;
    }

    public void setGiaKm(float giaKm) {
        this.giaKm = giaKm;
    }
}
