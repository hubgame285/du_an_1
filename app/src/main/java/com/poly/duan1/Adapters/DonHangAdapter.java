package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.poly.duan1.Fragments.HoaDonFragment;
import com.poly.duan1.Models.DonHang;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangAdapter extends ArrayAdapter<DonHang> {
    private Context context;
    HoaDonFragment fragment;
    private ArrayList<DonHang> lists;
    TextView tvMaDH, tvNgayMua ,tvTongTien;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm:ss a");

    public DonHangAdapter(Context context, HoaDonFragment fragment, ArrayList<DonHang> lists) {
        super(context, 0,lists);
        this.context=context;
        this.fragment=fragment;
        this.lists=lists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_don_hang, null);
        }
        final DonHang item = lists.get(position);
        if(item != null){
            tvMaDH = v.findViewById(R.id.tvMaDH);
            tvNgayMua = v.findViewById(R.id.tvNgayMua);
            tvTongTien = v.findViewById(R.id.tvTongTien);


            tvMaDH.setText("Mã: "+item.getMaDH());
            tvNgayMua.setText(""+sdf.format(item.getNgayMua()));
            tvTongTien.setText(currencyFormat(String.valueOf(item.getTongTien()))+" VND");

        }
        return v;
    }


    //Dinh dang tien te
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }
}
