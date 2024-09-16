package com.poly.duan1.Models;

public class NhanVien {
    private int maNV;
    private String hoTen;
    private String SDT;
    private String taiKhoan;
    private String matKhau;
    private int vaiTro; //0: quan ly    1: nhan vien

    public NhanVien() {
    }

    public NhanVien(int maNV, String hoTen, String SDT, String taiKhoan, String matKhau, int vaiTro) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }
}
