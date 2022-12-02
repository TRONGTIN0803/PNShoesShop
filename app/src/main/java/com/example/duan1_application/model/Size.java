package com.example.duan1_application.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;

public class Size implements Serializable {
  private int maSp;
  private int sosize;
  private String masize;
  private int soluong;

  public Size(int maSp, String masize, int soluong) {
    this.maSp = maSp;
    this.masize = masize;
    this.soluong = soluong;
  }

  public int getMaSp() {
    return maSp;
  }

  public void setMaSp(int maSp) {
    this.maSp = maSp;
  }

  public String getMasize() {
    return masize;
  }

  public void setMasize(String masize) {
    this.masize = masize;
  }

  public int getSoluong() {
    return soluong;
  }

  public void setSoluong(int soluong) {
    this.soluong = soluong;
  }

  public int getSosize() {
    return sosize;
  }

  public void setSosize(int sosize) {
    this.sosize = sosize;
  }
}
