package com.example.duanmau_application.Model;

import java.io.Serializable;

public class LoaiSach implements Serializable {
    int code;
    String name;

    public LoaiSach(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public LoaiSach(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
