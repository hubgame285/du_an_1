package com.poly.duan1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duan1.R;
import com.poly.duan1.Fragments.ThemFragment;
import com.poly.duan1.Models.ModelThem;

import java.util.ArrayList;

public class ThemAdapter extends ArrayAdapter<ModelThem> {
    private Context context;
    ThemFragment fragment;
    private ArrayList<ModelThem> lists;
    TextView tvTXT;
    ImageView imgIcon;

    public ThemAdapter(Context context, ThemFragment fragment, ArrayList<ModelThem> lists) {
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
            v = inflater.inflate(R.layout.item_them, null);
        }
        final ModelThem item = lists.get(position);
        if(item != null){
            tvTXT = v.findViewById(R.id.tvTXT);
            imgIcon = v.findViewById(R.id.imgIcon);
            tvTXT.setText(item.txt);
            imgIcon.setImageResource(item.icon);

        }
        return v;
    }
}
