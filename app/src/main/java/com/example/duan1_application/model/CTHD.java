package com.example.duan1_application.model;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

public class CTHD implements Serializable {
  private int maCthd;
  private String hinhanh;
  private int maHd;
  private String tenSp;
  private int maSp;
  private int soluong;
  private String masize;
  private int gia;

  public CTHD(int macthd) {
    this.maCthd = macthd;
  }

  public CTHD(int mahd, int masp, int soluong, String masize) {
    this.maHd = mahd;
    this.maSp = masp;
    this.soluong = soluong;
    this.masize = masize;
  }

  public int getMacthd() {
    return maCthd;
  }

  public void setMacthd(int macthd) {
    this.maCthd = macthd;
  }

  public String getHinhanh() {
    return hinhanh;
  }

  public void setHinhanh(String hinhanh) {
    this.hinhanh = hinhanh;
  }

  public String getTenSp() {
    return tenSp;
  }

  public void setTenSp(String tenSp) {
    this.tenSp = tenSp;
  }

  public int getSoluong() {
    return soluong;
  }

  public void setSoluong(int soluong) {
    this.soluong = soluong;
  }

  public int getGia() {
    return gia;
  }

  public void setGia(int gia) {
    this.gia = gia;
  }

  public int getMahd() {
    return maHd;
  }

  public void setMahd(int mahd) {
    this.maHd = mahd;
  }

  public int getMasp() {
    return maSp;
  }

  public void setMasp(int masp) {
    this.maSp = masp;
  }

  public String getMasize() {
    return masize;
  }

  public void setMasize(String masize) {
    this.masize = masize;
  }
}
