package com.poly.duan1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.poly.duan1.Activities.XemDonHangActivity;
import com.poly.duan1.Adapters.DonHangAdapter;
import com.poly.duan1.DAO.DonHangDAO;
import com.poly.duan1.Models.DonHang;
import com.poly.duan1.R;

import java.util.ArrayList;


public class HoaDonFragment extends Fragment {
    SearchView searchView;
    ListView lvDH;

    ArrayList<DonHang> list;
    ArrayList<DonHang> listSearch;
    DonHang items;
    DonHangAdapter adapter;
    DonHangDAO dao;
    public static int mMaDH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        // ánh xạ
        lvDH = v.findViewById(R.id.lvHD);
        lvDH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DonHang donHang = list.get(position);
                mMaDH = donHang.getMaDH();
                startActivity(new Intent(getActivity(), XemDonHangActivity.class));
            }
        });
        setHasOptionsMenu(true);
        capNhatLv();
        return v;
    }

    //set menu có icon search và tìm kiếm
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hoa_don,menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_searchBH);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listSearch = new ArrayList<>();
                for (int i = 0; i <= list.size() - 1; i++) {
                    items = list.get(i);
                    if (String.valueOf(items.getMaDH()).toUpperCase().contains(newText.toUpperCase())) {
                        listSearch.add(items);
                    }
                }
                adapter = new DonHangAdapter(getContext(), HoaDonFragment.this, listSearch);
                lvDH.setAdapter(adapter);
                lvDH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DonHang donHang = listSearch.get(position);
                        mMaDH = donHang.getMaDH();
                        startActivity(new Intent(getActivity(), XemDonHangActivity.class));
                    }
                });
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    void capNhatLv() {
        dao = new DonHangDAO(getContext());
        list = (ArrayList<DonHang>) dao.getDaTT();
        adapter = new DonHangAdapter(getContext(), this, list);
        lvDH.setAdapter(adapter);
    }

    public static int getmMaDH(){
        return mMaDH;
    }
}
