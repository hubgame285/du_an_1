package com.poly.duan1.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.poly.duan1.DAO.NhaSanXuatDAO;
import com.poly.duan1.DAO.SanPhamDAO;
import com.poly.duan1.Models.NhaSanXuat;
import com.poly.duan1.Models.SanPham;
import com.poly.duan1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView mTitle;
    ActionBar ab;

    ImageView imgAnhSP;
    TextView tv_tenSP,tv_giaTien,tv_nsx,tv_moTa;

    NhaSanXuatDAO nhaSanXuatDAO;
    SanPhamDAO dao;
    SanPham items;
    ArrayList<SanPham> list;
    String mMaSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imgAnhSP = findViewById(R.id.imgAnhSPDetail);
        tv_tenSP = findViewById(R.id.tvTenSP);
        tv_giaTien = findViewById(R.id.tvGiaTien);
        tv_nsx = findViewById(R.id.tvNSX);
        tv_moTa = findViewById(R.id.tvMota);

        mMaSP = getIntent().getExtras().getString("maSP");
        //check maSP
        Log.d("maspdetail: ",mMaSP );
        dao = new SanPhamDAO(this);
        items = dao.getById(mMaSP);
        //check items
        Log.d("itemsdetail: ",items+"" );
        nhaSanXuatDAO = new NhaSanXuatDAO(this);
        NhaSanXuat nhaSanXuat = nhaSanXuatDAO.getById(items.getMaNSX());
        tv_tenSP.setText("Tên sản phẩm: "+items.getTenSP());
        tv_nsx.setText("Nhà sản xuất: "+nhaSanXuat.getTenNSX());
        tv_giaTien.setText("Giá tiền: "+currencyFormat(String.valueOf(items.getGiaTien()))+"đ");
        tv_moTa.setText(items.getMoTa());
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(items.getAnhSP(), 0, items.getAnhSP().length);
            imgAnhSP.setImageBitmap(bitmap);
        }catch (Exception e){

        }
        // set toolbar thay thế actionbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("");
        mTitle.setText("Thông tin");
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
    //Dinh dang tien te
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }
}