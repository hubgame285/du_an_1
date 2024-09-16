package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.SanPham;

import java.util.ArrayList;
import java.util.List;

import static com.poly.duan1.Utility.Constants.COLUMN_DONHANG_MA_DON_HANG;
import static com.poly.duan1.Utility.Constants.COLUMN_SANPHAM_GIA_TIEN;
import static com.poly.duan1.Utility.Constants.COLUMN_SANPHAM_MA_NHA_SAN_XUAT;
import static com.poly.duan1.Utility.Constants.COLUMN_SANPHAM_MA_SAN_PHAM;
import static com.poly.duan1.Utility.Constants.COLUMN_SANPHAM_MO_TA;
import static com.poly.duan1.Utility.Constants.COLUMN_SANPHAM_SO_LUONG;
import static com.poly.duan1.Utility.Constants.COLUMN_SANPHAM_TEN_SAN_PHAM;
import static com.poly.duan1.Utility.Constants.TABLE_DONHANGCHITIET;
import static com.poly.duan1.Utility.Constants.TABLE_SANPHAM;

public class DonHangChiTietDAO {
    private SQLiteDatabase db;
    private Context context;
    public DonHangChiTietDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        this.context=context;
    }

    //thêm dữ liệu
    public long insert(DonHangChiTiet obj){
        ContentValues values = new ContentValues();
        values.put("maDH",obj.getMaDH());
        values.put("maSP",obj.getMaSP());
        values.put("soLuongMua",obj.getSoLuongMua());
        values.put("thanhTien",obj.getThanhTien());
        return db.insert("DonHangChiTiet",null,values);
    }

    // cập nhật dữ liệu
    public int update(DonHangChiTiet obj){
        ContentValues values = new ContentValues();
        values.put("maCT",obj.getMaCT());
        values.put("maDH",obj.getMaDH());
        values.put("maSP",obj.getMaSP());
        values.put("soLuongMua",obj.getSoLuongMua());
        values.put("thanhTien",obj.getThanhTien());
        return db.update("DonHangChiTiet",values,"maCT= "+obj.getMaCT(),null);

    }


    //xóa dữ liệu theo id
    public int delete(String id){
        return db.delete("DonHangChiTiet","maCT=?", new String[]{String.valueOf(id)});
    }

    //


    //get data nhieu tham so
    public List<DonHangChiTiet> getData(String sql, String...selectionArgs){
        List<DonHangChiTiet> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            DonHangChiTiet obj = new DonHangChiTiet();
            obj.setMaCT(Integer.parseInt(c.getString(c.getColumnIndex("maCT"))));
            obj.setMaDH(Integer.parseInt(c.getString(c.getColumnIndex("maDH"))));
            obj.setMaSP(c.getString(c.getColumnIndex("maSP")));
            obj.setThanhTien(Integer.parseInt(c.getString(c.getColumnIndex("thanhTien"))));
            obj.setSoLuongMua(Integer.parseInt(c.getString(c.getColumnIndex("soLuongMua"))));
            list.add(obj);
        }
        return list;
    }

    //get tat ca
    public List<DonHangChiTiet> getAll(){
        String sql = "select * from DonHangChiTiet";
        return getData(sql);
    }

    public List<DonHangChiTiet> getBymaHD(String maHD){
        String sql = "select * from DonHangChiTiet where maDH=?";
        return getData(sql, maHD);
    }

    //get theo id
    public DonHangChiTiet getID(String id){
        String sql = "select * from DonHangChiTiet where maCT=?";
        List<DonHangChiTiet> list = getData(sql,id);
        return list.get(0);
    }

    public int getTongTien(String maDH){
        String sqldoanhthu = "select sum(thanhTien) as tongTien from DonHangChiTiet where maDH = ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqldoanhthu, new String[]{maDH});

        while (c.moveToNext()){
            try{
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tongTien"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    public int getTongSL(String maDH){
        String sqldoanhthu = "select sum(soLuongMua) as tongSL from DonHangChiTiet where maDH = ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqldoanhthu, new String[]{maDH});

        while (c.moveToNext()){
            try{
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tongSL"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    public List<DonHangChiTiet> getBymaSP(String maSP){
        String sql = "select * from DonHangChiTiet where maSP=?";
        return getData(sql, maSP);
    }

    //get theo maSP
    public DonHangChiTiet getMaSP(String id){
        String sql = "select * from DonHangChiTiet where maSP=?";
        List<DonHangChiTiet> list = getData(sql,id);
        return list.get(0);
    }

    //get theo maHD
    //lây theo id
    public DonHangChiTiet getByMaHD(String id) {
        DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
        String query = "SELECT * FROM DonHangChiTiet WHERE maDH = ?";
        Cursor c = db.rawQuery(query, new String[]{id});
        try {
            if (c.moveToFirst()) {
                while (c.isAfterLast() == false) {
                    int maCT = c.getInt(c.getColumnIndex("maCT"));
                    int maDH = c.getInt(c.getColumnIndex("maDH"));
                    String maSP = c.getString(c.getColumnIndex("maSP"));
                    int soLuongMua = c.getInt(c.getColumnIndex("soLuongMua"));
                    int thanhTien = c.getInt(c.getColumnIndex("thanhTien"));
                    donHangChiTiet.setMaCT(maCT);
                    donHangChiTiet.setMaDH(maDH);
                    donHangChiTiet.setMaSP(maSP);
                    donHangChiTiet.setSoLuongMua(soLuongMua);
                    donHangChiTiet.setThanhTien(thanhTien);
                    c.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Get MaHD DHCT eror: ", e.getMessage());
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return donHangChiTiet;
    }
}
