package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private SQLiteDatabase db;

    public KhachHangDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //thêm dữ liệu
    public long insert(KhachHang obj){
        ContentValues values = new ContentValues();
        values.put("tenKH",obj.tenKH);
        values.put("SDT",obj.SDT);
        return db.insert("KhachHang",null,values);
    }

    // cập nhật dữ liệu
    public int update(KhachHang obj){
        ContentValues values = new ContentValues();
        values.put("tenKH",obj.tenKH);
        values.put("SDT",obj.SDT);
        return db.update("KhachHang",values,"maKH=?",new String[]{String.valueOf(obj.maKH)});

    }

    //xóa dữ liệu theo id
    public int delete(String id){
        return db.delete("KhachHang","maKH=?", new String[]{id});
    }


    //get data nhieu tham so
    public List<KhachHang> getData(String sql, String...selectionArgs){
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            KhachHang obj = new KhachHang();
            obj.maKH = Integer.parseInt(c.getString(c.getColumnIndex("maKH")));
            obj.tenKH = c.getString(c.getColumnIndex("tenKH"));
            obj.SDT = c.getString(c.getColumnIndex("SDT"));
            list.add(obj);
        }
        return list;
    }

    //get tat ca
    public List<KhachHang> getAll(){
        String sql = "select * from KhachHang";
        return getData(sql);
    }

    //get theo id
    public KhachHang getID(String id){
        String sql = "select * from KhachHang where maKH=?";
        List<KhachHang> list = getData(sql,id);
        return list.get(0);
    }
}
