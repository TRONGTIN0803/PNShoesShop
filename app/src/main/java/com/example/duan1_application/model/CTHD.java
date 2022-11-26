package com.example.duan1_application.model;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

public class CTHD implements Serializable {
  private String hinhanh;

  private String tenSp;

  private int soluong;

  private int gia;

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
}
