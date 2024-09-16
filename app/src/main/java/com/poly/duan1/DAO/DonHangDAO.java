package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.DonHang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.poly.duan1.Utility.Constants.*;

public class DonHangDAO {
    private SQLiteDatabase db;
    public DonHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // lấy tất cả
    public List<DonHang> get(){
        List<DonHang> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_DONHANG + " ";
        Cursor cursor = db.rawQuery(query,null);
        try{
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast()==false){
                    int _maDH = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_DON_HANG));
                    double _tongTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_DONHANG_TONG_TIEN));
                    int _trangThai = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_TRANG_THAI));
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_NHAN_VIEN));
                    Long ngayMuaInt = cursor.getLong(cursor.getColumnIndex(COLUMN_DONHANG_NGAY_MUA));
                    Date _ngayMua = new Date(ngayMuaInt);
                    DonHang donHang = new DonHang(_maDH,_tongTien,_trangThai,_ngayMua,_maNV);
                    list.add(donHang);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e){
            Log.e("Get DONHANG eror: ",e.getMessage());
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }
    // lay don hang da thanh toan
    public List<DonHang> getDaTT(){
        List<DonHang> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_DONHANG + " where " + COLUMN_DONHANG_TRANG_THAI + " = 1 ";
        Cursor cursor = db.rawQuery(query,null);
        try{
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast()==false){
                    int _maDH = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_DON_HANG));
                    double _tongTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_DONHANG_TONG_TIEN));
                    int _trangThai = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_TRANG_THAI));
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_NHAN_VIEN));
                    Long ngayMuaInt = cursor.getLong(cursor.getColumnIndex(COLUMN_DONHANG_NGAY_MUA));
                    Date _ngayMua = new Date(ngayMuaInt);
                    DonHang donHang = new DonHang(_maDH,_tongTien,_trangThai,_ngayMua,_maNV);
                    list.add(donHang);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e){
            Log.e("Get daTT eror: ",e.getMessage());
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    //lấy theo id
    public DonHang getById(int id) {
        DonHang donHang = null;
        String query = "SELECT * FROM " + TABLE_DONHANG + " WHERE " + COLUMN_DONHANG_MA_DON_HANG + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    int _maDH = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_DON_HANG));
                    double _tongTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_DONHANG_TONG_TIEN));
                    int _trangThai = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_TRANG_THAI));
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_NHAN_VIEN));
                    Long ngayMuaInt = cursor.getLong(cursor.getColumnIndex(COLUMN_DONHANG_NGAY_MUA));
                    Date _ngayMua = new Date(ngayMuaInt);
                    donHang = new DonHang(_maDH,_tongTien,_trangThai,_ngayMua,_maNV);
                    Log.d("xem1>>",ngayMuaInt+"");
                    Log.d("xem2>>",_ngayMua+"");
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get ID DONHANG eror: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return donHang;
    }
    // lay don hang chua thanh toan
    public DonHang getChuaTT() {
        DonHang donHang = null;
        String query = "SELECT * FROM " + TABLE_DONHANG + " WHERE " + COLUMN_DONHANG_TRANG_THAI + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{0+""});
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    int _maDH = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_DON_HANG));
                    double _tongTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_DONHANG_TONG_TIEN));
                    int _trangThai = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_TRANG_THAI));
                    int _maNV = cursor.getInt(cursor.getColumnIndex(COLUMN_DONHANG_MA_NHAN_VIEN));
                    Long ngayMuaInt = cursor.getLong(cursor.getColumnIndex(COLUMN_DONHANG_NGAY_MUA));
                    Date _ngayMua = new Date(ngayMuaInt);
                    donHang = new DonHang(_maDH,_tongTien,_trangThai,_ngayMua,_maNV);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get chuaTT eror: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return donHang;
    }

    //them mới dữ liệu
        public void insert(DonHang donHang) {

            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(COLUMN_DONHANG_MA_NHAN_VIEN, donHang.getMaNV());
                values.put(COLUMN_DONHANG_NGAY_MUA, donHang.getNgayMua().getTime());
                values.put(COLUMN_DONHANG_TONG_TIEN, donHang.getTongTien());
                values.put(COLUMN_DONHANG_TRANG_THAI, donHang.getTrangThai());
                db.insert(TABLE_DONHANG,null,values);
                db.setTransactionSuccessful();
                Log.d("xem >>",donHang.getNgayMua().getTime()+"");
            }catch (Exception e){
                Log.e("Insert DONHANG eror: ",e.getMessage());
            }finally {
                db.endTransaction();
            }
        }

        // cập nhật dữ liệu
    public void update(DonHang donHang) {

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_DONHANG_MA_NHAN_VIEN, donHang.getMaNV());
            values.put(COLUMN_DONHANG_NGAY_MUA, donHang.getNgayMua().getTime());
            values.put(COLUMN_DONHANG_TONG_TIEN, donHang.getTongTien());
            values.put(COLUMN_DONHANG_TRANG_THAI, donHang.getTrangThai());
            db.update(TABLE_DONHANG,values,COLUMN_DONHANG_MA_DON_HANG + " = ? ", new String[]{String.valueOf(donHang.getMaDH())});
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Update DONHANG eror: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }

    //xoa dữ liệu
    public void delete(Integer id) {

        db.beginTransaction();
        try {
            db.delete(TABLE_DONHANG, COLUMN_DONHANG_MA_DON_HANG + " = ? ", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Delete DONHANG eror: ", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
