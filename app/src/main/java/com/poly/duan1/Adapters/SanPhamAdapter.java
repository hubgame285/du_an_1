package com.poly.duan1.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duan1.Activities.MatHangActivity;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    MatHangActivity activity;
    private ArrayList<SanPham> lists;
    TextView tvTenSP, tvSoLuong ,tvGiaTien;
    ImageView imgAnhSP;

    public SanPhamAdapter(Context context, MatHangActivity activity, ArrayList<SanPham> lists) {
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
            v = inflater.inflate(R.layout.item_san_pham, null);
        }
        final SanPham item = lists.get(position);
        if(item != null){
            tvTenSP = v.findViewById(R.id.tvTenSP);
            tvSoLuong = v.findViewById(R.id.tvSoLuong);
            tvGiaTien = v.findViewById(R.id.tvGiaTien);
            imgAnhSP = v.findViewById(R.id.imgAnhSP);

            tvTenSP.setText(""+item.getTenSP());
            tvSoLuong.setText("Còn: "+item.getSoLuong());
            tvGiaTien.setText(currencyFormat(String.valueOf(item.getGiaTien()))+" VND");

            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(item.getAnhSP(), 0, item.getAnhSP().length);
                imgAnhSP.setImageBitmap(bitmap);
            }catch (Exception e){

            }

        }
        return v;
    }
    //Dinh dang tien te
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }
}
