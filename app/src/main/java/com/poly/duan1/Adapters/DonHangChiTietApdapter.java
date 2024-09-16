package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.poly.duan1.Activities.GioHangActivity;
import com.poly.duan1.DAO.SanPhamDAO;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DonHangChiTietApdapter extends ArrayAdapter<DonHangChiTiet> {
    private Context context;
    GioHangActivity activity;
    private ArrayList<DonHangChiTiet> lists;
    TextView tvTenSP, tvSLDG ,tvThanhTien;
    SanPhamDAO sanPhamDAO;

    public DonHangChiTietApdapter(Context context, GioHangActivity activity, ArrayList<DonHangChiTiet> lists) {
        super(context, 0,lists);
        this.context=context;
        this.activity=activity;
        this.lists=lists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hoa_don_chi_tiet, null);
        }
        final DonHangChiTiet item = lists.get(position);
        if(item != null){
            sanPhamDAO = new SanPhamDAO(context);
            SanPham sanPham = sanPhamDAO.getById(String.valueOf(item.getMaSP()));

            tvTenSP = v.findViewById(R.id.tvTenSP);
            tvSLDG = v.findViewById(R.id.tvSLDG);
            tvThanhTien = v.findViewById(R.id.tvThanhTien);

            tvTenSP.setText(" "+sanPham.getTenSP());
            tvSLDG.setText(item.getSoLuongMua()+" x "+currencyFormat(String.valueOf(sanPham.getGiaTien()))+"");
            tvThanhTien.setText(currencyFormat(String.valueOf(sanPham.getGiaTien() * item.getSoLuongMua()))+" VND");



        }
        return v;
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }
}
