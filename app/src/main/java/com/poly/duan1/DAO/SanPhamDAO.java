package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.SanPham;

import java.util.ArrayList;
import java.util.List;

import static com.poly.duan1.Utility.Constants.*;

public class SanPhamDAO {
    private SQLiteDatabase db;
    public SanPhamDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //lây tất cả
    public List<SanPham> get(){
        List<SanPham> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SANPHAM + " ";
        Cursor cursor = db.rawQuery(query,null);
        try{
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast()==false){

                    String _maSP = cursor.getString(cursor.getColumnIndex(COLUMN_SANPHAM_MA_SAN_PHAM));
                    String _tenSP = cursor.getString(cursor.getColumnIndex(COLUMN_SANPHAM_TEN_SAN_PHAM));
                    String _moTa = cursor.getString(cursor.getColumnIndex(COLUMN_SANPHAM_MO_TA));
                    int _soLuong = cursor.getInt(cursor.getColumnIndex(COLUMN_SANPHAM_SO_LUONG));
                    int _maNSX = cursor.getInt(cursor.getColumnIndex(COLUMN_SANPHAM_MA_NHA_SAN_XUAT));
                    int _giaTien = cursor.getInt(cursor.getColumnIndex(COLUMN_SANPHAM_GIA_TIEN));
                    byte[] _anhSP = cursor.getBlob(cursor.getColumnIndex("anhSP"));
                    SanPham sanPham = new SanPham(_maSP,_tenSP,_soLuong,_moTa,_giaTien,_maNSX,_anhSP);
                    list.add(sanPham);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e){
            Log.e("Get SANPHAM eror: ",e.getMessage());
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    //lây theo id
    public SanPham getById(String id) {
        SanPham sanPham = null;
        String query = "SELECT * FROM " + TABLE_SANPHAM + " WHERE " + COLUMN_SANPHAM_MA_SAN_PHAM + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    String _maSP = cursor.getString(cursor.getColumnIndex(COLUMN_SANPHAM_MA_SAN_PHAM));
                    String _tenSP = cursor.getString(cursor.getColumnIndex(COLUMN_SANPHAM_TEN_SAN_PHAM));
                    String _moTa = cursor.getString(cursor.getColumnIndex(COLUMN_SANPHAM_MO_TA));
                    int _soLuong = cursor.getInt(cursor.getColumnIndex(COLUMN_SANPHAM_SO_LUONG));
                    int _maNSX = cursor.getInt(cursor.getColumnIndex(COLUMN_SANPHAM_MA_NHA_SAN_XUAT));
                    int _giaTien = cursor.getInt(cursor.getColumnIndex(COLUMN_SANPHAM_GIA_TIEN));
                    byte[] _anhSP = cursor.getBlob(cursor.getColumnIndex("anhSP"));
                    sanPham = new SanPham(_maSP,_tenSP,_soLuong,_moTa,_giaTien,_maNSX,_anhSP);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get ID SANPHAM eror: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return sanPham;
    }

    //Thêm dữ liệu mới
        public void insert(SanPham sanPham) {

            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                values.put(COLUMN_SANPHAM_MA_SAN_PHAM, sanPham.getMaSP());
                values.put(COLUMN_SANPHAM_TEN_SAN_PHAM, sanPham.getTenSP());
                values.put(COLUMN_SANPHAM_GIA_TIEN, sanPham.getGiaTien());
                values.put(COLUMN_SANPHAM_MA_NHA_SAN_XUAT, sanPham.getMaNSX());
                values.put(COLUMN_SANPHAM_MO_TA, sanPham.getMoTa());
                values.put(COLUMN_SANPHAM_SO_LUONG, sanPham.getSoLuong());
                values.put("anhSP", sanPham.getAnhSP());
                db.insert(TABLE_SANPHAM,null,values);
                db.setTransactionSuccessful();
            }catch (Exception e){
                Log.e("Insert SANPHAM eror: ",e.getMessage());
            }finally {
                db.endTransaction();
            }
        }

    //cập nhật dữ liệu
    public void update(SanPham sanPham) {

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SANPHAM_MA_SAN_PHAM, sanPham.getMaSP());
            values.put(COLUMN_SANPHAM_TEN_SAN_PHAM, sanPham.getTenSP());
            values.put(COLUMN_SANPHAM_GIA_TIEN, sanPham.getGiaTien());
            values.put(COLUMN_SANPHAM_MA_NHA_SAN_XUAT, sanPham.getMaNSX());
            values.put(COLUMN_SANPHAM_MO_TA, sanPham.getMoTa());
            values.put(COLUMN_SANPHAM_SO_LUONG, sanPham.getSoLuong());
            values.put("anhSP", sanPham.getAnhSP());
            db.update(TABLE_SANPHAM,values,COLUMN_SANPHAM_MA_SAN_PHAM+ " = ? ", new String[]{String.valueOf(sanPham.getMaSP())});
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Update SANPHAM eror: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }


    // xóa dữ liệu
    public void delete(String id) {

        db.beginTransaction();
        try {
            db.delete(TABLE_SANPHAM, COLUMN_SANPHAM_MA_SAN_PHAM + " = ? ", new String[]{id});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Delete SANPHAM eror: ", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
