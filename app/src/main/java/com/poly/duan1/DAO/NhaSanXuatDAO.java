package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.NhaSanXuat;

import java.util.ArrayList;
import java.util.List;

import static com.poly.duan1.Utility.Constants.*;

public class NhaSanXuatDAO {
    private SQLiteDatabase db;

    public NhaSanXuatDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //lây tất cả
    public List<NhaSanXuat> get(){
        List<NhaSanXuat> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NHASANXUAT + " ";
        Cursor cursor = db.rawQuery(query,null);
        try{
            if(cursor.moveToFirst()){
                while (cursor.isAfterLast()==false){
                    int _maNSX = cursor.getInt(cursor.getColumnIndex(COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT));
                    String _tenNSX = cursor.getString(cursor.getColumnIndex(COLUMN_NHASANXUAT_TEN_NHA_SAN_XUAT));
                    NhaSanXuat nhaSanXuat = new NhaSanXuat(_maNSX,_tenNSX);
                    list.add(nhaSanXuat);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e){
            Log.e("Get NHASANXUAT eror: ",e.getMessage());
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    //lây theo id
    public NhaSanXuat getById(int id) {
        NhaSanXuat nhaSanXuat = null;
        String query = "SELECT * FROM " + TABLE_NHASANXUAT + " WHERE " + COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    int _maNSX = cursor.getInt(cursor.getColumnIndex(COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT));
                    String _tenNSX = cursor.getString(cursor.getColumnIndex(COLUMN_NHASANXUAT_TEN_NHA_SAN_XUAT));
                    nhaSanXuat = new NhaSanXuat(_maNSX,_tenNSX);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get ID NHASANXUAT eror: ", e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return nhaSanXuat;
    }

    //Thêm dữ liệu mới
    public void insert(NhaSanXuat nhaSanXuat) {

        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_NHASANXUAT_TEN_NHA_SAN_XUAT, nhaSanXuat.getTenNSX());
            db.insert(TABLE_NHASANXUAT,null,values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Insert NHASANXUAT eror: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }
    //cập nhật dữ liệu
    public void update(NhaSanXuat nhaSanXuat) {

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NHASANXUAT_TEN_NHA_SAN_XUAT, nhaSanXuat.getTenNSX());
            db.update(TABLE_NHASANXUAT,values,COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT + " = ? ", new String[]{String.valueOf(nhaSanXuat.getMaNSX())});
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Update NHASANXUAT eror: ",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }
    // xóa dữ liệu
    public void delete(Integer id) {

        db.beginTransaction();
        try {
            db.delete(TABLE_NHASANXUAT, COLUMN_NHASANXUAT_MA_NHA_SAN_XUAT + " = ? ", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Delete NHASANXUAT eror: ", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
