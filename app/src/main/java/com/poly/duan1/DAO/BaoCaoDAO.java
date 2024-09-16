package com.poly.duan1.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.duan1.Database.DbHelper;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.Models.Top;

import java.util.ArrayList;
import java.util.List;

import static com.poly.duan1.Utility.Constants.*;

public class BaoCaoDAO {
    private static Context context;
    private static SQLiteDatabase db;
    public BaoCaoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        this.context=context;
    }

    public static List<Top> get(){
        String query = "select maSP, sum(soLuongMua) as tongSoLuong from DonHangChiTiet group by maSP order by tongSoLuong desc limit 10 ";
        List<Top> list = new ArrayList<>();
        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
        Cursor cursor =db.rawQuery(query,null);
        while (cursor.moveToNext()){
            SanPham sanPham = sanPhamDAO.getById(cursor.getString(cursor.getColumnIndex("maSP")));
            String _maSP = cursor.getString(cursor.getColumnIndex("maSP"));
            String _tenSP = sanPham.getTenSP();
            int _soLuong = cursor.getInt(cursor.getColumnIndex("tongSoLuong"));
            byte[] _anh = sanPham.getAnhSP();
            Top top = new Top(_maSP,_tenSP,_soLuong,_anh);
            list.add(top);
        }
        return list;
    }

    public int getDoanhThu(String maSP){
        String query = "SELECT (soLuongMua*thanhTien) AS doanhThu FROM DonHangChiTiet WHERE maSP = ? ";
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery(query, new String[]{maSP});
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    public int getDoanhThu2(String tungay, String denngay){
        String sqldoanhthu = "select sum(TONG_TIEN) as doanhthu from DON_HANG where NGAY_MUA >= ? and NGAY_MUA <= ? ";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqldoanhthu, new String[]{tungay,denngay});

        while (c.moveToNext()){
            try{
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhthu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
