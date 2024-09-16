package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duan1.Fragments.BaoCaoFragment;
import com.poly.duan1.Models.ModelBaoCao;
import com.poly.duan1.R;

import java.util.ArrayList;

public class BaoCaoAdapter extends ArrayAdapter<ModelBaoCao> {
    private Context context;
    BaoCaoFragment fragment;
    private ArrayList<ModelBaoCao> lists;
    TextView tieude;
    ImageView hinh;
    public BaoCaoAdapter(Context c, BaoCaoFragment frag,ArrayList<ModelBaoCao> list) {
        super(c, 0, list);
        this.context=c;
        this.fragment=frag;
        this.lists=list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_bao_cao, null);
        }
        final ModelBaoCao item = lists.get(position);
        if(item != null){
            tieude= v.findViewById(R.id.tvTXTBC);
            hinh = v.findViewById(R.id.imgIconBC);
            tieude.setText(item.tieude);
            hinh.setImageResource(item.hinh);

        }
        return v;
    }
}
