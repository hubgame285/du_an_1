package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.poly.duan1.Activities.KhachHangActivity;
import com.poly.duan1.Activities.NhanVienActivity;
import com.poly.duan1.Models.KhachHang;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

import java.util.ArrayList;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    NhanVienActivity activity;
    private ArrayList<NhanVien> lists;
    TextView tvTenNV, tvSDT ,tvVaiTro;

    public NhanVienAdapter(Context context, NhanVienActivity activity, ArrayList<NhanVien> lists) {
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
            v = inflater.inflate(R.layout.item_nhan_vien, null);
        }
        final NhanVien item = lists.get(position);
        if(item != null){
            tvTenNV = v.findViewById(R.id.tvTenNV);
            tvSDT = v.findViewById(R.id.tvSDT);
            tvVaiTro = v.findViewById(R.id.tvVaiTro);

            tvTenNV.setText(""+item.getHoTen());
            tvSDT.setText(""+item.getSDT());
            if (item.getVaiTro()==0){
                tvVaiTro.setText("Quản lý");
            } else {
                tvVaiTro.setText("Nhân viên");
            }

        }
        return v;
    }
}
