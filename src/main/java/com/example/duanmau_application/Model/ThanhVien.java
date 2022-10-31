package com.example.duanmau_application.Model;

public class ThanhVien {
    int matv;
    String hoten;
    String namsinh;
    String matt;
    String tentt;
    String namsinhtt;
    String diachitt;

    public ThanhVien(int matv, String hoten, String namsinh) {
        this.matv = matv;
        this.hoten = hoten;
        this.namsinh = namsinh;
    }

    public ThanhVien(String matt, String tentt, String namsinhtt, String diachitt) {
        this.matt = matt;
        this.tentt = tentt;
        this.namsinhtt = namsinhtt;
        this.diachitt = diachitt;
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getTentt() {
        return tentt;
    }

    public void setTentt(String tentt) {
        this.tentt = tentt;
    }

    public String getNamsinhtt() {
        return namsinhtt;
    }

    public void setNamsinhtt(String namsinhtt) {
        this.namsinhtt = namsinhtt;
    }

    public String getDiachitt() {
        return diachitt;
    }

    public void setDiachitt(String diachitt) {
        this.diachitt = diachitt;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }
}
