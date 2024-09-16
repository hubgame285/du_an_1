package com.poly.duan1.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Constants {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "DU_AN_1";

    public static final String TABLE_SANPHAM = "SAN_PHAM";
    public static final String COLUMN_SANPHAM_MA_NHA_SAN_XUAT = "SAN_PHAM_MA_NHA_SAN_XUAT";
    public static final String COLUMN_SANPHAM_MA_SAN_PHAM = "MA_SAN_PHAM";
    public static final String COLUMN_SANPHAM_TEN_SAN_PHAM = "TEN_SAN_PHAM";
    public static final String COLUMN_SANPHAM_GIA_TIEN = "GIA_TIEN";
    public static final String COLUMN_SANPHAM_MO_TA = "MO_TA";
    public static final String COLUMN_SANPHAM_SO_LUONG = "SO_LUONG";

    public static final String TABLE_NHASANXUAT = "NHA_SAN_XUAT";
    public static final String COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT = "MA_NHA_SAN_XUAT";
    public static final String COLUMN_NHASANXUAT_TEN_NHA_SAN_XUAT = "TEN_NHA_SAN_XUAT";

    public static final String TABLE_NHANVIEN = "NHAN_VIEN";
    public static final String COLUMN_NHANVIEN_MA_NHAN_VIEN = "MA_NHAN_VIEN";
    public static final String COLUMN_NHANVIEN_HO_TEN = "HO_TEN";
    public static final String COLUMN_NHANVIEN_SO_DIEN_THOAI = "SO_DIEN_THOAI";
    public static final String COLUMN_NHANVIEN_TEN_DANG_NHAP = "TEN_DANG_NHAP";
    public static final String COLUMN_NHANVIEN_MAT_KHAU = "MAT_KHAU";
    public static final String COLUMN_NHANVIEN_VAI_TRO = "VAI_TRO"; // 0:quản lý, 1:nhân viên

    public static final String TABLE_DONHANG = "DON_HANG";
    public static final String COLUMN_DONHANG_MA_DON_HANG = "MA_DON_HANG";
    public static final String COLUMN_DONHANG_MA_NHAN_VIEN = "DON_HANG_MA_NHAN_VIEN";
    public static final String COLUMN_DONHANG_NGAY_MUA = "NGAY_MUA";
    public static final String COLUMN_DONHANG_TONG_TIEN = "TONG_TIEN";
    public static final String COLUMN_DONHANG_TRANG_THAI = "TRANG_THAI";

    public static final String TABLE_DONHANGCHITIET = "DON_HANG_CHI_TIET";
    public static final String COLUMN_DONHANGCHITIET_MA_CHI_TIET = "MA_DON_HANG_CHI_TIET";
    public static final String COLUMN_DONHANGCHITIET_MA_SAN_PHAM = "DON_HANG_CHI_TIET_MA_SAN_PHAM";
    public static final String COLUMN_DONHANGCHITIET_MA_DON_HANG = "DON_HANG_CHI_TIET_MA_DON_HANG";
    public static final String COLUMN_DONHANGCHITIET_SO_LUONG_MUA = "SO_LUONG_MUA";
    public static final String COLUMN_DONHANGCHITIET_THANH_TIEN = "THANH_TIEN";

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
