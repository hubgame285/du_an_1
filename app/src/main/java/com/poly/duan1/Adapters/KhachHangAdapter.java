package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.poly.duan1.Activities.KhachHangActivity;
import com.poly.duan1.Activities.NhaSanXuatActivity;
import com.poly.duan1.Models.KhachHang;
import com.poly.duan1.Models.NhaSanXuat;
import com.poly.duan1.R;

import java.util.ArrayList;

public class KhachHangAdapter extends ArrayAdapter<KhachHang> {
    private Context context;
    KhachHangActivity activity;
    private ArrayList<KhachHang> lists;
    TextView tvTenKH,tvSDT;

    public KhachHangAdapter(Context context, KhachHangActivity activity, ArrayList<KhachHang> lists) {
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
            v = inflater.inflate(R.layout.item_khach_hang, null);
        }
        final KhachHang item = lists.get(position);
        if(item != null){
            tvTenKH = v.findViewById(R.id.tvTenKH);
            tvSDT = v.findViewById(R.id.tvSDT);

            tvTenKH.setText(""+item.tenKH);
            tvSDT.setText(""+item.SDT);

        }
        return v;
    }
}
