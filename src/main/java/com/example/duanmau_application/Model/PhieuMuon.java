package com.example.duanmau_application.Model;

public class PhieuMuon {
    int mapm;
    int matv;
    String mauser;
    int masach;
    String ngay;
    int trangthai;
    int giathue;
    String tentv;
    String tenuser;
    String tensach;
    int tongdoanhthu;
    int soluongdachomuon;

    public PhieuMuon(int matv, String mauser, int masach, String ngay, int trangthai, int giathue) {
        this.matv = matv;
        this.mauser = mauser;
        this.masach = masach;
        this.ngay = ngay;
        this.trangthai = trangthai;
        this.giathue = giathue;
    }
//                    pm.mapm, pm.matv, tv.hoten, pm.mauser, user.hoten, pm.masach, sc.tensach, pm.ngaythue, pm.trangthai, pm.giathue
    public PhieuMuon(int mapm, int matv, String tentv, String mauser, String tenuser, int masach, String tensach, String ngay, int trangthai, int giathue) {
        this.mapm = mapm;
        this.matv = matv;
        this.mauser = mauser;
        this.masach = masach;
        this.ngay = ngay;
        this.trangthai = trangthai;
        this.giathue = giathue;
        this.tentv = tentv;
        this.tenuser = tenuser;
        this.tensach = tensach;
    }

    public PhieuMuon(String mauser, String tenuser, int tongdoanhthu) {
        this.mauser = mauser;
        this.tongdoanhthu = tongdoanhthu;
        this.tenuser = tenuser;
    }

    public PhieuMuon(int soluongdachomuon,String mauser, String tenuser, int tongdoanhthu) {
        this.mauser = mauser;
        this.tenuser = tenuser;
        this.tongdoanhthu = tongdoanhthu;
        this.soluongdachomuon = soluongdachomuon;
    }

    public int getSoluongdachomuon() {
        return soluongdachomuon;
    }

    public void setSoluongdachomuon(int soluongdachomuon) {
        this.soluongdachomuon = soluongdachomuon;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getTongdoanhthu() {
        return tongdoanhthu;
    }

    public void setTongdoanhthu(int tongdoanhthu) {
        this.tongdoanhthu = tongdoanhthu;
    }
}
