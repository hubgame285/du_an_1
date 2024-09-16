package com.poly.duan1.Models;

import java.util.Date;

public class DonHangChiTiet {
    private int maCT;
    private int maDH;
    private String maSP;
    private int soLuongMua;
    private int thanhTien;

    public int getMaCT() {
        return maCT;
    }

    public void setMaCT(int maCT) {
        this.maCT = maCT;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public DonHangChiTiet() {
    }

    public DonHangChiTiet(int maCT, int maDH, String maSP, int soLuongMua, int thanhTien) {
        this.maCT = maCT;
        this.maDH = maDH;
        this.maSP = maSP;
        this.soLuongMua = soLuongMua;
        this.thanhTien = thanhTien;
    }
}
