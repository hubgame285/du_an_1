package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.NhanVien;

import java.util.ArrayList;
import java.util.List;

import static com.poly.duan1.Utility.Constants.*;

public class NhanVienDAO {
    private SQLiteDatabase db;
    public NhanVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //lây tất cả
    public List<NhanVien> get(){
        List<NhanVien> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NHANVIEN + " ";
        Cursor cursor = db.rawQuery(query,null);
        try{
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast()==false){
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_MA_NHAN_VIEN));
                    String _hoTen = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_HO_TEN));
                    String _sdt = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_SO_DIEN_THOAI));
                    String _tenDN = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_TEN_DANG_NHAP));
                    String _matKhau = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_MAT_KHAU));
                    int _vaiTro = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_VAI_TRO));
                    NhanVien nhanVien = new NhanVien(_maNV,_hoTen,_sdt,_tenDN,_matKhau,_vaiTro);
                    list.add(nhanVien);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e){
            Log.e("Get NHANVIEN eror: ",e.getMessage());
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }
// lấy dữ liệu theo id
    public NhanVien getById(int id) {
        NhanVien nhanVien = null;
        String query = "SELECT * FROM " + TABLE_NHANVIEN + " WHERE " + COLUMN_NHANVIEN_MA_NHAN_VIEN + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_MA_NHAN_VIEN));
                    String _hoTen = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_HO_TEN));
                    String _sdt = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_SO_DIEN_THOAI));
                    String _tenDN = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_TEN_DANG_NHAP));
                    String _matKhau = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_MAT_KHAU));
                    int _vaiTro = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_VAI_TRO));
                    nhanVien = new NhanVien(_maNV,_hoTen,_sdt,_tenDN,_matKhau,_vaiTro);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get ID NHANVIEN eror: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return nhanVien;
    }

// thêm dữ liệu
        public void insert(NhanVien nhanVien) {

            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(COLUMN_NHANVIEN_HO_TEN, nhanVien.getHoTen());
                values.put(COLUMN_NHANVIEN_SO_DIEN_THOAI, nhanVien.getSDT());
                values.put(COLUMN_NHANVIEN_TEN_DANG_NHAP, nhanVien.getTaiKhoan());
                values.put(COLUMN_NHANVIEN_MAT_KHAU, nhanVien.getMatKhau());
                values.put(COLUMN_NHANVIEN_VAI_TRO, nhanVien.getVaiTro());
                db.insert(TABLE_NHANVIEN,null,values);
                db.setTransactionSuccessful();
            }catch (Exception e){
                Log.e("Insert NHANVIEN eror: ",e.getMessage());
            }finally {
                db.endTransaction();
            }
        }

        // cập nhật dữ liệu
    public void update(NhanVien nhanVien) {

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NHANVIEN_HO_TEN, nhanVien.getHoTen());
            values.put(COLUMN_NHANVIEN_SO_DIEN_THOAI, nhanVien.getSDT());
            values.put(COLUMN_NHANVIEN_TEN_DANG_NHAP, nhanVien.getTaiKhoan());
            values.put(COLUMN_NHANVIEN_MAT_KHAU, nhanVien.getMatKhau());
            values.put(COLUMN_NHANVIEN_VAI_TRO, nhanVien.getVaiTro());
            db.update(TABLE_NHANVIEN,values,COLUMN_NHANVIEN_MA_NHAN_VIEN + " = ? ", new String[]{String.valueOf(nhanVien.getMaNV())});
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Update NHANVIEN eror: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }
    // xóa dữ liệu
    public void delete(Integer id) {

        db.beginTransaction();
        try {
            db.delete(TABLE_NHANVIEN, COLUMN_NHANVIEN_MA_NHAN_VIEN + " = ? ", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Delete NHANVIEN eror: ", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<NhanVien> getData(String sql, String...selectionArgs){
        List<NhanVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_MA_NHAN_VIEN)));
            nhanVien.setHoTen(cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_HO_TEN)));
            nhanVien.setSDT(cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_SO_DIEN_THOAI)));
            nhanVien.setTaiKhoan(cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_TEN_DANG_NHAP)));
            nhanVien.setMatKhau(cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_MAT_KHAU)));
            nhanVien.setVaiTro(cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_VAI_TRO)));
            list.add(nhanVien);
        }
        return list;
    }

    // kiểm tra đăng nhập
    public int checkLogin(String user, String pass){
        String query = "SELECT * FROM " + TABLE_NHANVIEN + " WHERE " + COLUMN_NHANVIEN_TEN_DANG_NHAP + " = ? AND " + COLUMN_NHANVIEN_MAT_KHAU + " = ? ";
        List<NhanVien> list = getData(query,user,pass);
        if (list.size() == 0){
            return -1;
        }
        return 1;
    }
    //đổi mật mật khẩu
    public int updatePass(NhanVien nhanVien){

        ContentValues values = new ContentValues();
//        values.put(COLUMN_NHANVIEN_HO_TEN, nhanVien.getHoTen());
//        values.put(COLUMN_NHANVIEN_SO_DIEN_THOAI, nhanVien.getSDT());
//        values.put(COLUMN_NHANVIEN_TEN_DANG_NHAP, nhanVien.getTaiKhoan());
        values.put(COLUMN_NHANVIEN_MAT_KHAU, nhanVien.getMatKhau().toString());
//        values.put(COLUMN_NHANVIEN_VAI_TRO, nhanVien.getVaiTro());
        db.update(TABLE_NHANVIEN,values,COLUMN_NHANVIEN_MA_NHAN_VIEN + " = ? ", new String[]{String.valueOf(nhanVien.getMaNV())});
        return 1;
    }

    // lây dữ liệu theo tài khoản
    public NhanVien getByUser(String user){
        NhanVien nhanVien = null;
        String query = "SELECT * FROM " + TABLE_NHANVIEN  + " WHERE " + COLUMN_NHANVIEN_TEN_DANG_NHAP + " = ? ";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(user)});
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_MA_NHAN_VIEN));
                String _hoTen = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_HO_TEN));
                String _sdt = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_SO_DIEN_THOAI));
                String _tenDN = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_TEN_DANG_NHAP));
                String _matKhau = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_MAT_KHAU));
                int _vaiTro = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_VAI_TRO));
                nhanVien = new NhanVien(_maNV,_hoTen,_sdt,_tenDN,_matKhau,_vaiTro);
                cursor.moveToNext();
            }
        }
        return nhanVien;
    }

    public NhanVien getByTK(String TK) {
        NhanVien nhanVien = null;
        String query = "SELECT * FROM " + TABLE_NHANVIEN + " WHERE " + COLUMN_NHANVIEN_TEN_DANG_NHAP + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{TK});
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_MA_NHAN_VIEN));
                    String _hoTen = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_HO_TEN));
                    String _sdt = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_SO_DIEN_THOAI));
                    String _tenDN = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_TEN_DANG_NHAP));
                    String _matKhau = cursor.getString(cursor.getColumnIndex(COLUMN_NHANVIEN_MAT_KHAU));
                    int _vaiTro = cursor.getInt(cursor.getColumnIndex(COLUMN_NHANVIEN_VAI_TRO));
                    nhanVien = new NhanVien(_maNV,_hoTen,_sdt,_tenDN,_matKhau,_vaiTro);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get ID NHANVIEN eror: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return nhanVien;
    }
}
