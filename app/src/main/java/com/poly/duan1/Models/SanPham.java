package com.poly.duan1.Models;

public class SanPham {
    private String maSP;
    private String tenSP;
    private int soLuong;
    private String moTa;
    private int giaTien;
    private int maNSX;
    private byte[] anhSP;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, int soLuong, String moTa, int giaTien, int maNSX, byte[] anhSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.maNSX = maNSX;
        this.anhSP = anhSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(int maNSX) {
        this.maNSX = maNSX;
    }

    public byte[] getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(byte[] anhSP) {
        this.anhSP = anhSP;
    }
}
