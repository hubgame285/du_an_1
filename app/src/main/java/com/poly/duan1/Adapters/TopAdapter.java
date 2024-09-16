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

import com.poly.duan1.Activities.TopActivity;
import com.poly.duan1.Models.Top;
import com.poly.duan1.R;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {
    private Context context;
    TopActivity activity;
    private ArrayList<Top> lists;
    TextView tvTenSP, tvTongSoLuong;
    ImageView imgAnhSP;

    public TopAdapter(Context context, TopActivity activity, ArrayList<Top> lists) {
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
            v = inflater.inflate(R.layout.item_top, null);
        }
        final Top item = lists.get(position);
        if(item != null){
            tvTenSP = v.findViewById(R.id.tvTenSP);
            tvTongSoLuong = v.findViewById(R.id.tvTongSoLuong);
            imgAnhSP = v.findViewById(R.id.imgAnhSP);

            tvTenSP.setText(""+item.tenSP);
            tvTongSoLuong.setText("Số lượng: "+item.soLuong);

            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(item.anhSP, 0, item.anhSP.length);
                imgAnhSP.setImageBitmap(bitmap);
            }catch (Exception e){

            }

        }
        return v;
    }
}
