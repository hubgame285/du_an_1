package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duan1.Activities.NhaSanXuatActivity;
import com.poly.duan1.Models.NhaSanXuat;
import com.poly.duan1.R;

import java.util.ArrayList;

public class NhaSanXuatAdapter extends ArrayAdapter<NhaSanXuat> {
    private Context context;
    NhaSanXuatActivity activity;
    private ArrayList<NhaSanXuat> lists;
    TextView tvTenNSX;

    public NhaSanXuatAdapter(Context context, NhaSanXuatActivity activity, ArrayList<NhaSanXuat> lists) {
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
            v = inflater.inflate(R.layout.item_nha_san_xuat, null);
        }
        final NhaSanXuat item = lists.get(position);
        if(item != null){
            tvTenNSX = v.findViewById(R.id.tvTenNSX);
            tvTenNSX.setText(""+item.getTenNSX());

        }
        return v;
    }
}
