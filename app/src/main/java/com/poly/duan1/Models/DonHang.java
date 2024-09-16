package com.poly.duan1.Models;

import java.util.Date;

public class DonHang {
    private int maDH;
    private double tongTien;
    private int trangThai; //0:chua thanh toan    1:da thanh toan
    private Date ngayMua;
    private int maNV;

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public DonHang() {
    }

    public DonHang(int maDH, double tongTien, int trangThai, Date ngayMua, int maNV) {
        this.maDH = maDH;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ngayMua = ngayMua;
        this.maNV = maNV;
    }
}
