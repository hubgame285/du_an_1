package com.poly.duan1.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.poly.duan1.Adapters.DonHangChiTietApdapter;
import com.poly.duan1.Adapters.DonHangChiTietApdapter2;
import com.poly.duan1.DAO.DonHangChiTietDAO;
import com.poly.duan1.DAO.DonHangDAO;
import com.poly.duan1.DAO.NhanVienDAO;
import com.poly.duan1.Fragments.HoaDonFragment;
import com.poly.duan1.Models.DonHang;
import com.poly.duan1.Models.DonHangChiTiet;
import com.poly.duan1.Models.NhanVien;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class XemDonHangActivity extends AppCompatActivity {

    ConstraintLayout layoutKH;
    TextView tvMaDH,tvKH, tvTongTien, tvNgayMua, tvNV;
    ListView lvHDCT;

    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    ArrayList<DonHangChiTiet> list;
    DonHangChiTietDAO dao;
    DonHangChiTietApdapter2 adapter;

    DonHang donHang;
    DonHangDAO donHangDAO;
    NhanVien nhanVien;
    NhanVienDAO nhanVienDAO;

    int mMaDH;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm:ss a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        //anh xạ
        layoutKH = findViewById(R.id.layoutKH);
        tvNgayMua = findViewById(R.id.tvNgayMua);
        tvMaDH = findViewById(R.id.tvMaDH);
        tvKH = findViewById(R.id.tvKH);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvNV = findViewById(R.id.tvNV);
        lvHDCT = findViewById(R.id.lvHDCT);
        capNhatLv();
        tvMaDH.setText(mMaDH+"");
        tvTongTien.setText(currencyFormat(String.valueOf(donHang.getTongTien()))+" VND");
        tvNgayMua.setText(sdf.format(donHang.getNgayMua())+"");
        tvNV.setText(nhanVien.getHoTen());


        // set toolbar thay thế actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Giỏ hàng");

        //set icon
        ab.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
        ab.setDisplayHomeAsUpEnabled(true);

        //nhấn vào icon back bên trái trên màn hình thực hiện onBackPressed();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void capNhatLv() {
        HoaDonFragment hoaDonFragment = new HoaDonFragment();
        mMaDH = hoaDonFragment.getmMaDH();
        donHangDAO = new DonHangDAO(this);
        donHang = donHangDAO.getById(mMaDH);
        nhanVienDAO = new NhanVienDAO(this);
        nhanVien = nhanVienDAO.getById(donHang.getMaNV());
        dao = new DonHangChiTietDAO(this);
        list = (ArrayList<DonHangChiTiet>) dao.getBymaHD(String.valueOf(donHang.getMaDH()));
        adapter = new DonHangChiTietApdapter2(this, this, list);
        lvHDCT.setAdapter(adapter);
    }
    //Dinh dang tien te
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }
}