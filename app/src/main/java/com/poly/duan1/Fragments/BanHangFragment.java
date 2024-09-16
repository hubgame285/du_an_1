package com.poly.duan1.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.poly.duan1.Activities.DetailsActivity;
import com.poly.duan1.Activities.GioHangActivity;
import com.poly.duan1.Adapters.BanHangAdapter;
import com.poly.duan1.DAO.DonHangChiTietDAO;
import com.poly.duan1.DAO.DonHangDAO;
import com.poly.duan1.DAO.SanPhamDAO;
import com.poly.duan1.Models.DonHang;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.util.ArrayList;
import java.util.List;


public class BanHangFragment extends Fragment {

    SearchView searchView;
    TextView mTVCount;
    ImageView mImg;
    GridView lvBH;
    int countCart=0;

    ArrayList<SanPham> list;
    ArrayList<SanPham> listSearch;
    ArrayList<DonHangChiTiet> listChiTiet;
    SanPhamDAO dao;
    DonHangDAO donHangDAO;
    DonHangChiTietDAO chiTietDAO;
    DonHang donHang;
    DonHangChiTiet chiTiet;
    BanHangAdapter adapter;
    SanPham item1;
    String mMaSP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ban_hang, container, false);
        lvBH = v.findViewById(R.id.lvBH);
        setHasOptionsMenu(true);
        capNhatLv();

        //long click vao item thi hien thi thong tin san pham
        lvBH.setLongClickable(true);
        lvBH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item1 = list.get(position);
                mMaSP = item1.getMaSP();
                //check maSP
                Log.d("masplongclick: ",mMaSP );
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("maSP",mMaSP);
                startActivity(intent);
                return false;
            }
        });
        // click vào item trong listview tạo ra 1 đơn hàng chi tiết với mã sp đó, hoặc tăng thêm 1 số lượng khi cùng mã sp
        lvBH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item1 = list.get(position);
                mMaSP = item1.getMaSP();

                if(item1.getSoLuong()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Sản phẩm bạn chọn đã hết hàng");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    if (listChiTiet.size() >= 1) {
                        for (int i = 0; i <= listChiTiet.size() - 1; i++) {
                            chiTiet = listChiTiet.get(i);
                            if (chiTiet.getMaSP().equals(mMaSP) == true && chiTiet.getMaDH() == donHang.getMaDH()) {
                                // tạo mới 1 đơn hàng chi tiết
                                DonHangChiTiet chiTiet2 = new DonHangChiTiet();
                                chiTiet2.setMaCT(chiTiet.getMaCT());
                                chiTiet2.setMaSP(mMaSP);
                                chiTiet2.setMaDH(donHang.getMaDH());
                                chiTiet2.setSoLuongMua(chiTiet.getSoLuongMua() + 1);
                                chiTiet2.setThanhTien((chiTiet.getSoLuongMua() + 1) * item1.getGiaTien());
                                chiTietDAO.update(chiTiet2);
                                //cập nhật lại số lượng sp
                                SanPham sanPham = new SanPham();
                                sanPham.setMaSP(item1.getMaSP());
                                sanPham.setMaNSX(item1.getMaNSX());
                                sanPham.setTenSP(item1.getTenSP());
                                sanPham.setMoTa(item1.getMoTa());
                                sanPham.setGiaTien(item1.getGiaTien());
                                sanPham.setSoLuong(item1.getSoLuong() - 1);
                                sanPham.setAnhSP(item1.getAnhSP());
                                dao.update(sanPham);
                                break;
                            } else if (chiTiet.getMaSP().equals(mMaSP) == false && chiTiet.getMaDH() == donHang.getMaDH() && i >= listChiTiet.size() - 1) {
                                //thêm 1 sô lượng đơn hàng chi tiết
                                DonHangChiTiet chiTiet1 = new DonHangChiTiet();
                                chiTiet1.setMaSP(mMaSP);
                                chiTiet1.setMaDH(donHang.getMaDH());
                                chiTiet1.setSoLuongMua(1);
                                chiTiet1.setThanhTien(item1.getGiaTien());
                                chiTietDAO.insert(chiTiet1);
                                //cập nhật lại số lượng sp
                                SanPham sanPham = new SanPham();
                                sanPham.setMaSP(item1.getMaSP());
                                sanPham.setMaNSX(item1.getMaNSX());
                                sanPham.setTenSP(item1.getTenSP());
                                sanPham.setMoTa(item1.getMoTa());
                                sanPham.setGiaTien(item1.getGiaTien());
                                sanPham.setSoLuong(item1.getSoLuong() - 1);
                                sanPham.setAnhSP(item1.getAnhSP());
                                dao.update(sanPham);
                                break;
                            }
                        }
                    } else {
                        // tạo mới 1 đơn hàng chi tiết
                        DonHangChiTiet chiTiet1 = new DonHangChiTiet();
                        chiTiet1.setMaSP(mMaSP);
                        chiTiet1.setMaDH(donHang.getMaDH());
                        chiTiet1.setSoLuongMua(1);
                        chiTiet1.setThanhTien(item1.getGiaTien());
                        chiTietDAO.insert(chiTiet1);

                        //cập nhật lại số lượng sp
                        SanPham sanPham = new SanPham();
                        sanPham.setMaSP(item1.getMaSP());
                        sanPham.setMaNSX(item1.getMaNSX());
                        sanPham.setTenSP(item1.getTenSP());
                        sanPham.setMoTa(item1.getMoTa());
                        sanPham.setGiaTien(item1.getGiaTien());
                        sanPham.setSoLuong(item1.getSoLuong() - 1);
                        sanPham.setAnhSP(item1.getAnhSP());
                        dao.update(sanPham);
                    }
                }
                    // gọi lại cập nhật lv
                    capNhatLv();
                    //tv số trên giỏ hàng hiện ra và set số lượng
                    mTVCount.setVisibility(getView().VISIBLE);
                    mTVCount.setText(countCart+"");
                }
        });
        return v;
    }

    // set menu trên tool bar, có icon giỏ hàng và icon search
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ban_hang,menu);
        super.onCreateOptionsMenu(menu, inflater);
        //icon gio hang
        MenuItem item = menu.findItem(R.id.action_cartBH);
        MenuItemCompat.setActionView(item, R.layout.so_tren_gio_hang);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        mTVCount = (TextView) notifCount.findViewById(R.id.tvCount);
        mImg = (ImageView) notifCount.findViewById(R.id.mImg);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GioHangActivity.class));
            }
        });
        if (countCart==0){
            mTVCount.setVisibility(getView().GONE);
        } else{
            mTVCount.setText(countCart+"");
        }

        //icon search
        MenuItem myActionMenuItem = menu.findItem( R.id.action_searchBH);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // tìm kiếm đổ lên lv và khi click tạo đơn hàng chi tiết hoặc tăng số lượng
            @Override
            public boolean onQueryTextChange(String newText) {
                listSearch = new ArrayList<>();
                for (int i = 0; i <= list.size() - 1; i++) {
                    item1 = list.get(i);
                    if (item1.getTenSP().toUpperCase().contains(newText.toUpperCase())) {
                        listSearch.add(item1);
                    }
                }
                adapter = new BanHangAdapter(getContext(), BanHangFragment.this, listSearch);
                lvBH.setAdapter(adapter);
                lvBH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        item1 = listSearch.get(position);
                        mMaSP = item1.getMaSP();
                        if (listChiTiet.size()>=1){
                            for (int i=0; i<=listChiTiet.size()-1; i++){
                                chiTiet = listChiTiet.get(i);
                                if (chiTiet.getMaSP().equals(mMaSP)==true && chiTiet.getMaDH() == donHang.getMaDH()){
                                    // tạo mới 1 đơn hàng chi tiết
                                    DonHangChiTiet chiTiet2 = new DonHangChiTiet();
                                    chiTiet2.setMaCT(chiTiet.getMaCT());
                                    chiTiet2.setMaSP(mMaSP);
                                    chiTiet2.setMaDH(donHang.getMaDH());
                                    chiTiet2.setSoLuongMua(chiTiet.getSoLuongMua()+1);
                                    chiTiet2.setThanhTien((chiTiet.getSoLuongMua()+1)*item1.getGiaTien());
                                    chiTietDAO.update(chiTiet2);
                                    //cập nhật lại số lượng sp
                                    SanPham sanPham = new SanPham();
                                    sanPham.setMaSP(item1.getMaSP());
                                    sanPham.setMaNSX(item1.getMaNSX());
                                    sanPham.setTenSP(item1.getTenSP());
                                    sanPham.setMoTa(item1.getMoTa());
                                    sanPham.setGiaTien(item1.getGiaTien());
                                    sanPham.setSoLuong(item1.getSoLuong()-1);
                                    sanPham.setAnhSP(item1.getAnhSP());
                                    dao.update(sanPham);
                                    break;
                                } else if (chiTiet.getMaSP().equals(mMaSP)==false && chiTiet.getMaDH() == donHang.getMaDH() && i>=listChiTiet.size()-1){
                                    //thêm 1 sô lượng đơn hàng chi tiết
                                    DonHangChiTiet chiTiet1 = new DonHangChiTiet();
                                    chiTiet1.setMaSP(mMaSP);
                                    chiTiet1.setMaDH(donHang.getMaDH());
                                    chiTiet1.setSoLuongMua(1);
                                    chiTiet1.setThanhTien(item1.getGiaTien());
                                    chiTietDAO.insert(chiTiet1);
                                    //cập nhật lại số lượng sp
                                    SanPham sanPham = new SanPham();
                                    sanPham.setMaSP(item1.getMaSP());
                                    sanPham.setMaNSX(item1.getMaNSX());
                                    sanPham.setTenSP(item1.getTenSP());
                                    sanPham.setMoTa(item1.getMoTa());
                                    sanPham.setGiaTien(item1.getGiaTien());
                                    sanPham.setSoLuong(item1.getSoLuong()-1);
                                    sanPham.setAnhSP(item1.getAnhSP());
                                    dao.update(sanPham);
                                    break;
                                }
                            }
                        } else {
                            // tạo mới 1 đơn hàng chi tiết
                            DonHangChiTiet chiTiet1 = new DonHangChiTiet();
                            chiTiet1.setMaSP(mMaSP);
                            chiTiet1.setMaDH(donHang.getMaDH());
                            chiTiet1.setSoLuongMua(1);
                            chiTiet1.setThanhTien(item1.getGiaTien());
                            chiTietDAO.insert(chiTiet1);

                            //cập nhật lại số lượng sp
                            SanPham sanPham = new SanPham();
                            sanPham.setMaSP(item1.getMaSP());
                            sanPham.setMaNSX(item1.getMaNSX());
                            sanPham.setTenSP(item1.getTenSP());
                            sanPham.setMoTa(item1.getMoTa());
                            sanPham.setGiaTien(item1.getGiaTien());
                            sanPham.setSoLuong(item1.getSoLuong()-1);
                            sanPham.setAnhSP(item1.getAnhSP());
                            dao.update(sanPham);
                        }
                        capNhatLv();
                        mTVCount.setVisibility(getView().VISIBLE);
                        mTVCount.setText(countCart+"");
                        listSearch = new ArrayList<>();
                        for (int i = 0; i <= list.size() - 1; i++) {
                            item1 = list.get(i);
                            if (item1.getTenSP().toUpperCase().contains(newText.toUpperCase())) {
                                listSearch.add(item1);
                            }
                        }
                        adapter = new BanHangAdapter(getContext(), BanHangFragment.this, listSearch);
                        lvBH.setAdapter(adapter);
                    }
                });
                return false;
            }
        });
    }

    void capNhatLv() {
        //thêm try catch de khong bi vang app de di tim loi
        try {
            dao = new SanPhamDAO(getContext());
            donHangDAO = new DonHangDAO(getContext());
            donHang = donHangDAO.getChuaTT();
            chiTietDAO = new DonHangChiTietDAO(getContext());
            Log.d("chiTietDAO = new DonHangChiTietDAO(getContext());", "thành công");
            //lỗi từ dòng dưới
            listChiTiet = (ArrayList<DonHangChiTiet>) chiTietDAO.getBymaHD(String.valueOf(donHang.getMaDH()));
            list = (ArrayList<SanPham>) dao.get();
            adapter = new BanHangAdapter(getContext(), this, list);
            lvBH.setAdapter(adapter);
            countCart = chiTietDAO.getTongSL(String.valueOf(donHang.getMaDH()));
        }catch (Exception e){
            Log.e("capNhatLv EROR",e.getMessage());
        }
    }
}
