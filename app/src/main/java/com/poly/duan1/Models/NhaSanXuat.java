package com.poly.duan1.Models;

public class NhaSanXuat {
    private int maNSX;
    private String tenNSX;

    public int getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(int maNSX) {
        this.maNSX = maNSX;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public NhaSanXuat() {
    }

    public NhaSanXuat(int maNSX, String tenNSX) {
        this.maNSX = maNSX;
        this.tenNSX = tenNSX;
    }
}
