package com.example.duan1_application.model;

public class HangSP {
    private String mahang;
    private String tenhang;

    public HangSP(String mahang, String tenhang) {
        this.mahang = mahang;
        this.tenhang = tenhang;
    }

    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }
}
