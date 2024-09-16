package com.poly.duan1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.poly.duan1.Activities.DoanhThuActivity2;
import com.poly.duan1.Activities.TopActivity;
import com.poly.duan1.Adapters.BaoCaoAdapter;
import com.poly.duan1.Models.ModelBaoCao;
import com.poly.duan1.R;

import java.util.ArrayList;


public class BaoCaoFragment extends Fragment {
    ListView lvBaoCao;
    ArrayList<ModelBaoCao> list = new ArrayList<>();
    BaoCaoAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bao_cao, container, false);
        lvBaoCao = v.findViewById(R.id.lvBaoCao);
        //Thêm vào lvBaoCao
        list.add(new ModelBaoCao(R.drawable.ic_baseline_bar_chart_24,"Top 10 sản phẩm bán chạy"));
        list.add(new ModelBaoCao(R.drawable.ic_baseline_assignment_24,"Doanh thu theo ngày"));
        adapter = new BaoCaoAdapter(getActivity(),BaoCaoFragment.this,list);
        lvBaoCao.setAdapter(adapter);

        lvBaoCao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    startActivity(new Intent(getActivity(), TopActivity.class));
                }
                if (position==1){
                    startActivity(new Intent(getActivity(), DoanhThuActivity2.class));
                }
            }
        });
        return v;
    }
}
